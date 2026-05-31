# Historias de Usuario — LUNORION LABS

59 historias de usuario en primera persona, organizadas por épica. Cada historia expresa la necesidad del usuario y el permiso PBAC asociado.

---

## Épica 1 — Inventario & Stock (6 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-01 | Quiero registrar el ingreso de mercadería al depósito para llevar un control exacto de lo que entra. | `INVENTARIO_REGISTRAR_INGRESO` |
| US-02 | Quiero registrar egresos de productos (venta o consumo en servicio) para saber en todo momento qué quedó disponible. | `INVENTARIO_REGISTRAR_EGRESO` |
| US-03 | Quiero consultar el stock actual de cualquier producto en tiempo real para no interrumpir un servicio por falta de insumos. | `INVENTARIO_VER_STOCK` |
| US-04 | Quiero recibir alertas automáticas cuando un producto alcance su stock mínimo para reponer a tiempo sin frenar la operación. | `INVENTARIO_VER_ALERTAS` |
| US-05 | Quiero ver el historial completo de movimientos de un producto (entradas, salidas, fechas, usuario responsable) para auditar cualquier diferencia. | `INVENTARIO_VER_HISTORIAL` |
| US-06 | Quiero un panel con los productos de mayor rotación y los de baja circulación para optimizar compras y liberar capital inmovilizado. | `INVENTARIO_VER_REPORTES` |

---

## Épica 2 — Compras & Proveedores (4 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-07 | Quiero registrar una orden de compra con datos del proveedor, productos, cantidades y precios para respaldar cada adquisición. | `COMPRA_CREAR_ORDEN` |
| US-08 | Quiero que al confirmar la recepción de una compra el stock del inventario se actualice automáticamente para evitar doble carga manual y errores. | `COMPRA_RECIBIR_ORDEN` |
| US-09 | Quiero visualizar el gasto total en compras por período (semana, mes) para controlar los costos operativos del taller. | `COMPRA_VER_REPORTE_GASTOS` |
| US-10 | Quiero mantener un registro de proveedores con nombre, rubro, datos de contacto y condiciones comerciales para agilizar los pedidos de reposición. | `PROVEEDOR_GESTIONAR` |

---

## Épica 3 — Ventas (6 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-11 | Quiero registrar una venta de mostrador con búsqueda rápida por nombre, marca o código y que el stock se descuente al instante. | `VENTA_REGISTRAR_MOSTRADOR` |
| US-12 | Quiero emitir boleta de venta electrónica al consumidor final directamente desde el sistema, sin necesidad de otro software. | `VENTA_EMITIR_BOLETA` |
| US-13 | Quiero emitir factura electrónica (CF/B2B) cuando el cliente requiere crédito fiscal, completando los datos fiscales necesarios. | `VENTA_EMITIR_FACTURA` |
| US-14 | Quiero generar una nota de crédito electrónica asociada a una boleta o factura previa para corregir montos, registrar devoluciones o aplicar descuentos posteriores. | `VENTA_EMITIR_NOTA_CREDITO` |
| US-15 | Quiero ver el total facturado por día, semana y mes discriminado por boleta, factura y nota de crédito para medir el rendimiento real del negocio. | `VENTA_VER_REPORTE_FACTURACION` |
| US-16 | Quiero consultar el historial de compras de un cliente para ofrecerle productos relevantes y generar recurrencia. | `VENTA_VER_HISTORIAL_CLIENTE` |

---

## Épica 4 — Cotizaciones (4 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-17 | Quiero generar una cotización detallada con repuestos, insumos y mano de obra para que el cliente autorice el trabajo con conocimiento pleno del costo. | `COTIZACION_CREAR` |
| US-18 | Quiero enviar la cotización al cliente por WhatsApp o email en formato legible para acelerar la aprobación sin que tenga que acercarse al taller. | `COTIZACION_ENVIAR_CLIENTE` |
| US-19 | Quiero que una cotización aprobada se convierta automáticamente en una orden de trabajo para no recargar los datos manualmente. | `COTIZACION_CONVERTIR_OT` |
| US-20 | Quiero ver el historial de cotizaciones emitidas (pendientes, aprobadas, rechazadas) para medir la tasa de conversión y hacer seguimiento comercial. | `COTIZACION_VER_HISTORIAL` |

---

