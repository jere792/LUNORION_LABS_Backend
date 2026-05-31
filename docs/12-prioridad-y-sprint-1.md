# Prioridad de Implementación y Sprint 1 — LUNORION LABS

---

## Prioridad General

Basada en el flujo operativo real del taller automotriz peruano y las obligaciones legales (SUNAT).

| Prioridad | Épica / Módulo | CU Clave | RF Clave | Justificación |
|:---:|:---|:---|:---|:---|
| **P0 — Inmediata** | Autenticación + Clientes + Vehículos + Catálogo Productos | UC-60, UC-32, UC-29, UC-54, UC-55 | RF-60, RF-61, RF-32, RF-29, RF-53, RF-54, RF-55 | Base del sistema. Sin clientes, vehículos y productos no se puede operar nada. |
| **P1 — Crítica** | Inventario + Stock + Órdenes de Trabajo + Dashboard | UC-01 al UC-06, UC-21 al UC-25, UC-50 | RF-01 a RF-06, RF-21 a RF-25, RF-50 | Core del negocio: recibir vehículos, crear OT, consumir stock, ver estado del taller. |
| **P2 — Alta (Legal)** | Ventas + Facturación Electrónica SOAP + CDR + PLE | UC-11 al UC-14, UC-16b al UC-16f | RF-11 a RF-16f | Obligación fiscal SUNAT. Sin facturación el taller no puede cobrar legalmente. |
| **P3 — Media** | Cotizaciones + Citas + Check-in + Caja + Arqueo | UC-17 al UC-20, UC-26 al UC-28, UC-38 al UC-40, UC-44 al UC-46 | RF-17 a RF-20, RF-26 a RF-28, RF-38 a RF-40, RF-44 a RF-46 | Operativas: agilizan el flujo pero no bloquean el negocio principal. |
| **P4 — Media** | Compras + Proveedores | UC-07 al UC-10 | RF-07 a RF-10 | Reposición de stock. Se puede operar con ingresos manuales. |
| **P5 — Baja** | RRHH + Garantías + Reportes avanzados | UC-41 al UC-43, UC-47 al UC-49b, UC-49c, UC-51, UC-52 | RF-41 a RF-43, RF-47 a RF-49c, RF-51, RF-52 | Administración interna. No crítica para operación diaria ni facturación. |
| **P6 — SaaS** | Multitenant + Administración global | UC-56 al UC-59 | RF-56 a RF-59 | Para cuando haya múltiples talleres usando la plataforma. |

---

## Sprint 1 — Base del Sistema

**Duración sugerida:** 3-4 semanas  
**Objetivo:** Tener un MVP donde el taller pueda registrar clientes, vehículos, productos, y crear órdenes de trabajo con consumo de stock.

### Épicas y Casos de Uso del Sprint 1

| Épica | CU a Implementar | RF a Implementar |
|:---|:---|:---|
| **Seguridad / Auth** | UC-60 (login), UC-61 (recuperación) | RF-60, RF-61 |
| **Clientes** | UC-32 (crear), UC-33 (historial trabajos) | RF-32, RF-33 |
| **Vehículos** | UC-29 (crear), UC-30 (historial), UC-31 (vincular) | RF-29, RF-30, RF-31 |
| **Catálogo Productos** | UC-54 (crear), UC-55 (crear rápido) | RF-54, RF-55 |
| **Inventario & Stock** | UC-01 (ingreso), UC-02 (egreso), UC-03 (consultar), UC-05 (historial) | RF-01, RF-02, RF-03, RF-05 |
| **Órdenes de Trabajo** | UC-21 (crear), UC-22 (insumos), UC-23 (horas), UC-24 (tablero), UC-25 (cerrar) | RF-21, RF-22, RF-23, RF-24, RF-25 |
| **Dashboard** | UC-50 (KPIs básicos: OTs abiertas, stock) | RF-50 |

### Entidades a Crear (Base de Datos)

```
tenant, usuario, permiso, usuario_permiso
cliente, vehiculo
categoria_producto, producto, movimiento_stock
orden_trabajo, ot_insumo, ot_mano_obra
```

### Endpoints del Sprint 1

| Método | Path | Descripción |
|:---|:---|:---|
| `POST` | `/auth/login` | Login con JWT |
| `POST` | `/auth/refresh` | Refrescar token |
| `GET/POST` | `/customers` | CRUD clientes |
| `GET/POST` | `/vehicles` | CRUD vehículos |
| `POST` | `/vehicles/{id}/assign-customer` | Asignar vehículo a cliente |
| `GET/POST` | `/products` | CRUD productos |
| `POST` | `/products/quick-create` | Creación rápida |
| `POST` | `/stock-movements/entry` | Ingreso de stock |
| `POST` | `/stock-movements/exit` | Egreso de stock |
| `GET` | `/stock-movements` | Historial |
| `GET/POST` | `/work-orders` | CRUD OTs |
| `POST` | `/work-orders/{id}/insumos` | Agregar insumo |
| `POST` | `/work-orders/{id}/labor` | Registrar horas |
| `POST` | `/work-orders/{id}/close` | Cerrar OT |
| `GET` | `/work-orders/kanban` | Tablero Kanban |
| `GET` | `/dashboard/kpis` | KPIs básicos |

### Stack del Sprint 1

| Componente | Tecnología |
|:---|:---|
| Backend | Spring Boot + JPA + Security |
| Base de datos | PostgreSQL (local o Docker) |
| Frontend | Angular standalone components |
| API | REST JSON |
| Auth | JWT con permisos embebidos |

### Criterios de Aceptación del Sprint 1

1. Un ADMIN puede loguearse y crear usuarios PUBLIC con permisos
2. Un usuario PUBLIC puede registrar un cliente con su vehículo
3. Un usuario PUBLIC puede crear productos y mover stock (ingreso manual)
4. Se puede crear una OT vinculando cliente + vehículo + técnico
5. El técnico puede agregar insumos (descuenta stock automáticamente)
6. El técnico puede registrar horas de mano de obra
7. Al cerrar la OT se calcula el total automáticamente
8. El dashboard muestra OTs abiertas y stock de productos

### Lo que NO incluye el Sprint 1

- Facturación electrónica (Sprint 2)
- SOAP / SUNAT (Sprint 2)
- Caja / cobros (Sprint 3)
- Citas / check-in (Sprint 3)
- Compras (Sprint 4)
- RRHH (Sprint 5)
- Multitenant (Sprint 6)

---

## Roadmap de Sprints (Propuesta)

| Sprint | Prioridad | Módulos | Dependencias |
|:---:|:---:|:---|:---|
| **Sprint 1** | P0-P1 | Auth, Clientes, Vehículos, Productos, Inventario básico, OT, Dashboard | — |
| **Sprint 2** | P2 | Ventas, Facturación Electrónica SOAP, CDR, RDB | Sprint 1 (clientes, productos, stock) |
| **Sprint 3** | P3 | Caja, Cotizaciones, Citas, Check-in Digital | Sprint 1 (clientes, vehículos, OT) |
| **Sprint 4** | P4 | Compras, Proveedores, Alertas stock mínimo | Sprint 1 (productos, stock) |
| **Sprint 5** | P5 | RRHH, Garantías, Reportes avanzados, PLE | Sprint 2 (facturación), Sprint 1 (OT) |
| **Sprint 6** | P6 | Multitenant, Administración global, Certificados | Todos los anteriores |
