# Sprint 01 — Tareas del Equipo

## Dominid — Gestor Fullstack

| # | Tarea | Tipo | Depende de |
|:---:|:---|---|:---:|
| 1 | Crear repo principal con ramas: `deployed`, `test`, `programacion` | Infra | — |
| 2 | Configurar protect rules (PRs requeridos, approvals) | Infra | #1 |
| 3 | Configurar CI/CD (build, test, lint) | Infra | — |
| 4 | Configurar entornos (producción, testing, desarrollo) | Infra | — |
| 5 | Coordinar y hacer code review de todo el equipo | Gestión | — |
| 6 | Documentar estándares del proyecto (convenciones, commit msg) | Docs | — |
| 7 | Auth: módulo de login + JWT + refresh token | Backend | #4 |
| 8 | Auth: middleware/filter de permisos PBAC | Backend | #7 |
| 9 | Auth: endpoints `/auth/login`, `/auth/refresh` | Backend | #7 |
| 10 | Endpoint `/auth/tenant-config` — devuelve logo_url, color_primario, color_secundario del tenant | Backend | #7 |
| 11 | Integration tests del Sprint 01 | Testing | #7, #8, #9 |

## Jeremy — Backend 

| # | Tarea | Endpoint | Depende de |
|:---:|---|---|:---:|
| 1 | Customers CRUD | `GET/POST /customers` | BD lista |
| 2 | Customer work history | `GET /customers/{id}/work-history` | #1, OTs listas |
| 3 | Vehicles CRUD | `GET/POST /vehicles` | BD lista |
| 4 | Assign vehicle to customer | `POST /vehicles/{id}/assign-customer` | #1, #3 |
| 5 | Vehicle service history | `GET /vehicles/{id}/history` | #3, OTs listas |
| 6 | Dashboard KPIs | `GET /dashboard/kpis` | OTs y stock funcionales |
| 7 | Unit tests de sus módulos | Testing | — |

## Juan Diego — Backend 

| # | Tarea | Endpoint | Depende de |
|:---:|---|---|:---:|
| 1 | Products CRUD | `GET/POST /products` | BD lista |
| 2 | Product quick-create | `POST /products/quick-create` | #1 |
| 3 | Stock entry | `POST /stock-movements/entry` | #1 |
| 4 | Stock exit | `POST /stock-movements/exit` | #1 |
| 5 | Stock history | `GET /stock-movements` | BD lista |
| 6 | Work Orders CRUD | `GET/POST /work-orders` | Clientes, vehículos, productos listos |
| 7 | Add insumo to OT | `POST /work-orders/{id}/insumos` | #6, #3 |
| 8 | Register labor hours | `POST /work-orders/{id}/labor` | #6 |
| 9 | Close OT | `POST /work-orders/{id}/close` | #7, #8 |
| 10 | Kanban board | `GET /work-orders/kanban` | #6 |
| 11 | Unit tests de sus módulos | Testing | — |

## Erick — Base de Datos

| # | Tarea | Entidad | Depende de |
|:---:|---|---|:---:|
| 1 | Diseñar esquema completo Sprint 01 | Todas las entidades | — |
| 2 | Migration: `tenant`, `usuario`, `permiso`, `usuario_permiso` | Auth | — |
| 3 | Migration: `cliente`, `vehiculo` | Clientes | — |
| 4 | Migration: `categoria_producto`, `producto` | Productos | — |
| 5 | Migration: `movimiento_stock` | Inventario | #4 |
| 6 | Migration: `orden_trabajo`, `ot_insumo`, `ot_mano_obra` | OTs | #3, #4 |
| 7 | Índices y constraints (FKs, unique, check) | Todas | #2-#6 |
| 8 | Seed data para desarrollo (admin default, categorías, productos demo) | Data | #2-#6 |
| 9 | Migration: agregar `logo_url`, `color_primario`, `color_secundario` a tabla `tenant` | Tenant | #2 |
| 10 | Script de reset/reload de BD | Infra | — |

## Breider — Frontend (Shared)

| # | Componente | Prioridad | Depende de |
|:---:|:---|:---:|:---|
| 1 | **Data Table** — tabla genérica con sorting, paginación, acciones por fila | Alta | — |
| 2 | **Select / Autocomplete** — selector con búsqueda remota | Alta | — |
| 3 | **Search Bar** — búsqueda con debounce para listados | Alta | — |
| 4 | **Detail Card** — vista read-only de entidad | Media | — |
| 5 | **Toast / Snackbar** — feedback de éxito/error | Alta | — |

## Angie — Frontend (Layout)

| # | Componente | Prioridad | Depende de |
|:---:|:---|:---:|:---|
| 1 | **Sidebar / Navigation Menu** — menú lateral con permisos, usa logo y colores del tenant | Alta | 
| 2 | **Top Nav Bar** — barra superior con logo del tenant, usuario, logout | Alta | — |
| 3 | **Breadcrumb** — migas de pan para navegación | Media | — |
| 4 | **Confirmation Dialog / Modal** — cuadro de confirmación genérico | Alta | — |
| 6 | **Loading Spinner** — indicador de carga circular | Alta | — |

## Bianca — Frontend (Formularios y Data Display)

