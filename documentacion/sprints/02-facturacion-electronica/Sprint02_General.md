# Sprint 02 — Facturación Electrónica SUNAT

**Duración:** 3-4 semanas  
**Prioridad:** P2 (Legal) — Obligación fiscal SUNAT  
**Auditado:** Julio 2026

---

## Deuda Sprint 01 → Cargada al Sprint 02

Estas tareas del Sprint 01 no se completaron y **deben terminarse en la Semana 1 del Sprint 02.** Son bloqueantes para todo el trabajo de feature screens.

### Backend Pendiente (Dominid)

| # | Tarea | Endpoint | Prioridad |
|:---:|:---|:---|:---:|
| 1 | Crear nuevo permiso en catálogo | `POST /api/permisos` | P1 |
| 2 | Desactivar permiso | `POST /api/permisos/{id}/desactivar` | P1 |

### Frontend Pendiente — Breider

| # | Tarea | Componente | Bloquea a | Prioridad |
|:---:|:---|:---|:---|:---:|
| 3 | **Data Table** — tabla genérica con sorting, paginación, acciones | `shared/ui/data-table` | Angie, Bianca, Juan Diego | **P0** |
| 4 | **Select / Autocomplete** — selector con búsqueda remota | `shared/ui/select` | Angie, Bianca | **P0** |
| 5 | **Search Bar** — búsqueda con debounce | `shared/ui/search-bar` | Angie, Bianca | **P0** |
| 6 | Detail Card — vista read-only de entidad | `shared/ui/detail-card` | Angie | P1 |
| 7 | Toast / Snackbar — feedback éxito/error | `shared/ui/toast` | Angie, Bianca | P1 |

### Frontend Pendiente — Bianca

| # | Tarea | Componente | Bloquea a | Prioridad |
|:---:|:---|:---|:---|:---:|
| 8 | **Form Field** — wrapper label + input + validación | `shared/ui/form-field` | Angie, Juan Diego | **P0** |
| 9 | **Page Header** — título, subtítulo, botones de acción | `shared/ui/page-header` | Angie, Bianca | **P0** |
| 10 | Status Badge / Chip — indicador de estado por color | `shared/ui/status-badge` | Angie, Bianca | P1 |
| 11 | Empty State — placeholder para listas vacías | `shared/ui/empty-state` | Angie, Bianca | P1 |
| 12 | Filter Panel — panel de filtros avanzados | `shared/ui/filter-panel` | Bianca | P1 |
| 13 | Mover StatCard a shared + refactorizar | De `features/dashboard/` a `shared/ui/stat-card` | — | P2 |

---

## Objetivo del Sprint 02

### Semana 1 — Cerrar Deuda Sprint 01

- Breider: DataTable, Select, SearchBar (P0)
- Bianca: FormField, PageHeader (P0)
- Dominid: POST permisos + desactivar

### Semana 2-3 — Sprint 02 Backend (Dominid)

- Cliente SOAP SUNAT (sendBill, sendSummary, getStatus)
- Procesador CDR (decodificar ZIP, parsear XML)
- WebSocket notificaciones CDR
- Endpoints configuración SUNAT
- Rate limiting SOAP
- Landing Page

### Semana 2-4 — Sprint 02 Frontend (Todos)

| Responsable | Pantallas |
|:---|:---|
| **Juan Diego** | Figma de todo el módulo + Configuración SUNAT |
| **Breider** | Listado de Ventas, Registrar Venta, Detalle de Venta, Listado Comprobantes |
| **Angie** | Emitir Factura, Emitir Boleta, NC/ND, Detalle Comprobante, Reenviar |
| **Bianca** | Dashboard Facturación, Reporte Facturación, RDB, PLE, Historial Cliente |

---

## Estado Real del Backend (Auditado)

| Módulo | Controller | Endpoints | Estado |
|:---|:---|:---:|:---:|
| Ventas | VentaController | 6 | ✅ Completado (Jeremy) |
| Comprobantes | ComprobanteController | 19 | ✅ Completado (Jeremy) |
| Configuración SUNAT | — | 0 | ⬜ Pendiente (Dominid) |
| Permisos (Sprint 01) | UsuarioController | 1 GET | ⬜ Falta POST + desactivar (Dominid) |

---

## Épicas y RFs

| Épica | RFs | Backend REST | Backend SOAP |
|:---|:---|:---:|:---:|
| Ventas de Mostrador | RF-11 | ✅ | — |
| Factura Electrónica (01) | RF-13 | ✅ | ⬜ |
| Boleta Electrónica (03) | RF-12 | ✅ | ⬜ |
| Nota de Crédito (07) | RF-14 | ✅ | ⬜ |
| Nota de Débito (08) | RF-16b | ✅ | ⬜ |
| RDB | RF-16c | ✅ | ⬜ |
| CDR | RF-16d | ✅ | ⬜ |
| Reenvío | RF-16e | ✅ | ⬜ |
| PLE | RF-16f | ✅ | — |
| Reportes | RF-15 | ✅ | — |
| Historial Cliente | RF-16 | ✅ | — |

---

## Criterios de Aceptación

1. ⬜ Un usuario puede registrar una venta de mostrador con descuento automático de stock (backend ✅, frontend ⬜)
2. ⬜ Se puede emitir factura electrónica (tipo 01) contra SUNAT homologación (backend REST ✅, SOAP ⬜, frontend ⬜)
3. ⬜ Se puede emitir boleta electrónica (tipo 03) (backend REST ✅, SOAP ⬜, frontend ⬜)
4. ⬜ Se puede emitir nota de crédito (tipo 07) y nota de débito (tipo 08)
5. ⬜ El sistema notifica vía WebSocket cuando llega un CDR
6. ⬜ Se puede generar y enviar resumen diario de boletas (RDB)
7. ⬜ Se puede visualizar el reporte de facturación por período
8. ⬜ Se puede generar y descargar el PLE en formato TXT
9. ⬜ Dashboard muestra KPIs de facturación en tiempo real
10. ⬜ Flujo completo frontend integrado con backend (Angular)

---

## Bloqueantes Principales

```
DataTable (Breider) ────┐
Select (Breider) ───────┤
SearchBar (Breider) ────┼──→ Listados de ventas, comprobantes, clientes
FormField (Bianca) ─────┤
PageHeader (Bianca) ────┼──→ Formularios de emisión de factura/boleta
StatusBadge (Bianca) ───┘
Figma (Juan Diego) ────────→ Diseño visual de todas las pantallas
SOAP Client (Dominid) ──────→ Comunicación real con SUNAT
```
