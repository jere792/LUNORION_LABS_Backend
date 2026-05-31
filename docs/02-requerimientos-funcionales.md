# Requerimientos Funcionales — LUNORION LABS

Los requerimientos funcionales se organizan por épica/módulo. Cada RF tiene un ID único y un permiso PBAC asociado.

---

## RF-01 a RF-06: Inventario & Stock

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-01** | Registrar el ingreso de mercadería al depósito para llevar un control exacto del stock. | `INVENTARIO_REGISTRAR_INGRESO` |
| **RF-02** | Registrar egresos de productos (por venta o consumo interno) actualizando el disponible. | `INVENTARIO_REGISTRAR_EGRESO` |
| **RF-03** | Consultar el stock actual de cualquier producto en tiempo real. | `INVENTARIO_VER_STOCK` |
| **RF-04** | Recibir alertas automáticas cuando un producto alcance o supere su stock mínimo configurado, notificando en tiempo real vía WebSocket. | `INVENTARIO_VER_ALERTAS` |
| **RF-05** | Visualizar el historial completo de movimientos de un producto (ingresos, egresos, ajustes) con trazabilidad. | `INVENTARIO_VER_HISTORIAL` |
| **RF-06** | Visualizar un panel con los productos de mayor y menor rotación para optimizar decisiones de compra. | `INVENTARIO_VER_REPORTES` |

---

## RF-07 a RF-10: Compras & Proveedores

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-07** | Registrar una orden de compra con datos del proveedor, productos, cantidades y precios acordados. | `COMPRA_CREAR_ORDEN` |
| **RF-08** | Actualizar el stock automáticamente al confirmar la recepción total o parcial de una orden de compra. | `COMPRA_RECIBIR_ORDEN` |
| **RF-09** | Visualizar el gasto total en compras por período (semana, mes, rango personalizado). | `COMPRA_VER_REPORTE_GASTOS` |
| **RF-10** | Mantener un registro de proveedores con datos de contacto, condiciones comerciales e historial de compras. | `PROVEEDOR_GESTIONAR` |

---

## RF-11 a RF-16: Ventas

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-11** | Registrar una venta de mostrador con descuento automático del stock en tiempo real. | `VENTA_REGISTRAR_MOSTRADOR` |
| **RF-12** | Emitir boleta de venta electrónica al consumidor final cumpliendo requisitos fiscales SUNAT, enviada vía SOAP con firma digital XML. | `VENTA_EMITIR_BOLETA` |
| **RF-13** | Emitir factura electrónica (CF o B2B) con datos fiscales completos del cliente, enviada vía SOAP a SUNAT y obteniendo CDR. | `VENTA_EMITIR_FACTURA` |
| **RF-14** | Generar nota de crédito electrónica vía SOAP asociada a una boleta o factura previa para anulación o descuento. | `VENTA_EMITIR_NOTA_CREDITO` |
| **RF-15** | Visualizar el total facturado por día, semana o mes, discriminado por tipo de comprobante. | `VENTA_VER_REPORTE_FACTURACION` |
| **RF-16** | Consultar el historial completo de compras de un cliente para reposición proactiva o análisis de consumo. | `VENTA_VER_HISTORIAL_CLIENTE` |

### RF-16b a RF-16e: Documentos Legales SUNAT (SOAP)

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-16b** | Emitir nota de débito electrónica vía SOAP para incrementar el monto de un comprobante ya emitido. | `VENTA_EMITIR_NOTA_DEBITO` |
| **RF-16c** | Generar resumen diario de boletas (RDB) para boletas no asignadas a RUC, enviado vía SOAP a SUNAT. | `VENTA_GENERAR_RESUMEN_DIARIO` |
| **RF-16d** | Almacenar y consultar el CDR (Comprobante de Recepción) de SUNAT para cada comprobante emitido, incluyendo código de error si fue rechazado. | `VENTA_VER_CDR` |
| **RF-16e** | Reenviar comprobantes rechazados por SUNAT tras corregir los datos observados. | `VENTA_REENVIAR_COMPROBANTE` |

### RF-16f: Libros Electrónicos (PLE)

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-16f** | Generar automáticamente el Registro de Ventas Electrónico (RVE) y Registro de Compras en formato SUNAT (PLE) para declaración mensual. | `VENTA_GENERAR_PLE` |

---

