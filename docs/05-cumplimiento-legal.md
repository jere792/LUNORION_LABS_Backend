# Cumplimiento Legal — LUNORION LABS

Flujo completo de obligaciones legales según normativa peruana, ordenadas por precedencia.

---

## Índice de Flujo Legal

1. [RUC y Habilitación del Contribuyente](#1-ruc-y-habilitación-del-contribuyente)
2. [Certificado y Firma Digital](#2-certificado-y-firma-digital)
3. [Facturación Electrónica — SUNAT](#3-facturación-electrónica--sunat)
4. [Resumen Diario de Boletas](#4-resumen-diario-de-boletas)
5. [Comprobante de Recepción (CDR)](#5-comprobante-de-recepción-cdr)
6. [Libros Electrónicos (PLE)](#6-libros-electrónicos-ple)
7. [Documentos Laborales y RRHH](#7-documentos-laborales-y-rrhh)
8. [Protección de Datos Personales](#8-protección-de-datos-personales)
9. [Documentos Contractuales y Comerciales](#9-documentos-contractuales-y-comerciales)
10. [Retención y Archivo Legal](#10-retención-y-archivo-legal)
11. [Auditoría y Trazabilidad Legal](#11-auditoría-y-trazabilidad-legal)

---

## 1. RUC y Habilitación del Contribuyente

### 1.1 Registro del Taller ante SUNAT

Cada tenant (taller) debe contar con RUC activo antes de operar en el sistema.

| Acción | Responsable | Sistema |
|:---|:---|:---|
| Validar RUC contra SUNAT | Super Admin | Consulta a API SUNAT (SOAP) al crear tenant |
| Verificar estado (activo/cerrado) | Super Admin | Validación mensual automática |
| Almacenar datos fiscales | Sistema | Razón social, RUC, domicilio fiscal, régimen |
| Detectar cambios de estado | Sistema | Alerta si el RUC cambia a estado "Baja" o "Suspendido" |

### 1.2 Datos Fiscales Obligatorios por Tenant

```
RUC             : 20XXXXXXXXX (11 dígitos)
Razón Social    : Nombre legal del taller
Domicilio Fiscal: Dirección registrada en SUNAT
Régimen         : MYPE Tributario / General / RUS
Representante   : DNI, nombres, cargo
```

### 1.3 Validación en Cada Comprobante

Todo comprobante electrónico debe incluir:
- RUC del emisor (del taller)
- Razón social del emisor
- Domicilio fiscal registrado en SUNAT

---

## 2. Certificado y Firma Digital

Base legal: **Ley 27269 — Ley de Firmas y Certificados Digitales**

### 2.1 Certificado Digital

Cada tenant necesita un certificado digital emitido por una entidad acreditada por INDECOPI.

| Requisito | Detalle |
|:---|:---|
| Tipo | Certificado Digital para Persona Jurídica (RUC) |
| Emisor | INDECOPI, RENIEC o empresa acreditada (Ej. E-CERT, KEBUTEC) |
| Vigencia | 1 a 3 años (renovable antes del vencimiento) |
| Almacenamiento | Keystore PKCS#12 (.p12) protegido por contraseña |
| Algoritmo | RSA 2048 bits / SHA-256 |

### 2.2 Uso de Firma en el Sistema

| Documento | Tipo de Firma | Sistema |
|:---|:---|:---|
| Comprobantes Electrónicos (XML) | Firma digital XML (XAdES-EPES) | Spring WS con WS-Security |
| Boleta de Pago (PDF) | Firma digital PDF (PAdES) | Librería de firma PDF |
| Acta de Ingreso (PDF) | Firma manuscrita digitalizada + metadatos | Captura táctil + sello de tiempo |
| Cotizaciones / OT (PDF) | Sello digital de la empresa (sin firma legal obligatoria) | Imagen de sello + QR |

### 2.3 Caducidad y Renovación

- El sistema **debe alertar** 30 días antes del vencimiento del certificado.
- Si el certificado vence, **se bloquea la emisión** de comprobantes electrónicos.
- Los comprobantes emitidos con certificado válido **siguen siendo legales** aunque el certificado venza después.

---

## 3. Facturación Electrónica — SUNAT

Base legal: **RS 188-2020/SUNAT y normas complementarias**

### 3.1 Arquitectura de Envío SOAP

```
┌──────────────┐     SOAP / XML-Signed     ┌──────────────┐
│   Backend    │ ──────────────────────────▶│ SUNAT OSE    │
│  (Cliente    │◀──────────────────────────│ (O SERVICIO  │
│   SOAP)      │     CDR (XML firmado)      │  OSB)        │
└──────────────┘                            └──────────────┘
                    │
                    │ (o a través de PSE)
                    ▼
            ┌──────────────┐
            │ PSE          │
            │ (Proveedor   │
            │  Servicios   │
            │  Electrónicos)│
            └──────────────┘
```

### 3.2 Comprobantes Electrónicos

El sistema debe emitir 4 tipos de comprobante vía SOAP:

| Tipo | Código SUNAT | Descripción | Cuándo se usa |
|:---|:---:|:---|:---|
| **Factura** | 01 | Comprobante B2B con RUC del cliente | Cliente requiere factura para sustentar gasto |
| **Boleta de Venta** | 03 | Comprobante para consumidor final (sin RUC) | Cliente persona natural sin RUC |
| **Nota de Crédito** | 07 | Anulación total/parcial o descuento | Devolución, error en monto, descuento posterior |
| **Nota de Débito** | 08 | Incremento del monto facturado | Cobro adicional, intereses, ajuste |

### 3.3 Ciclo de Vida de un Comprobante

```
1. GENERACIÓN
   ├── Usuario crea venta/boleta/factura en el sistema
   ├── Sistema calcula totales, impuestos (IGV 18%)
   └── Genera XML según esquema XSD de SUNAT (UBL 2.1)

2. FIRMA DIGITAL
   ├── Sistema carga certificado .p12 del tenant
   ├── Firma el XML (XAdES-EPES) usando digest SHA-256
   └── XML firmado queda listo para envío

3. ENVÍO SOAP
   ├── Envía XML firmado a SUNAT OSE (o PSE)
   ├── Timeout: 10s conexión + 30s lectura
   ├── Reintentos: 3 con backoff exponencial
   └── Si falla: encola para reintento automático

4. RESPUESTA (CDR)
   ├── SUNAT responde con CDR (XML firmado por SUNAT)
   ├── CDR contiene:
   │   ├── Estado: 0 = Aceptado, 1 = Observación, 2 = Rechazado
   │   ├── Código de error (si aplica)
   │   └── Descripción del error
   └── Sistema almacena CDR + comprobante original

5. NOTIFICACIÓN
   ├── WebSocket → usuario: "Comprobante aceptado" o "Rechazado"
   └── Email automático al cliente adjuntando XML + PDF (si aceptado)

6. RECHAZO
   ├── Usuario corrige datos observados
   └── Reenvía comprobante corregido (nuevo intento)
```

### 3.4 Datos Obligatorios por Tipo

**Factura (01):**
```
RUC Emisor, Razón Social Emisor
Tipo Doc. Cliente: RUC (obligatorio)
Número Doc. Cliente (11 dígitos)
Razón Social Cliente
Dirección Cliente (opcional pero recomendado)
Monto Operaciones Gravadas
IGV (18%)
Total
Leyenda: "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO" (si aplica)
```

**Boleta de Venta (03):**
```
RUC Emisor, Razón Social Emisor
Sin datos del cliente (o DNI opcional)
Monto Total (incluye IGV)
Monto exonerado si aplica
```

**Nota de Crédito (07) y Débito (08):**
```
Mismo emisor y cliente que comprobante original
Tipo y serie del comprobante que modifica
Número del comprobante que modifica
Motivo o sustento de la modificación
Monto modificado
```

### 3.5 Series por Tipo de Comprobante

| Comprobante | Serie | Numeración |
|:---|:---|:---|
| Factura | F001 - F999 | 00000001 - 99999999 |
| Boleta | B001 - B999 | 00000001 - 99999999 |
| Nota de Crédito (Factura) | FC01 - FC99 | 00000001 - 99999999 |
| Nota de Crédito (Boleta) | BC01 - BC99 | 00000001 - 99999999 |
| Nota de Débito (Factura) | FD01 - FD99 | 00000001 - 99999999 |

### 3.6 Cálculo de Impuestos

| Concepto | % | Base Legal |
|:---|:---:|:---|
| IGV (Impuesto General a la Venta) | 18% | Ley IGV |
| Detracciones (según rubro) | Variable | SPOT |
| Percepción (ventas no domiciliadas) | Variable | Ley IGV |

Para talleres automotrices:
- **IGV 18%** sobre servicios de reparación y venta de repuestos
- **Detracción:** No aplica para talleres (a menos que emitan facturas por más de S/ 700 soles)
- **Percepción:** Solo aplica si el taller importa repuestos

---

## 4. Resumen Diario de Boletas

Base legal: **RS 188-2020/SUNAT**

### 4.1 Cuándo se Usa

Las boletas de venta (serie Bxxx) no se envían individualmente a SUNAT en tiempo real. Se agrupan en un **Resumen Diario de Boletas (RDB)** que se envía al día siguiente hábil.

### 4.2 Flujo

```
DÍA 1                  DÍA 2 (antes de 23:59)
─────                  ─────────────────────
Boleta B001-00000001    ┐
Boleta B001-00000002    │ → Resumen Diario: RC-2024001-001
Boleta B001-00000003    ┘    enviado vía SOAP a SUNAT
                                ↓
                           CDR del Resumen
```

### 4.3 Resumen por Baja

Si un cliente solicita factura después de haber recibido boleta:
1. Se agrega la boleta al RDB con estado "Anulado"
2. Se emite la factura correspondiente
3. Se envía en el próximo RDB

---

## 5. Comprobante de Recepción (CDR)

### 5.1 Estructura del CDR

```
<cdr:ComprobanteDeRecepcion>
  <cdr:Respuesta>
    <cdr:Estado>0</cdr:Estado>          <!-- 0=Aceptado, 1=Obs, 2=Rechazado -->
    <cdr:Descripcion>La Factura [...] fue aceptada</cdr:Descripcion>
    <cdr:Nota>OBSERVACIONES SI APLICA</cdr:Nota>
    <cdr:CodigoError>XXXX</cdr:CodigoError>
  </cdr:Respuesta>
</cdr:ComprobanteDeRecepcion>
```

### 5.2 Almacenamiento Obligatorio

| Elemento | Formato | Almacenamiento |
|:---|:---|:---|
| XML del comprobante firmado | .xml | Base de datos + disco |
| CDR de SUNAT | .xml | Base de datos + disco |
| PDF (representación impresa) | .pdf | Base de datos + disco |
| Resumen Diario enviado | .xml | Base de datos + disco |
| CDR del Resumen Diario | .xml | Base de datos + disco |

### 5.3 Códigos de Error Comunes

| Código | Descripción | Acción |
|:---:|:---|:---|
| 1000 | Aceptado | — |
| 2000 | Rechazado por validación XSD | Revisar estructura XML |
| 2001 | RUC del emisor no existe o está de baja | Validar RUC del tenant |
| 2002 | RUC del cliente no existe | Validar RUC cliente |
| 2008 | Serie o número ya registrado | Verificar correlativo |
| 2012 | El comprobante modificado no existe | Verificar NC/ND referenciada |
| 2018 | Monto total no coincide con sumatoria | Revisar cálculos |
| 2020 | Error en cálculo de IGV | Revisar tasa y base |
| 2030 | Fecha de emisión fuera de rango | Revisar fecha del sistema |

---

## 6. Libros Electrónicos (PLE)

Base legal: **Resolución de Contaduría Pública / SUNAT**

### 6.1 Libros Obligatorios para el Taller

| Libro | Código SUNAT | Periodicidad | Contenido |
|:---|:---:|:---|:---|
| Registro de Ventas Electrónico (RVE) | 140100 | Mensual | Todas las facturas, boletas, NC, ND emitidas |
| Registro de Compras Electrónico | 080100 | Mensual | Todas las compras del taller |
| Libro Diario | 030100 | Mensual | Asientos contables del mes |
| Libro Mayor | 040100 | Mensual | Cuentas contables |

### 6.2 Generación Automática desde el Sistema

```
┌─────────────────────────────────────────────────────┐
│                   Transacciones del Sistema           │
│  Ventas → Facturas, Boletas, NC, ND                  │
│  Compras → Órdenes de Compra recibidas               │
│  Caja → Ingresos y egresos                           │
│  RRHH → Planilla, boletas de pago                    │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────┐
│           Motor de Generación PLE                    │
│  · Agrupa por mes                                    │
│  · Formatea según esquema SUNAT (TXT/XML)            │
│  · Calcula totales por tipo                          │
│  · Valida contra reglas contables                    │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────┐
│    Salida para SUNAT / Contador                      │
│  · Formato TXT plano (campos delimitados)            │
│  · Archivos ZIP por mes y por libro                  │
│  · Descargable desde el dashboard                    │
└─────────────────────────────────────────────────────┘
```

### 6.3 Formato del Registro de Ventas (RVE)

| Campo | Descripción |
|:---|:---|
| Periodo | YYYYMM |
| CUI | Código Único de Identificación del asiento |
| Nro Correlativo | Secuencia del registro |
| Fecha de Emisión | DD/MM/YYYY |
| Tipo Comprobante | 01 / 03 / 07 / 08 |
| Serie | F001, B001, etc. |
| Número | 00000001 |
| RUC Cliente | 11 dígitos o DNI |
| Razón Social Cliente | Nombre |
| Valor Facturado | Base imponible |
| IGV | 18% |
| Total | Valor + IGV |

### 6.4 Plazos de Presentación

| Período | Fecha Máxima |
|:---|:---|
| Enero | 15 de Febrero |
| Febrero | 15 de Marzo |
| ... | ... |
| Noviembre | 15 de Diciembre |
| Diciembre | 15 de Enero del año siguiente |

---

## 7. Documentos Laborales y RRHH

Base legal: **Ley de Productividad y Competitividad Laboral, PLAME, T-Registro**

### 7.1 T-Registro

Todo trabajador debe ser registrado en T-Registro (SUNAT) antes del inicio de labores.

| Dato | Obligatorio |
|:---|:---|
| Nombres y apellidos | Sí |
| DNI / CE | Sí |
| Fecha de nacimiento | Sí |
| Fecha de inicio de labores | Sí |
| Tipo de contrato | Sí |
| Régimen pensionario (ONP/AFP) | Sí |
| RUC del empleador | Sí |

El sistema debe almacenar estos datos y generar el reporte consolidado para PLAME.

### 7.2 PLAME (Planilla Mensual de Pagos)

| Componente | Descripción | Cálculo |
|:---|:---|:---|
| Remuneración Básica | Sueldo mensual del trabajador | Según contrato |
| Horas Extras | 25% (2 primeras), 35% (siguientes) | Cálculo automático |
| Comisiones | % sobre ventas o servicios | Según regla configurada (RF-49) |
| Asignación Familiar | 10% de la RMV (S/ 102.50) | Si tiene hijos |
| Total Ingresos | Suma de todos los conceptos | Automático |
| Descuento ONP (13%) | 13% de la remuneración | Si está en ONP |
| Descuento AFP | Porcentaje variable según fondo | Si está en AFP |
| ESSALUD | 9% del empleador (no descuenta del trabajador) | Automático |
| Neto a Pagar | Total Ingresos - Descuentos | Automático |

### 7.3 Boleta de Pago Electrónica

Generada mensualmente para cada trabajador:

```
┌─────────────────────────────────────┐
│         BOLETA DE PAGO              │
│    Taller: [Razón Social + RUC]     │
│    Trabajador: [Nombre + DNI]       │
│    Periodo: Mayo 2026               │
├─────────────────────────────────────┤
│ INGRESOS                            │
│  Básico          S/ 1,200.00        │
│  Horas Extras    S/   180.00        │
│  Comisiones      S/   320.00        │
│  Asig. Familiar  S/   102.50        │
│  Total           S/ 1,802.50        │
├─────────────────────────────────────┤
│ DESCUENTOS                          │
│  ONP (13%)       S/   234.33        │
│  Total           S/   234.33        │
├─────────────────────────────────────┤
│ NETO A PAGAR     S/ 1,568.17        │
├─────────────────────────────────────┤
│ APORTES DEL EMPLEADOR               │
│  ESSALUD (9%)    S/   162.23        │
│  SENATI (si aplica)                 │
├─────────────────────────────────────┤
│ Fecha de pago: 30/05/2026           │
│ Sello digital / QR de verificación  │
└─────────────────────────────────────┘
```

### 7.4 CTS y Gratificaciones

| Beneficio | Periodicidad | Cálculo |
|:---|:---|:---|
| CTS | Mayo y Noviembre | 50% del sueldo + 1/6 de gratificación |
| Gratificación | Julio y Diciembre | Sueldo completo + 9% ESSALUD |
| Vacaciones | 1 año de servicio | 30 días de sueldo |

---

## 8. Protección de Datos Personales

Base legal: **Ley 29733 — Ley de Protección de Datos Personales**

### 8.1 Datos Personales que Maneja el Sistema

| Categoría | Datos | Nivel |
|:---|:---|:---|
| Cliente | Nombres, DNI, teléfono, email, dirección | Sensible |
| Trabajador | DNI, domicilio, salud, datos bancarios, asistencia | Muy sensible |
| Vehículo | Placa, chasis, propietario | Sensible |
| Usuario del sistema | Email, rol, permisos | Interno |

### 8.2 Obligaciones del Sistema

| Obligación | Implementación |
|:---|:---|
| **Consentimiento informado** | Checkbox en registro de cliente: "Acepto el tratamiento de mis datos personales" |
| **Finalidad determinada** | Los datos solo se usan para la operación del taller |
| **Plazo de almacenamiento** | 5 años desde la última transacción (o hasta solicitud de cancelación) |
| **Derecho de cancelación** | Endpoint/cliente para solicitar baja de datos personales |
| **Seguridad** | Cifrado AES-256 en reposo, TLS 1.3 en tránsito |
| **Notificación de brechas** | Alerta al administrador si se detecta acceso no autorizado |

### 8.3 Registro ante ARP

Si el sistema maneja datos de más de 10,000 personas (multitenant → probablemente sí), el operador del sistema debe registrar el **Banco de Datos Personales** ante la **Autoridad Nacional de Protección de Datos Personales (ANPD)** del MINJUS.

---

## 9. Documentos Contractuales y Comerciales

### 9.1 Cotización

**Valor legal:** Documento comercial (no tributario). No requiere firma digital certificada.

| Elemento | Valor Legal |
|:---|:---|
| Validez de oferta | 15 días (por defecto, configurable) |
| Aceptación del cliente | Firma manuscrita o digital simple |
| Conversión a OT | Automática si es aprobada |

### 9.2 Orden de Trabajo (OT)

**Valor legal:** Documento contractual entre el taller y el cliente.

Debe contener:
- Datos del taller (RUC, razón social, dirección)
- Datos del cliente (nombres, DNI/RUC, contacto)
- Datos del vehículo (placa, marca, modelo, kilometraje)
- Descripción del servicio solicitado
- Repuestos e insumos a utilizar (con precios)
- Horas de mano de obra estimadas
- Costo total estimado
- Fecha de ingreso y fecha prometida de entrega
- Firma del cliente (aceptación del servicio)

### 9.3 Acta de Ingreso (Check-in Digital)

**Valor legal:** Documento probatorio del estado del vehículo al ingreso.

Base legal: **Ley 27269 (Firma Digital)** si se firma con certificado digital. Si es firma manuscrita digitalizada, tiene valor como **documento privado**.

Debe contener:
```
ACTA DE INGRESO — TALLER MECÁNICO
─────────────────────────────────
Taller: [RUC + Razón Social]
Cliente: [Nombre + DNI]
Vehículo: [Placa + Marca + Modelo + Año]
Kilometraje actual: [km]

ESTADO DECLARADO DEL VEHÍCULO
- Carrocería: [Fotos × 4]
- Tablero: [Foto con kilometraje visible]
- Interior: [Fotos × 2]
- Llantas: [Estado visual]
- Combustible: [1/4, 1/2, 3/4, Full]

OBSERVACIONES DEL CLIENTE:
"________________________________"

DAÑOS PREEXISTENTES:
[Fotos de daños + descripción]

ACEPTACIÓN:
Yo, [Cliente], declaro conforme el estado registrado
y autorizo el inicio de los trabajos.

─────────────────────────────────
Firma del Cliente       Fecha/Hora
[Sello digital del taller]
```

### 9.4 Garantía

Base legal: **Código Civil Peruano, Código de Protección al Consumidor**

| Aspecto | Detalle |
|:---|:---|
| Plazo mínimo legal | 30 días para servicios (puede extenderse) |
| Repuestos nuevos | 6 meses mínimo (proveedor) |
| Cobertura | Mano de obra y repuestos defectuosos |
| Exclusión | Desgaste normal por uso, mal uso del cliente |
| Documentación | OT de garantía vinculada a la OT original |

---

## 10. Retención y Archivo Legal

### 10.1 Plazos de Retención

| Documento | Plazo Mínimo | Base Legal |
|:---|:---:|:---|
| Comprobantes Electrónicos (XML+CDR) | 5 años | Código Tributario |
| Libros Electrónicos (PLE) | 5 años desde la última anotación | Ley IGV |
| Boletas de Pago | 5 años | Código Civil / Laboral |
| Planilla Electrónica (PLAME) | 5 años | SUNAT |
| Contratos de Trabajo | 5 años desde el cese | Ley Laboral |
| Actas de Ingreso (Check-in) | 2 años (o hasta que prescriba acción legal) | Código Civil |
| Cotizaciones / OT | 2 años | Código de Protección al Consumidor |
| Datos Personales | Hasta solicitud de cancelación o 5 años | Ley 29733 |

### 10.2 Estructura de Almacenamiento

```
/legal-storage/
├── {tenant_id}/
│   ├── invoices/
│   │   ├── F001/
│   │   │   ├── F001-00000001.xml       ← XML firmado
│   │   │   ├── F001-00000001-cdr.xml   ← CDR de SUNAT
│   │   │   ├── F001-00000001.pdf       ← Representación impresa
│   │   │   └── F001-00000001-meta.json ← Metadatos
│   │   ├── B001/
│   │   ├── FC01/
│   │   ├── BC01/
│   │   └── FD01/
│   ├── daily-summary/
│   │   ├── RC-2024-001.xml
│   │   └── RC-2024-001-cdr.xml
│   ├── ple/
│   │   ├── 2024/
│   │   │   ├── 140100-202401.txt  ← RVE enero
│   │   │   ├── 080100-202401.txt  ← Compras enero
│   │   │   └── 030100-202401.txt  ← Diario enero
│   │   └── 2025/
│   ├── payroll/
│   │   ├── 2024/
│   │   │   ├── boleta-202401-{worker-id}.pdf
│   │   │   └── plame-202401.txt
│   │   └── 2025/
│   ├── checkin/
│   │   └── {checkin-id}/
│   │       ├── acta-ingreso.pdf
│   │       └── fotos/
│   └── work-orders/
│       └── {ot-id}.pdf
```

### 10.3 Backup Legal

- Backup diario incremental de documentos legales
- Backup semanal completo exportable
- Verificación mensual de integridad de archivos (checksum SHA-256)
- Prueba de restauración trimestral

---

## 11. Auditoría y Trazabilidad Legal

### 11.1 Eventos Auditables Obligatorios

| Evento | Registro | Retención |
|:---|:---|:---|
| Emisión de comprobante | Usuario, fecha, XML hash | 5 años |
| Recepción de CDR | Estado, código error, timestamp | 5 años |
| Anulación de comprobante | Usuario, motivo, NC asociada | 5 años |
| Reenvío a SUNAT | Intento, error, timestamp | 5 años |
| Modificación de datos fiscales | Usuario, campo anterior, campo nuevo | 5 años |
| Cambio de certificado digital | Usuario, fecha vencimiento anterior, nuevo | 5 años |
| Generación de PLE | Mes, usuario, hash del archivo | 5 años |
| Acceso a datos personales | Usuario, cliente consultado, timestamp | 2 años |
| Registro/eliminación de trabajador | Usuario, datos del cambio | 5 años |

### 11.2 Estructura de la Traza de Auditoría

```sql
CREATE TABLE audit_log (
    id              UUID PRIMARY KEY,
    tenant_id       VARCHAR(20) NOT NULL,
    event_type      VARCHAR(50) NOT NULL,   -- ej: INVOICE_EMITTED, CDR_RECEIVED
    entity_type     VARCHAR(50) NOT NULL,   -- ej: INVOICE, WORKER, TENANT
    entity_id       VARCHAR(50) NOT NULL,   -- ID del registro afectado
    user_id         VARCHAR(50) NOT NULL,   -- Usuario que ejecutó la acción
    user_ip         VARCHAR(45),
    old_value       JSONB,                  -- Valor anterior (si aplica)
    new_value       JSONB,                  -- Valor nuevo (si aplica)
    metadata        JSONB,                  -- Datos adicionales (hash, error, etc.)
    created_at      TIMESTAMP DEFAULT NOW()
);

-- Índice para búsqueda legal
CREATE INDEX idx_audit_tenant_event
    ON audit_log (tenant_id, event_type, created_at DESC);
```

### 11.3 Inmutabilidad

- La tabla `audit_log` **solo permite INSERT** (no UPDATE ni DELETE)
- Los XML firmados y CDR **no se pueden modificar** después de almacenados
- Cualquier corrección legal debe hacerse emitiendo un nuevo comprobante (NC/ND)

---

## Resumen de Normativas Aplicables

| Norma | Descripción | Artículos Clave |
|:---|:---|:---|
| **Ley IGV (Decreto Supremo 055-99-EF)** | Impuesto a las ventas (18%) | Arts. 1-12 |
| **RS 188-2020/SUNAT** | Facturación Electrónica | Anexo 1-8 |
| **Ley 27269** | Firma y Certificados Digitales | Arts. 1-15 |
| **Ley 29733** | Protección de Datos Personales | Arts. 1-38 |
| **Resolución Contaduría 001-2020-EF** | Libros Electrónicos (PLE) | Arts. 1-20 |
| **Ley 28015** | MYPE y Régimen Laboral | Arts. 1-50 |
| **TUO del Código Tributario** | Obligaciones formales | Arts. 87-89 |
| **Código Civil Peruano** | Contratos, obligaciones | Arts. 1351-1410 |
| **Ley 29571 (Código Protección al Consumidor)** | Garantías, reclamos | Arts. 18-25 |
