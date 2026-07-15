# Sprint 02 — Jeremy (Backend + Fullstack)

## Resumen

Responsable de todos los endpoints REST de facturación (completados). Ahora toma **testing, WebSocket en tiempo real, documentación Swagger y API layer para frontend.** También cerró `dashboard/exportar` del Sprint 01.

---

## Sprint 01 + Sprint 02 REST — Completado ✅

| # | Tarea | Estado | Endpoint |
|:---:|:---|:---:|:---|
| 1 | Dashboard exportar (Sprint 01) | ✅ | `GET /api/dashboard/exportar` |
| 2 | Lista de ventas | ✅ | `GET /api/ventas` |
| 3 | Registrar venta | ✅ | `POST /api/ventas` |
| 4 | Detalle de venta | ✅ | `GET /api/ventas/{id}` |
| 5 | Emitir factura electrónica | ✅ | `POST /api/comprobantes` |
| 6 | Emitir boleta electrónica | ✅ | `POST /api/comprobantes/recibo` |
| 7 | Emitir nota de crédito | ✅ | `POST /api/comprobantes/nota-credito` |
| 8 | Emitir nota de débito | ✅ | `POST /api/comprobantes/nota-debito` |
| 9 | Lista de comprobantes | ✅ | `GET /api/comprobantes/tenant/{tenantId}` |
| 10 | Detalle comprobante | ✅ | `GET /api/comprobantes/{id}` |
| 11 | Descargar XML firmado | ✅ | `GET /api/comprobantes/{id}/xml` |
| 12 | Descargar CDR | ✅ | `GET /api/comprobantes/{id}/cdr` |
| 13 | Reenviar comprobante | ✅ | `POST /api/comprobantes/{id}/reenviar` |
| 14 | Resumen diario de boletas | ✅ | `POST /api/comprobantes/resumen-diario` |
| 15 | Estado del RDB | ✅ | `GET /api/comprobantes/resumen-diario/{id}` |
| 16 | Reporte de facturación | ✅ | `GET /api/comprobantes/reporte-facturacion` |
| 17 | Historial compras cliente | ✅ | `GET /api/ventas/clientes/{clienteId}/historial` |
| 18 | Generar PLE | ✅ | `GET /api/comprobantes/ple` |
| 19 | Descargar PLE | ✅ | `GET /api/comprobantes/ple/descargar` |

---

## Sprint 02 Ampliado — Nuevas Tareas

### Testing (P0)

| # | Tarea | Tipo | Detalle |
|:---:|:---|:---|:---|
| 20 | Unit tests: Clientes (todos los endpoints) | Testing | MockMvc + JUnit 5. Cobertura ≥ 80% |
| 21 | Unit tests: Vehículos (todos los endpoints) | Testing | Incluir casos de asignación y desactivación |
| 22 | Unit tests: Dashboard KPIs + exportar | Testing | Verificar cálculos de rentabilidad y formatos de exportación |
| 23 | Unit tests: Ventas (todos los endpoints) | Testing | Casos: stock insuficiente, cliente no existe, items vacíos |
| 24 | Unit tests: Comprobantes (todos los endpoints) | Testing | Casos: CDR aceptado, rechazado, timeout, reenvío, RDB, PLE |
| 25 | Integration tests: flujo completo facturación | Testing | Crear cliente → vehículo → venta → emitir factura → recibir CDR → consultar PLE |

### WebSocket (P1)

| # | Tarea | Canal | Detalle |
|:---:|:---|:---|:---|
| 26 | Dashboard KPIs en vivo | `/topic/dashboard/kpis` | `{ facturacionHoy, stockCritico, otsAbiertas, citasHoy }` — push cada 30s o en evento |
| 27 | Alertas stock crítico | `/topic/stock/alerts` | `{ productoId, nombre, stockActual, stockMinimo }` — al cruzar umbral mínimo |
| 28 | Kanban OTs actualizado | `/topic/work-orders/kanban` | `{ otId, numeroOt, estadoAnterior, estadoNuevo }` — al cambiar estado de OT |
| 29 | Notificaciones usuario | `/topic/notifications` | `{ tipo, titulo, mensaje, url }` — notificaciones generales |
| 30 | Configurar STOMP + WebSocket con JWT | Infra | Endpoint `/ws` con autenticación JWT, handshake interceptor |

### Documentación (P1)

| # | Tarea | Tipo | Detalle |
|:---:|:---|:---|:---|
| 31 | SpringDoc OpenAPI para todos los controllers de Jeremy | Docs | Anotaciones `@Operation`, `@ApiResponse`, `@Schema` en cada endpoint |
| 32 | Colección Bruno/Postman de todos los endpoints | Docs | Exportable `.json` organizado por módulo (auth, clientes, vehículos, ventas, comprobantes, dashboard) |
| 33 | Documentar modelos request/response con ejemplos | Docs | Schemas JSON en Swagger UI visibles para frontend |

### API Layer Frontend (P1 — si Breider sigue bloqueado)

| # | Tarea | Tipo | Detalle |
|:---:|:---|:---|:---|
| 34 | `ClientesHttpService` + `ClientesStore` | Frontend | Servicio Angular + estado para consumir los 11 endpoints |
| 35 | `VehiculosHttpService` + `VehiculosStore` | Frontend | Servicio Angular + estado para consumir los 9 endpoints |
| 36 | `VentasHttpService` + `VentasStore` | Frontend | Servicio Angular + estado para consumir los 6 endpoints |
| 37 | `ComprobantesHttpService` + `ComprobantesStore` | Frontend | Servicio Angular + estado para consumir los 19 endpoints |

---

## Dependencias

| # | Depende de | Responsable | Estado |
|:---|:---|:---|:---|
| 26-29 | Dominid: WebSocket CDR channel | Dominid | ⬜ |
| 30 | Ninguna | — | — |
| 34-37 | Estructura `features/` definida | Juan Diego | ⬜ |

---

## Estado Global

| Bloque | Tareas | Estado |
|:---|:---:|:---:|
| Sprint 01 + 02 REST | 19 | ✅ 100% |
| Testing | 6 | ⬜ 0% |
| WebSocket | 5 | ⬜ 0% |
| Documentación | 3 | ⬜ 0% |
| API Layer Frontend | 4 | ⬜ 0% |
| **Total** | **37** | **19/37 (51%)** |
