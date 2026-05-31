# Contexto del Sistema — LUNORION LABS

## Propósito

Plataforma SaaS Multitenant para la gestión integral de talleres automotrices. Centraliza la operación comercial, administrativa y técnica del taller: inventario, ventas, órdenes de trabajo, citas, check-in digital, facturación electrónica (SUNAT vía SOAP), documentos legales-RRHH y reportes gerenciales con actualización en tiempo real vía WebSockets.

## Stakeholders

| Actor | Rol en Sistema | Descripción |
|:---|:---|:---|
| **Super Admin** | `SUPER_ADMIN` | Dueño de la plataforma SaaS. Gestiona tenants (talleres), certificados digitales, métricas globales. No opera talleres. |
| **Admin del Taller** | `ADMIN` | Dueño/gerente de un taller específico. Acceso total a su tenant. Crea usuarios PUBLIC y les asigna permisos individuales. |
| **Usuario Público** | `PUBLIC` | Cliente del servicio — empleado del taller (cajero, técnico, vendedor, recepcionista, asesor). Accede solo a los permisos que el ADMIN le configuró. Un mismo usuario puede tener permisos de múltiples áreas. |
| **Cliente del Taller** | Sin acceso | Persona externa que lleva su vehículo al taller. Recibe cotizaciones, agenda citas, firma acta de ingreso digital, recibe comprobantes electrónicos. No tiene acceso al sistema. |

> **Nota sobre permisos:** El sistema usa **PBAC puro** — no hay roles rígidos como "cajero" o "técnico". Cada usuario PUBLIC tiene permisos asignados uno a uno por el ADMIN de su taller. Un cajero puede ver inventario si el ADMIN lo decide, sin estar limitado por un rol fijo.

## Arquitectura Propuesta

```
┌─────────────────────────────────────────────────────┐
│                   Frontend Angular                    │
│        (Web App + PWA mobile para check-in)           │
└──────────┬──────────────┬───────────────────────────┘
           │ HTTPS / REST  │ WSS (WebSocket STOMP)
           │               │  (Dashboards, Stock, OT,
           │               │   Notificaciones en vivo)
┌──────────▼──────────────▼───────────────────────────┐
│               API Gateway (Spring Cloud Gateway)       │
│       (Autenticación, Rate Limiting, Routing x Tenant) │
└──────┬──────┬──────┬──────┬──────┬──────┬──────┬─────┘
       │      │      │      │      │      │      │
┌──────▼─┐ ┌──▼───┐ ┌▼────┐ ┌▼───┐ ┌▼────┐ ┌▼────┐ ┌▼───┐
│Módulo  │ │Módulo│ │Mód. │ │Mód.│ │Mód. │ │Mód. │ │Mód. │
│Invent. │ │Ventas│ │OT   │ │Caja│ │RRHH │ │Docs │ │Admin│
│        │ │      │ │     │ │    │ │Leg. │ │Leg. │ │     │
└────────┘ └──────┘ └─────┘ └────┘ └─────┘ └─────┘ └─────┘
       │      │      │      │      │      │      │
┌──────▼──────▼──────▼──────▼──────▼──────▼──────▼──────┐
│              PostgreSQL (Multitenant x tenant_id)        │
│            Aislamiento por fila + esquema compartido      │
└──────────────────────────────────────────────────────────┘

  ┌─────────────────────────────────────────────────────┐
  │  Integraciones Externas (vía SOAP)                   │
  │  ┌────────────────────┐  ┌──────────────────────────┐│
  │  │ SUNAT OSE / OSB    │  │ PSE (Proveedor Servicios ││
  │  │ · Boleta Electr.   │  │       Electrónicos)      ││
  │  │ · Factura Electr.  │  │ · Firma Digital          ││
  │  │ · Nota Créd/Débito │  │ · Envío masivo           ││
  │  │ · Resumen Diario   │  │ · Validación RUC         ││
  │  │ · CDR              │  │                          ││
  │  └────────────────────┘  └──────────────────────────┘│
  │              │                                        │
  │  ┌───────────▼──────────────────────────────────────┐ │
  │  │  Libros Electrónicos (PLE) · SUNAT               │ │
  │  │  Registro de Ventas, Compras, Diario             │ │
  │  └──────────────────────────────────────────────────┘ │
  └─────────────────────────────────────────────────────┘
```

