# Sprint 01 — Juan Diego (Backend)

## Resumen

Responsable de los módulos de Productos, Inventario, Stock y Órdenes de Compra. **Completado al 100% y verificado en código.** Desde el Sprint 02 asume como **Frontend Lead** y **Líder de Figma**.

---

## Tareas

| # | Tarea | Estado | Endpoint | Verificado |
|:---:|:---|:---:|:---|:---:|
| 1 | Detalle producto por ID | ✅ | `GET /api/productos/{id}` | ProductoController.java |
| 2 | Actualizar producto | ✅ | `PUT /api/productos/{id}` | ProductoController.java |
| 3 | Crear producto | ✅ | `POST /api/productos` | ProductoController.java |
| 4 | Creación rápida de producto | ✅ | `POST /api/productos/creacion-rapida` | ProductoController.java |
| 5 | Ajustar stock de producto | ✅ | `PATCH /api/productos/{id}/stock` | ProductoController.java |
| 6 | Movimientos de un producto | ✅ | `GET /api/productos/{id}/movimientos` | ProductoController.java |
| 7 | Productos por tenant | ✅ | `GET /api/productos/tenant/{tenantId}` | ProductoController.java |
| 8 | Alertas de stock | ✅ | `GET /api/productos/stock-alertas` | ProductoController.java |
| 9 | Reporte de rotación | ✅ | `GET /api/productos/reporte-rotacion` | ProductoController.java |
| 10 | Buscar producto por código | ✅ | `GET /api/productos/codigo/{codigo}` | ProductoController.java |
| 11 | Productos por categoría | ✅ | `GET /api/productos/categoria/{categoriaId}` | ProductoController.java |
| 12 | Listar movimientos inventario | ✅ | `GET /api/inventario/movimientos` | InventarioController.java |
| 13 | Registrar movimiento inventario | ✅ | `POST /api/inventario/movimientos` | InventarioController.java |
| 14 | Detalle movimiento inventario | ✅ | `GET /api/inventario/movimientos/{id}` | InventarioController.java |
| 15 | Eliminar movimiento inventario | ✅ | `DELETE /api/inventario/movimientos/{id}` | InventarioController.java |
| 16 | Movimientos por tenant | ✅ | `GET /api/inventario/movimientos/tenant/{tenantId}` | InventarioController.java |
| 17 | Movimientos por producto | ✅ | `GET /api/inventario/movimientos/producto/{productoId}` | InventarioController.java |
| 18 | Crear orden de compra | ✅ | `POST /api/ordenes-compra` | OrdenCompraController.java |
| 19 | Recibir orden de compra | ✅ | `POST /api/ordenes-compra/{id}/recibir` | OrdenCompraController.java |
| 20 | Completar orden de compra | ✅ | `POST /api/ordenes-compra/{id}/completar` | OrdenCompraController.java |
| 21 | Aprobar orden de compra | ✅ | `POST /api/ordenes-compra/{id}/aprobar` | OrdenCompraController.java |
| 22 | Anular orden de compra | ✅ | `POST /api/ordenes-compra/{id}/anular` | OrdenCompraController.java |
| 23 | Detalle orden de compra | ✅ | `GET /api/ordenes-compra/{id}` | OrdenCompraController.java |
| 24 | Items de orden de compra | ✅ | `GET /api/ordenes-compra/{id}/items` | OrdenCompraController.java |
| 25 | Órdenes de compra por tenant | ✅ | `GET /api/ordenes-compra/tenant/{tenantId}` | OrdenCompraController.java |
| 26 | Reporte de gastos compras | ✅ | `GET /api/ordenes-compra/reporte-gastos` | OrdenCompraController.java |
| 27 | Órdenes de compra por proveedor | ✅ | `GET /api/ordenes-compra/proveedor/{proveedorId}` | OrdenCompraController.java |

---

## Auditoría de Código

| Controller | Archivo | Endpoints |
|:---|:---|:---:|
| ProductoController | `core/producto/infrastructure/adapters/in/http/ProductoController.java` | 11 |
| InventarioController | `core/inventario/infrastructure/adapters/in/http/InventarioController.java` | 6 |
| OrdenCompraController | `core/ordencompra/infrastructure/adapters/in/http/OrdenCompraController.java` | 10 |

---

## Estado Global

**Progreso:** 27/27 (100%) ✅

---

## Transición al Sprint 02

Juan Diego pasa al rol de **Frontend Lead** y **Líder de Figma** para el Sprint 02. Responsable de:
- Diseñar en Figma todas las pantallas del módulo de facturación
- Coordinar al equipo frontend (Breider, Angie, Bianca)
- Code review de componentes frontend
