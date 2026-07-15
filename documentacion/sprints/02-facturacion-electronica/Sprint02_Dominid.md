# Sprint 02 — Dominid (Gestor Fullstack)

## Resumen

Responsable de la infraestructura SOAP para facturación electrónica y configuración SUNAT. También cierra las tareas pendientes de permisos del Sprint 01 e inicia el Landing Page.

---

## Tareas

| # | Tarea | Estado | Detalle |
|:---:|:---|:---:|:---|
| 1 | [Sprint 01] Permisos: crear nuevo permiso | ⬜ | `POST /api/permisos` |
| 2 | [Sprint 01] Permisos: desactivar | ⬜ | `POST /api/permisos/{id}/desactivar` |
| 3 | Configurar módulo SOAP en Spring Boot | ⬜ | WebServiceTemplate, JAXB marshaller, keystore certificado digital SUNAT |
| 4 | Cliente SOAP: `sendBill` (factura, boleta, NC, ND) | ⬜ | Firma XAdES-EPES, base64, envío a SUNAT |
| 5 | Cliente SOAP: `sendSummary` (RDB) | ⬜ | Resumen diario de boletas |
| 6 | Cliente SOAP: `getStatus` (consulta de ticket) | ⬜ | Polling del estado de envío |
| 7 | Procesador de CDR | ⬜ | Decodificar ZIP, parsear XML, almacenar código respuesta |
| 8 | Job de reintentos con backoff | ⬜ | Reintentar comprobantes rechazados |
| 9 | WebSocket: notificar CDR recibido | ⬜ | Canal `/topic/invoices/status` |
| 10 | Endpoints configuración SUNAT | ⬜ | `GET/POST /api/configuracion-sunat` |
| 11 | Permisos PBAC: entidades Sprint 02 | ⬜ | VENTA_REGISTRAR_MOSTRADOR, VENTA_EMITIR_FACTURA, etc. |
| 12 | Configurar rate limiting por tenant para SOAP | ⬜ | 10 req/min sendBill, 5 req/min RDB |
| 13 | Code review de todo el equipo | ⬜ | Gestión |
| 14 | Landing Page de la plataforma | ⬜ | Página pública de LUNORION LABS |

---

## Nota de Auditoría

Los endpoints REST del Sprint 02 (ventas, comprobantes, PLE) ya están implementados por Jeremy y verificados en código. Las tareas de Dominid en este sprint se enfocan en la capa SOAP (comunicación real con SUNAT), WebSocket de notificaciones, y configuración. El backend de facturación REST está **100% completo.**

---

## Estado Global

**Progreso:** 0/14 (0%)
