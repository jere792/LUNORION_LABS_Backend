# Sprint 01 — Jeremy (Backend)

## Resumen

Responsable de los módulos de Clientes, Vehículos y Dashboard KPIs. **Todo completado y verificado en código.**

---

## Tareas

| # | Tarea | Estado | Endpoint | Verificado |
|:---:|:---|:---:|:---|:---:|
| 1 | Detalle cliente por ID | ✅ | `GET /api/clientes/{id}` | ClienteController.java |
| 2 | Actualizar cliente | ✅ | `PUT /api/clientes/{id}` | ClienteController.java |
| 3 | Listar clientes | ✅ | `GET /api/clientes` | ClienteController.java |
| 4 | Crear cliente | ✅ | `POST /api/clientes` | ClienteController.java |
| 5 | Desactivar cliente | ✅ | `POST /api/clientes/{id}/desactivar` | ClienteController.java |
| 6 | Activar cliente | ✅ | `POST /api/clientes/{id}/activar` | ClienteController.java |
| 7 | Rentabilidad del cliente | ✅ | `GET /api/clientes/{id}/rentabilidad` | ClienteController.java |
| 8 | Historial trabajos del cliente | ✅ | `GET /api/clientes/{id}/historial-trabajos` | ClienteController.java |
| 9 | Historial compras del cliente | ✅ | `GET /api/clientes/{id}/historial-compras` | ClienteController.java |
| 10 | Clientes por tenant | ✅ | `GET /api/clientes/tenant/{tenantId}` | ClienteController.java |
| 11 | Buscar cliente por documento | ✅ | `GET /api/clientes/documento/{numero}` | ClienteController.java |
| 12 | Detalle vehículo por ID | ✅ | `GET /api/vehiculos/{id}` | VehiculoController.java |
| 13 | Actualizar vehículo | ✅ | `PUT /api/vehiculos/{id}` | VehiculoController.java |
| 14 | Listar vehículos | ✅ | `GET /api/vehiculos` | VehiculoController.java |
| 15 | Crear vehículo | ✅ | `POST /api/vehiculos` | VehiculoController.java |
| 16 | Desactivar vehículo | ✅ | `POST /api/vehiculos/{id}/desactivar` | VehiculoController.java |
| 17 | Activar vehículo | ✅ | `POST /api/vehiculos/{id}/activar` | VehiculoController.java |
| 18 | Asignar vehículo a cliente | ✅ | `POST /api/vehiculos/{id}/asignar-cliente` | VehiculoController.java |
| 19 | Vehículos por tenant | ✅ | `GET /api/vehiculos/tenant/{tenantId}` | VehiculoController.java |
| 20 | Vehículos por cliente | ✅ | `GET /api/vehiculos/cliente/{clienteId}` | VehiculoController.java |
| 21 | Dashboard rentabilidad | ✅ | `GET /api/dashboard/rentabilidad` | DashboardController.java |
| 22 | Dashboard KPIs | ✅ | `GET /api/dashboard/kpis` | DashboardController.java |
| 23 | Dashboard exportar | ✅ | `GET /api/dashboard/exportar` | DashboardController.java |

---

## Auditoría de Código

| Controller | Archivo | Endpoints |
|:---|:---|:---:|
| ClienteController | `core/cliente/infrastructure/adapters/in/http/ClienteController.java` | 11 |
| VehiculoController | `core/vehiculo/infrastructure/adapters/in/http/VehiculoController.java` | 9 |
| DashboardController | `core/dashboard/infrastructure/adapters/in/http/DashboardController.java` | 3 |

---

## Estado Global

**Progreso:** 23/23 (100%) ✅