## RF-17 a RF-20: Cotizaciones

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-17** | Generar una cotización detallada con repuestos, insumos y mano de obra estimada. | `COTIZACION_CREAR` |
| **RF-18** | Enviar la cotización al cliente por WhatsApp o Email en formato legible (PDF). | `COTIZACION_ENVIAR_CLIENTE` |
| **RF-19** | Convertir una cotización aprobada automáticamente en una Orden de Trabajo. | `COTIZACION_CONVERTIR_OT` |
| **RF-20** | Visualizar el historial de cotizaciones emitidas filtrado por estado (pendientes, aprobadas, rechazadas). | `COTIZACION_VER_HISTORIAL` |

---

## RF-21 a RF-25: Órdenes de Trabajo (OT)

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-21** | Crear una OT vinculando cliente, vehículo, servicio a realizar y técnico asignado. | `OT_CREAR` |
| **RF-22** | Descontar del inventario automáticamente al agregar insumos/repuestos a una OT. | `OT_GESTIONAR_INSUMOS` |
| **RF-23** | Registrar horas de mano de obra insumidas por el técnico para su posterior facturación. | `OT_GESTIONAR_MANO_OBRA` |
| **RF-24** | Visualizar el estado de las OT en un tablero Kanban (pendiente, en progreso, revisión, cerrado). | `OT_VER_TABLERO` |
| **RF-25** | Calcular el total automático (repuestos + horas) al cerrar la OT para proceder al cobro. | `OT_CERRAR` |

---

## RF-26 a RF-28: Citas & Reservas

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-26** | Agendar una cita para un cliente indicando vehículo, servicio solicitado, fecha y hora. | `CITA_CREAR` |
| **RF-27** | Visualizar la disponibilidad de técnicos y boxes en un calendario interactivo. | `CITA_VER_CALENDARIO` |
| **RF-28** | Enviar recordatorio automático 24 horas antes de la cita al cliente vía WhatsApp/Email. | `CITA_CONFIGURAR_NOTIFICACIONES` |

---

## RF-29 a RF-31: Vehículos

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-29** | Crear una ficha de vehículo con datos de patente, chasis, motor, marca, modelo y kilometraje. | `VEHICULO_CREAR` |
| **RF-30** | Consultar el historial completo de servicios y reparaciones realizadas al vehículo. | `VEHICULO_VER_HISTORIAL` |
| **RF-31** | Asociar múltiples vehículos a la ficha de un solo cliente (ej. flota familiar o empresarial). | `VEHICULO_VINCULAR_CLIENTE` |

---

## RF-32 a RF-35: Clientes

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-32** | Registrar un cliente con datos personales, de contacto y documentos fiscales. | `CLIENTE_CREAR` |
| **RF-33** | Consultar el historial de trabajos realizados en todos los vehículos del cliente. | `CLIENTE_VER_HISTORIAL_TRABAJOS` |
| **RF-34** | Consultar el historial de compras de mostrador realizadas por el cliente. | `CLIENTE_VER_HISTORIAL_COMPRAS` |
| **RF-35** | Visualizar la facturación acumulada y rentabilidad por cliente para acciones de fidelización. | `CLIENTE_VER_RENTABILIDAD` |

---

## RF-36 a RF-37: Técnicos

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-36** | Registrar un técnico con sus especialidades (mecánica general, electricidad, chapa/pintura, etc.). | `TECNICO_CREAR` |
| **RF-37** | Visualizar la carga de trabajo actual de cada técnico (órdenes activas y horas asignadas). | `TECNICO_VER_CARGA_TRABAJO` |

---

## RF-38 a RF-40: Check-in Digital

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-38** | Capturar fotos del vehículo al ingreso (tablero, exterior, interior, daños preexistentes). | `CHECKIN_CAPTURAR_FOTOS` |
| **RF-39** | Firmar digitalmente el acta de ingreso como conformidad del estado del vehículo, generando PDF/A con firma digital con validez legal (Ley 27269). | `CHECKIN_FIRMAR_ACTA` |
| **RF-40** | Generar una OT automática desde el check-in con los datos y fotos capturados. | `CHECKIN_CONVERTIR_OT` |

---

## RF-41 a RF-43: Garantías

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-41** | Reabrir una OT cerrada para atender un reclamo de garantía, indicando el motivo. | `GARANTIA_REABRIR_OT` |
| **RF-42** | Registrar repuestos y horas de mano de obra en garantía a costo cero para el cliente. | `GARANTIA_REGISTRAR_COSTOS` |
| **RF-43** | Visualizar reporte de pérdidas del taller por atenciones en garantía. | `GARANTIA_VER_REPORTE` |

