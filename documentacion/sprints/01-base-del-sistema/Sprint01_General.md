# Sprint 01 — Base del Sistema

**Duración:** 3-4 semanas  
**Prioridad:** P0-P1 (Inmediata-Crítica)  
**Auditado:** Julio 2026 — Backend `/backend/` + Frontend `/LUNORION_LABS_Frontend/src/app/`

---

## Estado Global

| Área | Endpoints/Componentes | Implementados | % |
|:---|:---:|:---:|:---:|
| **Backend REST** | 169 endpoints | 167 | **99%** |
| **Frontend Layout** (Angie) | 5 componentes | 5 | **100%** |
| **Frontend Shared** (Breider) | 5 componentes | 0 | **0%** |
| **Frontend Features** (Bianca) | 6 componentes | 1 | **17%** |
| **Feature Screens** | 6 módulos | 2 (auth, dashboard) | **33%** |

---

## Backend — Por Miembro

| Miembro | Tareas | Completadas | Pendiente | % |
|:---|:---:|:---:|:---|:---:|
| **Dominid** | 12 | 10 | POST permisos, desactivar permiso | 83% |
| **Jeremy** | 23 | 23 | — | **100%** |
| **Juan Diego** | 27 | 27 | — | **100%** |
| **Erick** | 10 | 10 | — | **100%** |

### Backend Pendiente → Cargado al Sprint 02

| # | Tarea | Responsable | Endpoint |
|:---:|:---|:---|:---|
| 1 | Crear nuevo permiso en catálogo | Dominid | `POST /api/permisos` |
| 2 | Desactivar permiso | Dominid | `POST /api/permisos/{id}/desactivar` |

---

## Frontend — Por Miembro

| Miembro | Tareas | Completadas | Pendiente | % |
|:---|:---:|:---:|:---|:---:|
| **Angie** | 5 | 5 | — | **100%** |
| **Bianca** | 6 | 1 | 5 | **17%** |
| **Breider** | 5 | 0 | 5 | **0%** |

### Frontend Pendiente → Cargado al Sprint 02

| # | Tarea | Responsable | Componente | Prioridad |
|:---:|:---|:---|:---|:---:|
| 1 | Data Table | Breider | `shared/ui/data-table` | **P0** |
| 2 | Select / Autocomplete | Breider | `shared/ui/select` | **P0** |
| 3 | Search Bar | Breider | `shared/ui/search-bar` | **P0** |
| 4 | Detail Card | Breider | `shared/ui/detail-card` | P1 |
| 5 | Toast / Snackbar | Breider | `shared/ui/toast` | P1 |
| 6 | Form Field | Bianca | `shared/ui/form-field` | **P0** |
| 7 | Page Header | Bianca | `shared/ui/page-header` | **P0** |
| 8 | Status Badge / Chip | Bianca | `shared/ui/status-badge` | P1 |
| 9 | Empty State | Bianca | `shared/ui/empty-state` | P1 |
| 10 | Filter Panel | Bianca | `shared/ui/filter-panel` | P1 |

---

## Feature Screens

| Módulo | Estado | Endpoints Backend |
|:---|:---:|:---|
| Auth (login/register) | ✅ Implementado | 2 endpoints |
| Dashboard (home + KPIs) | ✅ Implementado | 3 endpoints |
| Clientes | ❌ No existe | 11 endpoints listos |
| Vehículos | ❌ No existe | 9 endpoints listos |
| Productos / Inventario | ❌ No existe | 17 endpoints listos |
| Órdenes de Trabajo | ❌ No existe | 14 endpoints listos |
| Facturación | ❌ No existe | 25 endpoints listos |
| Caja | ❌ No existe | 8 endpoints listos |
| Citas / Check-in | ❌ No existe | 23 endpoints listos |
| Cotizaciones | ❌ No existe | 11 endpoints listos |
| Proveedores | ❌ No existe | 5 endpoints listos |
| Técnicos | ❌ No existe | 7 endpoints listos |
| Planilla | ❌ No existe | 10 endpoints listos |

---

## Criterios de Aceptación Sprint 01

1. ✅ Un ADMIN puede loguearse y crear usuarios PUBLIC con permisos
2. ✅ Un usuario PUBLIC puede registrar un cliente con su vehículo (backend listo, frontend pendiente)
3. ✅ Un usuario PUBLIC puede crear productos y mover stock (backend listo, frontend pendiente)
4. ✅ Se puede crear una OT vinculando cliente + vehículo + técnico (backend listo, frontend pendiente)
5. ✅ El técnico puede agregar insumos (backend listo, frontend pendiente)
6. ✅ El técnico puede registrar horas de mano de obra (backend listo, frontend pendiente)
7. ✅ Al cerrar la OT se calcula el total automáticamente (backend listo, frontend pendiente)
8. ✅ El dashboard muestra OTs abiertas y stock (backend listo, frontend con datos hardcodeados)

---

## Nota de Auditoría

El backend está prácticamente completo (167/169 endpoints). **El cuello de botella está 100% en el frontend.** El Sprint 02 debe priorizar Breider y Bianca completando sus componentes shared antes de que Angie y Juan Diego puedan construir feature screens.