**Recomendación:** Monolito Modular con Arquitectura Hexagonal (no microservicios).

Cada módulo es un bounded context independiente dentro del mismo deploy, comunicado por interfaces/puertos. Si en el futuro se requiere escalar equipos o módulos específicos, se extraen a microservicios sin reescribir lógica de dominio.

## Stack Tecnológico

| Capa | Tecnología |
|:---|:---|
| Frontend | Angular (Web + PWA) |
| Backend | Spring Boot (Java 17+) |
| API REST | Spring Web (OpenAPI 3.0 / SpringDoc) |
| API SOAP (SUNAT) | Spring Web Services (WS-Security, firma digital XML) |
| Tiempo Real | WebSockets (STOMP sobre SockJS) |
| Base de Datos | PostgreSQL |
| Arquitectura | SaaS Multitenant (aislamiento por `tenant_id`) |
| Seguridad | PBAC (Permission-Based Access Control) |
| Notificaciones | WebSocket en vivo + WhatsApp API + Email SMTP |
| Facturación Electrónica | SOAP client → SUNAT OSE / PSE autorizado |
| RRHH / Legal | Generación de boletas de pago, PLAME, libros electrónicos (PLE) |
| Almacén Documentos | PDF/A firmados digitalmente, CDR, XML-Signed |

## Cumplimiento Legal (Perú)

El sistema debe cumplir con la normativa peruana vigente:

| Normativa | Aplicación |
|:---|:---|
| **Ley de Facturación Electrónica — SUNAT (RS 188-2020/SUNAT)** | Emisión de comprobantes electrónicos (boleta, factura, NC, ND) vía SOAP con firma digital XML. |
| **Resolución de Contaduría Pública (Libros Electrónicos — PLE)** | Generación del Registro de Ventas, Compras y Libro Diario en formato SUNAT, retención mínima 5 años. |
| **Ley de Protección de Datos Personales (Ley 29733)** | Consentimiento del cliente, cifrado de datos sensibles, derecho de cancelación. |
| **Ley de Firma Digital (Ley 27269)** | Firma digital con certificado RENIEC/RUC para documentos electrónicos con validez legal. |
| **Normas Laborales — SUNAT (PLAME / T-Registro)** | Boleta de pago electrónica, registro de trabajadores, cálculo de comisiones y retenciones. |

### Documentos Legales que Genera el Sistema

| Documento | Canal | Marco Legal |
|:---|:---|:---|
| Boleta de Venta Electrónica | SOAP → SUNAT | Ley de Facturación Electrónica |
| Factura Electrónica (B2B) | SOAP → SUNAT | Ley de Facturación Electrónica |
| Nota de Crédito Electrónica | SOAP → SUNAT | Ley de Facturación Electrónica |
| Nota de Débito Electrónica | SOAP → SUNAT | Ley de Facturación Electrónica |
| Resumen Diario de Boletas | SOAP → SUNAT | Ley de Facturación Electrónica |
| Comprobante de Recepción (CDR) | SOAP ← SUNAT | Ley de Facturación Electrónica |
| Boleta de Pago Electrónica (RRHH) | Sistema → PDF firmado | Normas Laborales / PLAME |
| Acta de Ingreso (Check-in Digital) | Sistema → PDF firmado | Contrato civil / Firma Digital |
| Cotización / Orden de Trabajo | Sistema → PDF | Documento comercial |
| Reportes PLE (Libros Electrónicos) | Generado → SUNAT | Resolución Contaduría |

## Premisas Arquitectónicas

1. **Tenant_id en toda operación** — Toda consulta y transacción incluye el tenant_id como filtro obligatorio.
2. **Validación de permisos en backend** — Cada endpoint verifica el permiso específico del usuario autenticado.
3. **Auditoría obligatoria** — Todo cambio de estado (inventario, OT, caja, etc.) registra usuario, timestamp y delta.
4. **Transaccionalidad local** — Las operaciones que cruzan módulos usan transacciones de base de datos (no Sagas ni eventos eventuales) por coherencia inmediata.