---

## RF-44 a RF-46: Caja & Arqueo

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-44** | Registrar cobros asociados a una factura/boleta indicando el método de pago. | `CAJA_REGISTRAR_COBRO` |
| **RF-45** | Ejecutar el cierre de caja diario calculando automáticamente los descuadres entre lo esperado y lo registrado. | `CAJA_EJECUTAR_CIERRE` |
| **RF-46** | Visualizar el historial de cierres de caja para conciliación mensual. | `CAJA_VER_HISTORIAL` |

---

## RF-47 a RF-49: RRHH

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-47** | Registrar la asistencia diaria (entrada y salida) del personal del taller. | `RRHH_REGISTRAR_ASISTENCIA` |
| **RF-48** | Calcular la productividad cruzando datos de asistencia con horas registradas en OTs cerradas. | `RRHH_VER_PRODUCTIVIDAD` |
| **RF-49** | Configurar reglas de comisión por servicio o producto vendido aplicables a técnicos y vendedores. | `RRHH_CONFIGURAR_COMISIONES` |
| **RF-49b** | Generar boleta de pago electrónica mensual con haberes, descuentos, aportes ESSALUD/ONP y neto a pagar en PDF/A firmado digitalmente. | `RRHH_GENERAR_BOLETA_PAGO` |
| **RF-49c** | Consolidar datos para PLAME (Planilla Mensual de Pagos) — registro de trabajadores, ingresos, retenciones y aportes — para declaración SUNAT. | `RRHH_GENERAR_PLAME` |

---

## RF-50 a RF-52: Dashboard & Reportes

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-50** | Visualizar un dashboard de KPIs en tiempo real (facturación del día, stock crítico, OTs abiertas) con actualización vía WebSocket. | `DASHBOARD_VER_KPIS` |
| **RF-51** | Analizar la rentabilidad por tipo de servicio realizado (mantenimiento, reparación, diagnóstico). | `REPORTE_VER_RENTABILIDAD` |
| **RF-52** | Exportar reportes operativos y financieros a formato Excel y PDF. | `REPORTE_EXPORTAR` |

### RF-52b a RF-52d: Notificaciones en Tiempo Real (WebSocket)

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-52b** | Notificar al vendedor/cajero en tiempo real vía WebSocket cuando se confirme o rechace un comprobante en SUNAT (CDR recibido). | — |
| **RF-52c** | Notificar al técnico y asesor en tiempo real cuando se le asigne o reasigne una OT en el tablero Kanban. | — |
| **RF-52d** | Notificar al recepcionista en tiempo real cuando un cliente llegue para su cita agendada (check-in activado). | — |

---

## RF-53 a RF-55: Catálogo de Productos

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-53** | Organizar los productos mediante categorías jerárquicas. | `PRODUCTO_GESTIONAR_CATEGORIAS` |
| **RF-54** | Configurar la ficha completa del producto (código, marca, modelo, precios de compra/venta, stock mínimo). | `PRODUCTO_CREAR` |
| **RF-55** | Crear un producto rápido directamente desde las pantallas de compra o venta sin salir del flujo. | `PRODUCTO_CREAR_RAPIDO` |

---

## RF-56 a RF-59: Multitenant & Administración

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-56** | Registrar un nuevo taller (Tenant) en la plataforma y configurar su plan de suscripción. | `SAAS_TENANT_CREAR` |
| **RF-57** | Garantizar aislamiento estricto de datos entre talleres — ningún tenant puede acceder a datos de otro. | *Regla de Arquitectura Core* |
| **RF-58** | Gestionar usuarios internos del taller y asignar roles con permisos específicos (PBAC). | `CONFIG_USUARIO_CREAR` |
| **RF-59** | Visualizar un panel global con métricas consolidadas de todos los Tenants (solo Super Admin). | `SAAS_VER_METRICAS` |

---

## RF-60 a RF-61: Autenticación y Seguridad (Base)

| ID | Requerimiento Funcional | Permiso |
|:---|:---|:---|
| **RF-60** | Autenticar usuarios por tenant con credenciales seguras (email + contraseña hasheada). | — |
| **RF-61** | Permitir recuperación y cambio de contraseña mediante enlace verificado por email. | — |

---

**Total: 71 Requerimientos Funcionales**