| # | Componente | Prioridad | Depende de |
|:---:|:---|:---:|:---|
| 1 | **Form Field** — wrapper label + input + validación | Alta | — |
| 2 | **Page Header** — título + botones de acción | Alta | — |
| 3 | **Status Badge / Chip** — indicador de estado con color | Alta | — |
| 4 | **Empty State** — placeholder para listas vacías | Media | — |
| 5 | **Stat Card / KPI Card** — tarjeta de indicador numérico | Alta | — |
| 6 | **Filter Panel** — panel de filtros avanzados (estado, fechas) | Media | — |

---

## Tareas Globales (Todo el Equipo)

| # | Tarea |
|:---:|:---|
| 1 | Hacer fork del repositorio de Dominid |
| 2 | Clonar el fork localmente |
| 3 | Agregar upstream: `git remote add upstream https://github.com/dominid/lunorion-labs-backend.git` |
| 4 | Crear rama `feature/<nombre>` desde `upstream/programacion` |
| 5 | Al terminar, PR desde `feature/<nombre>` al repo de Dominid rama `programacion` |

---

## Git Workflow — Forks + PRs

```
Repo de Dominid (upstream)
  ├── deployed     → producción
  ├── test         → staging / QA
  └── programacion → desarrollo (base para features)

Cada miembro (fork propio)
  └── main (sync con upstream/programacion)
       └── feature/login
       └── feature/customers-crud
       └── feature/data-table
            ↓ PR
       upstream/programacion
            ↓ merge
       upstream/test
            ↓ merge
       upstream/deployed
```

**Flujo:**

1. Cada miembro trabaja en su **fork** personal
2. Crear rama `feature/<nombre>` desde `upstream/programacion`
3. PR al repo de Dominid → rama `programacion`
4. Dominid revisa y aprueba el PR
5. Se mergea a `programacion` → luego a `test` → luego a `deployed`

---

## Views del Sprint 01 y sus Componentes

| View | Shared Components Requeridos |
|:---|:---|
| Login | Form Field, Loading Spinner |
| Dashboard | Stat Card / KPI Card, Page Header, Toast |
| Customer List | Data Table, Search Bar, Page Header, Empty State, Loading Skeleton |
| Customer Create/Edit | Form Field, Page Header, Breadcrumb, Loading Spinner, Toast |
| Customer Detail | Detail Card, Tab Navigation, Breadcrumb |
| Customer Work History | Data Table, Tab Navigation, Loading Skeleton |
| Vehicle List | Data Table, Search Bar, Page Header, Empty State, Loading Skeleton |
| Vehicle Create/Edit | Form Field, Page Header, Breadcrumb, Loading Spinner, Toast |
| Vehicle Detail | Detail Card, Breadcrumb, Loading Skeleton |
| Assign Vehicle Modal | Select / Autocomplete, Confirmation Dialog, Toast |
| Product List | Data Table, Search Bar, Page Header, Empty State, Loading Skeleton |
| Product Create/Edit | Form Field, Page Header, Breadcrumb, Loading Spinner, Toast |
| Product Quick-Create | Form Field, Loading Spinner, Toast |
| Stock Inquiry | Data Table, Search Bar, Detail Card, Page Header |
| Stock Entry/Exit | Form Field, Select / Autocomplete, Page Header, Toast |
| Stock Movement History | Data Table, Search Bar, Filter Panel, Page Header, Loading Skeleton |
| Work Order List | Data Table, Search Bar, Filter Panel, Status Badge, Page Header, Empty State |
| Work Order Create/Edit | Form Field, Select / Autocomplete, Page Header, Breadcrumb, Toast |
| Work Order Detail | Detail Card, Breadcrumb, Status Badge, Tab Navigation |
| Kanban Board | Status Badge, Loading Spinner |
| Add Insumo Modal | Select / Autocomplete (producto), Form Field, Toast |
| Register Labor Form | Form Field, Toast |
| Close OT Confirmation | Confirmation Dialog, Detail Card (resumen), Toast |

## Orden Sugerido de Implementación (Frontend)

1. **Sidebar + Top Nav Bar** (Angie) — estructura base de navegación
2. **Form Field + Page Header** (Bianca) — base de formularios y páginas
3. **Data Table + Search Bar** (Breider) — base de listados
4. **Toast + Loading Spinner** (Breider/Angie) — feedback visual
5. **Status Badge + Empty State** (Bianca) — estados visuales
6. **Select / Autocomplete** (Breider) — selectores con búsqueda
7. **Confirmation Dialog** (Angie) — modales de confirmación
8. **Detail Card + Breadcrumb** (Breider/Angie) — vistas de detalle
9. **Stat Card / KPI Card** (Bianca) — dashboard
10. **Tab Navigation** (Bianca) — pestañas en perfiles
11. **Loading Skeleton** (Angie) — carga visual
12. **Filter Panel** (Bianca) — filtros avanzados
13. **Quick-Create Product Modal** (Breider) — creación inline

---

## Dependencias entre Áreas

```
Erick (BD)
  ├── Jeremy (Backend) — clientes, vehículos, dashboard
  ├── Juan Diego (Backend) — productos, stock, OTs
  └── Frontend (todos) — datos para componentes

Jeremy + Juan Diego (Backend)
  └── Breider, Angie, Bianca (Frontend) — APIs para consumir

Dominid (Gestor)
  ├── Repo base + CI/CD + entornos
  └── Code review + coordinación general
```