## Épica 5 — Órdenes de Trabajo (5 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-21 | Quiero crear una orden de trabajo vinculando cliente, vehículo, tipo de servicio y técnico asignado para que todo el taller sepa qué se está haciendo y por quién. | `OT_CREAR` |
| US-22 | Quiero que al agregar repuestos e insumos a una orden de trabajo se descuenten automáticamente del inventario. | `OT_GESTIONAR_INSUMOS` |
| US-23 | Quiero registrar las horas de mano de obra insumidas en cada orden para que el taller las facture correctamente. | `OT_GESTIONAR_MANO_OBRA` |
| US-24 | Quiero ver el estado de cada orden (pendiente, en progreso, en revisión, cerrado) en un tablero kanban para controlar el flujo diario. | `OT_VER_TABLERO` |
| US-25 | Quiero que al cerrar una orden de trabajo se calcule automáticamente el total (repuestos + mano de obra) y esté listo para facturar. | `OT_CERRAR` |

---

## Épica 6 — Citas & Reservas (3 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-26 | Quiero agendar una cita para un cliente indicando vehículo, tipo de servicio, fecha y hora para organizar la carga de trabajo del taller. | `CITA_CREAR` |
| US-27 | Quiero ver la disponibilidad de técnicos y boxes en el calendario de citas para no sobreasignar recursos. | `CITA_VER_CALENDARIO` |
| US-28 | Quiero que el sistema envíe un recordatorio automático al cliente (WhatsApp o email) 24 horas antes de su cita para reducir inasistencias. | `CITA_CONFIGURAR_NOTIFICACIONES` |

---

## Épica 7 — Vehículos (3 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-29 | Quiero crear una ficha por cada vehículo con patente, marca, modelo, año, número de chasis y kilometraje para identificarlo de manera unívoca. | `VEHICULO_CREAR` |
| US-30 | Quiero consultar el historial completo de servicios realizados a un vehículo (fecha, tipo de trabajo, repuestos, kilometraje, costo) para tener contexto antes de un nuevo diagnóstico. | `VEHICULO_VER_HISTORIAL` |
| US-31 | Quiero que un cliente pueda tener múltiples vehículos asociados a su ficha y que cada uno mantenga su propio historial independiente. | `VEHICULO_VINCULAR_CLIENTE` |

---

## Épica 8 — Clientes (4 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-32 | Quiero registrar un cliente con nombre, documento, teléfono, email y dirección para centralizar sus datos de contacto. | `CLIENTE_CREAR` |
| US-33 | Quiero consultar desde la ficha del cliente el historial completo de trabajos realizados a cada uno de sus vehículos para entender su relación con el taller. | `CLIENTE_VER_HISTORIAL_TRABAJOS` |
| US-34 | Quiero consultar desde la ficha del cliente el historial de compras de mostrador para identificar qué productos consume con frecuencia y ofrecerle reposición proactiva. | `CLIENTE_VER_HISTORIAL_COMPRAS` |
| US-35 | Quiero ver el total facturado acumulado por cliente para identificar a los de mayor valor y tomar acciones de fidelización. | `CLIENTE_VER_RENTABILIDAD` |

---

## Épica 9 — Técnicos (2 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-36 | Quiero registrar a cada técnico con nombre, especialidades y datos de contacto para asignarlo correctamente a las órdenes de trabajo. | `TECNICO_CREAR` |
| US-37 | Quiero ver la carga de trabajo actual de cada técnico (órdenes asignadas, horas pendientes) para equilibrar la distribución y evitar cuellos de botella. | `TECNICO_VER_CARGA_TRABAJO` |

---

## Épica 10 — Check-in Digital (3 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-38 | Quiero que al ingresar un vehículo al taller se capturen fotos del tablero (kilometraje), exterior (4 vistas) e interior para dejar registro visual del estado de ingreso. | `CHECKIN_CAPTURAR_FOTOS` |
| US-39 | Quiero que el cliente firme digitalmente el acta de ingreso conforme con el estado del vehículo y los trabajos solicitados para cubrir al taller ante cualquier reclamo posterior. | `CHECKIN_FIRMAR_ACTA` |
| US-40 | Quiero que el check-in genere automáticamente la orden de trabajo con los datos ya capturados para no duplicar la carga. | `CHECKIN_CONVERTIR_OT` |

---

