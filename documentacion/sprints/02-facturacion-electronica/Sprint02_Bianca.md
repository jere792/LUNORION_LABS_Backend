# Sprint 02 — Bianca (Frontend Dashboard + Reportes)

## Resumen

Responsable de las pantallas de dashboard de facturación, reportes, RDB y PLE. **Debe primero terminar sus 5 componentes pendientes del Sprint 01** (FormField, PageHeader, StatusBadge, EmptyState, FilterPanel) antes de comenzar las pantallas del Sprint 02. StatCard/KPI Card ya existe en `features/dashboard/ui/stat-card/` pero debe moverse a `shared/ui/`.

---

### Sprint 01 Pendiente (P0 — Semana 1)

| # | Tarea | Estado | Componente | Verificado |
|:---:|:---|:---:|:---|:---|
| 1 | **Form Field** — wrapper label + input + validación | ⬜ | `shared/ui/form-field` | No existe en código |
| 2 | **Page Header** — título, subtítulo, botones de acción y buscador | ⬜ | `shared/ui/page-header` | No existe en código |
| 3 | **Status Badge / Chip** — indicador de estado por color | ⬜ | `shared/ui/status-badge` | No existe en código |
| 4 | **Empty State** — placeholder para listas vacías | ⬜ | `shared/ui/empty-state` | No existe en código |
| 5 | **Filter Panel** — panel de filtros avanzados | ⬜ | `shared/ui/filter-panel` | No existe en código |
| 6 | Mover StatCard a shared + refactorizar | ⬜ | De `features/dashboard/` a `shared/ui/stat-card` | Existe en ubicación incorrecta |

---

### Sprint 02 — Feature Screens

| # | Tarea | Estado | Detalle |
|:---:|:---|:---:|:---|
| 7 | Pantalla: **Dashboard de Facturación** | ⬜ | KPI Cards con datos de `/api/comprobantes/reporte-facturacion` |
| 8 | Pantalla: **Reporte de Facturación** | ⬜ | Gráfico de barras por día/mes, DateRangePicker |
| 9 | Pantalla: **Resumen Diario (RDB)** | ⬜ | Lista de resúmenes diarios, estado de envío |
| 10 | Pantalla: **Generar PLE** | ⬜ | Selección de período, botón generar, descargar TXT |
| 11 | Pantalla: **Historial de Compras del Cliente** | ⬜ | DataTable con datos de `/api/ventas/clientes/{id}/historial` |

---

## Auditoría de Código

Se verificó el directorio `/src/app/shared/ui/` y solo StatCard existe (en `features/dashboard/`, no en shared). Los 5 componentes restantes de Bianca no existen.

---

## Estado Global

**Sprint 01 pendiente:** 0/6 (0%) ❌  
**Sprint 02:** 0/5 (0%)