## Épica 11 — Garantías (3 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-41 | Quiero reabrir una orden de trabajo ya cerrada para atender un reclamo de garantía, indicando el motivo y los repuestos o mano de obra necesarios para resolverlo. | `GARANTIA_REABRIR_OT` |
| US-42 | Quiero que los productos y horas utilizados en una garantía queden registrados con costo cero para el cliente pero con el costo real visible internamente para el taller. | `GARANTIA_REGISTRAR_COSTOS` |
| US-43 | Quiero un reporte que muestre cuánto dinero perdió el taller por garantías en un período para evaluar calidad de trabajo y selección de repuestos. | `GARANTIA_VER_REPORTE` |

---

## Épica 12 — Caja & Arqueo (3 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-44 | Quiero registrar cada cobro indicando el método de pago (efectivo, transferencia, débito, crédito) al momento de facturar para que la caja refleje lo que realmente ingresó. | `CAJA_REGISTRAR_COBRO` |
| US-45 | Quiero ejecutar el cierre de caja diario que muestre el total facturado, el detalle por método de pago y el cálculo de diferencia contra el efectivo contado para detectar descuadres al instante. | `CAJA_EJECUTAR_CIERRE` |
| US-46 | Quiero visualizar un historial de cierres de caja con filtro por fecha para conciliar con el banco y el contador al cierre del mes. | `CAJA_VER_HISTORIAL` |

---

## Épica 13 — RRHH (3 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-47 | Quiero registrar la asistencia diaria de cada técnico y vendedor (hora de entrada, hora de salida) para controlar el presentismo. | `RRHH_REGISTRAR_ASISTENCIA` |
| US-48 | Quiero que el sistema calcule automáticamente las horas trabajadas por cada técnico a partir de las órdenes cerradas y las cruce con la asistencia registrada para validar productividad. | `RRHH_VER_PRODUCTIVIDAD` |
| US-49 | Quiero configurar reglas de comisión por tipo de servicio o producto vendido para que el sistema las calcule automáticamente sobre lo facturado por cada empleado. | `RRHH_CONFIGURAR_COMISIONES` |

---

## Épica 14 — Dashboard & Reportes (3 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-50 | Quiero un dashboard con KPIs en tiempo real: facturación del mes, cantidad de órdenes por estado, productos con stock crítico y servicios más realizados para tomar decisiones rápidas. | `DASHBOARD_VER_KPIS` |
| US-51 | Quiero ver la rentabilidad por tipo de servicio (ingreso menos costo de repuestos y mano de obra) para identificar los trabajos que más y menos dejan. | `REPORTE_VER_RENTABILIDAD` |
| US-52 | Quiero exportar reportes de ventas, compras, servicios y caja a Excel o PDF para compartirlos con el contador. | `REPORTE_EXPORTAR` |

---

## Épica 15 — Catálogo de Productos (3 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-53 | Quiero organizar los productos por categorías (lubricantes, filtros, repuestos, neumáticos) para encontrar rápido cualquier ítem. | `PRODUCTO_GESTIONAR_CATEGORIAS` |
| US-54 | Quiero que cada producto tenga código interno, descripción, marca, precio de compra sugerido, precio de venta y stock mínimo para unificar criterios en todo el taller. | `PRODUCTO_CREAR` |
| US-55 | Quiero poder dar de alta un producto nuevo directamente desde la pantalla de compras o de ventas para no interrumpir el flujo de trabajo. | `PRODUCTO_CREAR_RAPIDO` |

---

## Épica 16 — Multitenant & Administración (4 US)

| ID | Historia de Usuario | Permiso |
|:---|:---|:---|
| US-56 | Quiero registrar un nuevo taller (tenant) con sus datos comerciales, plan contratado y fecha de activación para onboardear clientes de forma autogestionada. | `SAAS_TENANT_CREAR` |
| US-57 | Quiero que los datos, productos, clientes y operaciones de mi taller estén completamente aislados de otros talleres en la misma plataforma. | *Regla de Arquitectura* |
| US-58 | Quiero crear usuarios y asignarles permisos específicos para que cada empleado acceda solo a lo que su función requiere. | `CONFIG_USUARIO_CREAR` |
| US-59 | Quiero un panel global con métricas consolidadas de todos los tenants (facturación total, tenants activos, en mora, consumo de recursos) para monitorear la salud del negocio. | `SAAS_VER_METRICAS` |
