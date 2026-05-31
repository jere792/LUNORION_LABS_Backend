# Casos de Uso — LUNORION LABS

Diagramas y fichas detalladas de todos los casos de uso del sistema, organizados por módulo.

---

## Actores del Sistema

| Actor | Tipo | Descripción |
|:---|:---|:---|
| **Administrador (ADMIN)** | Interno | Dueño/gerente del taller o Super Admin SaaS. Acceso total, crea usuarios y asigna permisos. |
| **Usuario Público (PUBLIC)** | Interno | Cualquier empleado (cajero, técnico, vendedor, recepcionista). Accede solo a los permisos que el ADMIN le asignó. Un mismo usuario puede tener permisos de múltiples áreas. |
| **SUNAT (sistema externo)** | Externo | OSE/OSB de facturación electrónica — recibe comprobantes vía SOAP y responde CDR. |
| **Cliente (externo)** | Externo | Recibe servicios, cotizaciones, citas, comprobantes. No tiene acceso al sistema. |

> **PBAC:** Cada usuario PUBLIC tiene permisos asignados individualmente. No hay roles fijos. Por ejemplo, un usuario puede tener `VENTA_REGISTRAR_MOSTRADOR` + `CAJA_REGISTRAR_COBRO` + `CITA_CREAR` si el ADMIN se lo asigna.

---

## Diagramas PlantUML

Los diagramas están escritos en PlantUML. Copia el bloque en https://www.plantuml.com/plantuml/uml/ o en tu IDE con plugin PlantUML para renderizar.

---

### Diagrama General — Todos los Módulos

```plantuml
@startuml LUNORION_LABS_General

skinparam packageStyle rectangle
left to right direction

actor "Administrador\nDel Taller" as admin
actor "Recepcionista\n/ Asesor" as recep
actor "Técnico" as tec
actor "Vendedor" as vend
actor "Cajero" as caj
actor "Super Admin\n(SaaS)" as super
actor "SUNAT" as sunat
actor "Cliente" as cli

rectangle "Inventario & Stock" as inv {
  usecase (UC-01) as uc1  # "Registrar Ingreso\nMercadería"
  usecase (UC-02) as uc2  # "Registrar Egreso\nProducto"
  usecase (UC-03) as uc3  # "Consultar Stock"
}

rectangle "Ventas" as ventas {
  usecase (UC-11) as uc11 # "Registrar Venta\nMostrador"
  usecase (UC-12) as uc12 # "Emitir Boleta\nElectrónica"
  usecase (UC-13) as uc13 # "Emitir Factura\nElectrónica"
  usecase (UC-14) as uc14 # "Emitir Nota\nde Crédito"
}

rectangle "Órdenes de\nTrabajo" as ot {
  usecase (UC-21) as uc21 # "Crear OT"
  usecase (UC-24) as uc24 # "Ver Tablero\nKanban"
  usecase (UC-25) as uc25 # "Cerrar OT"
}

rectangle "Citas" as citas {
  usecase (UC-26) as uc26 # "Agendar Cita"
}

rectangle "RRHH" as rrhh {
  usecase (UC-47) as uc47 # "Registrar\nAsistencia"
  usecase (UC-49b) as uc49b # "Generar Boleta\nPago"
}

rectangle "Multitenant" as mt {
  usecase (UC-56) as uc56 # "Registrar\nTenant"
}

admin --> uc1
admin --> uc2
admin --> uc47
admin --> uc49b
vend --> uc3
vend --> uc11
vend --> uc12
vend --> uc13
vend --> uc14
recep --> uc21
recep --> uc26
tec --> uc24
tec --> uc25
caj --> uc11
super --> uc56
uc12 --> sunat
uc13 --> sunat
uc14 --> sunat

@enduml
```

---

### Módulo de Ventas + SUNAT (SOAP)

```plantuml
@startuml Ventas_SUNAT

actor Vendedor as v
actor Cajero as c
actor SUNAT_OSE as sunat
actor Cliente as cli

usecase (UC-11) as uc11 # "Registrar Venta\nMostrador"
usecase (UC-12) as uc12 # "Emitir Boleta\nElectrónica (SOAP)"
usecase (UC-13) as uc13 # "Emitir Factura\nElectrónica (SOAP)"
usecase (UC-14) as uc14 # "Emitir Nota de\nCrédito (SOAP)"
usecase (UC-16b) as uc16b # "Emitir Nota de\nDébito (SOAP)"
usecase (UC-16c) as uc16c # "Generar Resumen\nDiario Boletas"
usecase (UC-16d) as uc16d # "Consultar CDR\nSUNAT"
usecase (UC-16e) as uc16e # "Reenviar\nComprobante"

v --> uc11
v --> uc12
v --> uc13
v --> uc14
v --> uc16b
c --> uc11
uc12 .> sunat : <<SOAP>>
uc13 .> sunat : <<SOAP>>
uc14 .> sunat : <<SOAP>>
uc16b .> sunat : <<SOAP>>
uc16c .> sunat : <<SOAP>>
uc16d .> sunat : <<SOAP>>
uc16e .> sunat : <<SOAP>>
uc12 <. cli : Recibe\nBoleta
uc13 <. cli : Recibe\nFactura

@enduml
```

---

### Módulo de Órdenes de Trabajo (OT) + Check-in

```plantuml
@startuml OT_Checkin

actor "Asesor/Recepcionista" as asesor
actor "Técnico" as tec
actor "Cliente" as cli

usecase (UC-38) as uc38 # "Capturar Fotos\nCheck-in"
usecase (UC-39) as uc39 # "Firmar Acta\nIngreso"
usecase (UC-40) as uc40 # "Convertir Check-in\nen OT"
usecase (UC-21) as uc21 # "Crear OT"
usecase (UC-22) as uc22 # "Agregar Insumos\na OT"
usecase (UC-23) as uc23 # "Registrar Horas\nMano de Obra"
usecase (UC-24) as uc24 # "Ver Tablero\nKanban"
usecase (UC-25) as uc25 # "Cerrar OT"
usecase (UC-41) as uc41 # "Reabrir OT\n(Garantía)"

asesor --> uc38
asesor --> uc39
asesor --> uc40
asesor --> uc21
tec --> uc22
tec --> uc23
tec --> uc24
asesor --> uc25
asesor --> uc41
uc38 <. cli : Presenta\nVehículo
uc39 <. cli : Firma

@enduml
```

---

### Módulo de Citas + Calendario

```plantuml
@startuml Citas

actor "Recepcionista" as recep
actor "Cliente" as cli
actor "Sistema" as sys

usecase (UC-26) as uc26 # "Agendar Cita"
usecase (UC-27) as uc27 # "Ver Calendario\nDisponibilidad"
usecase (UC-28) as uc28 # "Enviar Recordatorio\nAutomático"

recep --> uc26
recep --> uc27
uc26 <. cli : Solicita\nCita
uc28 <. cli : Recibe\nRecordatorio
uc28 .> sys : <<CRON>>

@enduml
```

---

### Módulo de RRHH + Documentos Laborales

```plantuml
@startuml RRHH_Legal

actor "Administrador" as admin
actor "Trabajador" as trab
actor "SUNAT" as sunat

usecase (UC-47) as uc47 # "Registrar\nAsistencia"
usecase (UC-48) as uc48 # "Calcular\nProductividad"
usecase (UC-49) as uc49 # "Configurar\nComisiones"
usecase (UC-49b) as uc49b # "Generar Boleta\nPago Electrónica"
usecase (UC-49c) as uc49c # "Generar PLAME"

admin --> uc47
admin --> uc48
admin --> uc49
admin --> uc49b
admin --> uc49c
uc49b <. trab : Recibe\nBoleta
uc49c .> sunat : <<PLAME>>

@enduml
```

---

### Módulo Multitenant + Administración

```plantuml
@startuml Multitenant

actor "Super Admin\n(SaaS)" as super
actor "Admin del\nTaller" as admin

usecase (UC-56) as uc56 # "Registrar Nuevo\nTenant"
usecase (UC-57) as uc57 # "Aislar Datos\npor Tenant"
usecase (UC-58) as uc58 # "Gestionar\nUsuarios y Roles"
usecase (UC-59) as uc59 # "Ver Métricas\nGlobales"
usecase (UC-50) as uc50 # "Ver Dashboard\nKPIs"
usecase (UC-52) as uc52 # "Exportar Reportes\nExcel/PDF"

super --> uc56
super --> uc57
super --> uc59
admin --> uc58
admin --> uc50
admin --> uc52

@enduml
```

---

## Fichas Detalladas de Casos de Uso

---

### UC-01: Registrar Ingreso de Mercadería

| Campo | Detalle |
|:---|:---|
| **ID** | UC-01 |
| **Nombre** | Registrar ingreso de mercadería |
| **Actor** | Administrador del Taller |
| **Módulo** | Inventario & Stock |
| **Permiso** | `INVENTARIO_REGISTRAR_INGRESO` |
| **Descripción** | El administrador registra la entrada de productos al depósito, ya sea por compra a proveedor o devolución. |
| **Precondición** | Productos existentes en el catálogo (o crear rápido). Usuario autenticado con permiso. |
| **Postcondición** | Stock actualizado. Movimiento registrado en historial. |
| **Flujo Principal** | 1. El actor selecciona "Registrar Ingreso". 2. Selecciona tipo de ingreso (compra/devolución/ajuste). 3. Agrega productos, cantidades, y costo unitario. 4. Confirma el ingreso. 5. El sistema actualiza el stock y registra el movimiento. |
| **Flujo Alterno** | 3a. Si es orden de compra existente (UC-07), precarga productos. 3b. Si el producto no existe, redirige a creación rápida (UC-55). |
| **Reglas de Negocio** | El costo unitario no puede ser cero en compras. La cantidad debe ser > 0. |

---

### UC-02: Registrar Egreso de Producto

| Campo | Detalle |
|:---|:---|
| **ID** | UC-02 |
| **Nombre** | Registrar egreso de producto |
| **Actor** | Administrador del Taller |
| **Módulo** | Inventario & Stock |
| **Permiso** | `INVENTARIO_REGISTRAR_EGRESO` |
| **Descripción** | Registra la salida de productos por venta, consumo interno o ajuste. |
| **Precondición** | Stock suficiente del producto. |
| **Postcondición** | Stock disminuido. Movimiento registrado. |
| **Flujo Principal** | 1. Actor selecciona "Registrar Egreso". 2. Selecciona tipo (venta/consumo/ajuste/merma). 3. Busca y selecciona producto. 4. Ingresa cantidad. 5. Confirma. 6. Sistema descuenta stock. |
| **Flujo Alterno** | 2a. Si egreso por venta (UC-11), descuento automático al facturar. |
| **Reglas de Negocio** | Stock no puede ser negativo. Si alcanza stock mínimo, dispara alerta (UC-04). |

---

### UC-03: Consultar Stock

| Campo | Detalle |
|:---|:---|
| **ID** | UC-03 |
| **Nombre** | Consultar stock en tiempo real |
| **Actor** | Administrador, Vendedor |
| **Módulo** | Inventario & Stock |
| **Permiso** | `INVENTARIO_VER_STOCK` |
| **Descripción** | Consulta el stock disponible de cualquier producto. |
| **Precondición** | Usuario autenticado. |
| **Postcondición** | Visualización del stock actual. |
| **Flujo Principal** | 1. Actor busca producto por código, nombre o categoría. 2. Sistema muestra: stock actual, stock mínimo, ubicación, precio. 3. Actor puede ver historial de movimientos (UC-05). |

---

### UC-04: Alerta de Stock Mínimo

| Campo | Detalle |
|:---|:---|
| **ID** | UC-04 |
| **Nombre** | Recibir alerta de stock mínimo |
| **Actor** | Administrador del Taller (receptor) |
| **Módulo** | Inventario & Stock |
| **Permiso** | `INVENTARIO_VER_ALERTAS` |
| **Descripción** | El sistema notifica automáticamente cuando un producto alcanza o baja de su stock mínimo configurado. |
| **Precondición** | Producto configurado con stock mínimo. |
| **Postcondición** | Alerta visible en dashboard y notificación WebSocket enviada. |
| **Flujo Principal** | 1. Una transacción disminuye stock por debajo del mínimo. 2. Sistema genera alerta. 3. Notifica vía WebSocket en tiempo real. 4. Alerta visible en panel de notificaciones. |
| **Flujo Alterno** | 3a. Si usuario offline, alerta pendiente al reconectar. |

---

### UC-05: Ver Historial de Movimientos

| Campo | Detalle |
|:---|:---|
| **ID** | UC-05 |
| **Nombre** | Ver historial de movimientos |
| **Actor** | Administrador del Taller |
| **Módulo** | Inventario & Stock |
| **Permiso** | `INVENTARIO_VER_HISTORIAL` |
| **Descripción** | Consulta la trazabilidad completa de ingresos, egresos y ajustes de un producto. |
| **Flujo Principal** | 1. Selecciona producto. 2. Sistema muestra lista cronológica de movimientos: fecha, tipo, cantidad, documento asociado, usuario. 3. Actor puede filtrar por rango de fechas. |

---

### UC-06: Ver Reporte de Rotación

| Campo | Detalle |
|:---|:---|
| **ID** | UC-06 |
| **Nombre** | Visualizar reporte de rotación |
| **Actor** | Administrador / Gerente |
| **Módulo** | Inventario & Stock |
| **Permiso** | `INVENTARIO_VER_REPORTES` |
| **Descripción** | Muestra productos con mayor y menor rotación (más vendidos/consumidos). |

---

### UC-07: Registrar Orden de Compra

| Campo | Detalle |
|:---|:---|
| **ID** | UC-07 |
| **Nombre** | Registrar orden de compra |
| **Actor** | Administrador del Taller |
| **Módulo** | Compras & Proveedores |
| **Permiso** | `COMPRA_CREAR_ORDEN` |
| **Descripción** | Crea una orden de compra con productos, cantidades, precios y proveedor. |
| **Precondición** | Proveedor registrado (UC-10). |
| **Postcondición** | Orden de compra creada en estado "Pendiente". |
| **Flujo Principal** | 1. Actor selecciona "Nueva Orden de Compra". 2. Selecciona proveedor. 3. Agrega productos con cantidades y precio. 4. Confirma. 5. Sistema crea orden. |

---

### UC-08: Recibir Orden de Compra

| Campo | Detalle |
|:---|:---|
| **ID** | UC-08 |
| **Nombre** | Recibir orden de compra |
| **Actor** | Administrador del Taller |
| **Módulo** | Compras & Proveedores |
| **Permiso** | `COMPRA_RECIBIR_ORDEN` |
| **Descripción** | Confirma la recepción parcial o total de una orden de compra, actualizando stock automáticamente. |
| **Precondición** | Orden de compra en estado "Pendiente". |
| **Postcondición** | Stock actualizado. Orden pasa a "Recibida". |
| **Flujo Principal** | 1. Actor selecciona orden pendiente. 2. Ingresa cantidades recibidas (pueden ser parciales). 3. Sistema actualiza stock y registra ingreso. 4. Si es recepción total, orden cambia a "Completada". |

---

### UC-09: Ver Reporte de Gastos

| Campo | Detalle |
|:---|:---|
| **ID** | UC-09 |
| **Nombre** | Visualizar gasto en compras |
| **Actor** | Administrador / Gerente |
| **Módulo** | Compras & Proveedores |
| **Permiso** | `COMPRA_VER_REPORTE_GASTOS` |
| **Descripción** | Muestra el total gastado en compras por período, con desglose por proveedor. |

---

### UC-10: Gestionar Proveedores

| Campo | Detalle |
|:---|:---|
| **ID** | UC-10 |
| **Nombre** | Gestionar proveedores |
| **Actor** | Administrador del Taller |
| **Módulo** | Compras & Proveedores |
| **Permiso** | `PROVEEDOR_GESTIONAR` |
| **Descripción** | CRUD de proveedores: datos de contacto, RUC, condiciones, historial. |

---

### UC-11: Registrar Venta de Mostrador

| Campo | Detalle |
|:---|:---|
| **ID** | UC-11 |
| **Nombre** | Registrar venta de mostrador |
| **Actor** | Vendedor, Cajero |
| **Módulo** | Ventas |
| **Permiso** | `VENTA_REGISTRAR_MOSTRADOR` |
| **Descripción** | Registra una venta directa de repuestos/insumos con descuento automático de stock. |
| **Precondición** | Stock suficiente de los productos. |
| **Postcondición** | Stock actualizado. Transacción registrada. Opcionalmente, comprobante emitido. |
| **Flujo Principal** | 1. Actor busca y agrega productos al carrito. 2. Sistema muestra subtotal, IGV (18%), total. 3. Actor selecciona método de pago. 4. Si requiere comprobante → UC-12 o UC-13. 5. Si no → venta sin comprobante (solo control interno). 6. Sistema descuenta stock y registra. |
| **Reglas de Negocio** | El descuento máximo por defecto es 20% (configurable). |

---

### UC-12: Emitir Boleta Electrónica (SOAP)

| Campo | Detalle |
|:---|:---|
| **ID** | UC-12 |
| **Nombre** | Emitir boleta de venta electrónica |
| **Actor** | Vendedor |
| **Módulo** | Ventas / Legal |
| **Permiso** | `VENTA_EMITIR_BOLETA` |
| **Descripción** | Genera una boleta electrónica (tipo 03) para consumidor final y la envía a SUNAT vía SOAP. |
| **Precondición** | Venta registrada (UC-11). Certificado digital activo del tenant. |
| **Postcondición** | XML firmado almacenado. CDR recibido. Boleta notificada al cliente. |
| **Flujo Principal** | 1. Actor confirma emisión de boleta. 2. Sistema genera XML UBL 2.1 con datos de la venta. 3. Sistema firma XML con certificado digital (XAdES-EPES). 4. Sistema envía SOAP a SUNAT OSE. 5. SUNAT responde CDR. 6. Si aceptado → almacena XML+CDR, notifica WebSocket, envía PDF al cliente. 7. Si rechazado → muestra error, permite corregir y reenviar (UC-16e). |
| **Flujo Alterno** | 4a. Si SUNAT no responde → encola para reintento (máx. 3). 4b. Si no hay conexión → cola de respaldo. |

---

### UC-13: Emitir Factura Electrónica (SOAP)

| Campo | Detalle |
|:---|:---|
| **ID** | UC-13 |
| **Nombre** | Emitir factura electrónica |
| **Actor** | Vendedor |
| **Módulo** | Ventas / Legal |
| **Permiso** | `VENTA_EMITIR_FACTURA` |
| **Descripción** | Genera una factura electrónica (tipo 01) B2B con RUC del cliente. |
| **Precondición** | Venta registrada. Cliente tiene RUC activo (validado contra SUNAT). Certificado digital activo. |
| **Postcondición** | Factura aceptada por SUNAT. CDR almacenado. |
| **Flujo Principal** | Similar a UC-12. Diferencias: datos fiscales obligatorios del cliente (RUC, razón social, dirección). Serie Fxxx. |

---

### UC-14: Emitir Nota de Crédito (SOAP)

| Campo | Detalle |
|:---|:---|
| **ID** | UC-14 |
| **Nombre** | Emitir nota de crédito electrónica |
| **Actor** | Vendedor |
| **Módulo** | Ventas / Legal |
| **Permiso** | `VENTA_EMITIR_NOTA_CREDITO` |
| **Descripción** | Genera una NC (tipo 07) para anular total/parcial o aplicar descuento a un comprobante previo. |
| **Precondición** | Comprobante original existe y fue aceptado por SUNAT. |
| **Postcondición** | NC aceptada por SUNAT. (Para anulación total) comprobante original reversible. |
| **Flujo Principal** | 1. Actor selecciona comprobante original. 2. Selecciona motivo (anulación total, anulación parcial, descuento). 3. Ingresa monto. 4. Sistema genera NC referenciando comprobante original. 5. Envía vía SOAP. 6. Recibe CDR. |

---

### UC-16b: Emitir Nota de Débito (SOAP)

| Campo | Detalle |
|:---|:---|
| **ID** | UC-16b |
| **Nombre** | Emitir nota de débito electrónica |
| **Actor** | Vendedor |
| **Módulo** | Ventas / Legal |
| **Permiso** | `VENTA_EMITIR_NOTA_DEBITO` |
| **Descripción** | Genera una ND (tipo 08) para incrementar el monto de un comprobante previo. |

---

### UC-16c: Generar Resumen Diario de Boletas (SOAP)

| Campo | Detalle |
|:---|:---|
| **ID** | UC-16c |
| **Nombre** | Generar resumen diario de boletas |
| **Actor** | Sistema (automático, CRON) |
| **Módulo** | Ventas / Legal |
| **Permiso** | — |
| **Descripción** | Agrupa todas las boletas emitidas en el día y las envía a SUNAT como resumen diario. |
| **Precondición** | Hay boletas sin resumen del día anterior. |
| **Postcondición** | Resumen enviado y aceptado. Boletas pasan a estado "Comunicadas a SUNAT". |
| **Flujo Principal** | 1. CRON diario ejecuta a las 23:00. 2. Sistema agrupa boletas del día. 3. Genera XML de resumen. 4. Firma y envía vía SOAP. 5. Recibe CDR del resumen. |

---

### UC-16d: Consultar CDR

| Campo | Detalle |
|:---|:---|
| **ID** | UC-16d |
| **Nombre** | Consultar CDR de SUNAT |
| **Actor** | Vendedor, Administrador |
| **Módulo** | Ventas / Legal |
| **Permiso** | `VENTA_VER_CDR` |
| **Descripción** | Visualiza el CDR de cualquier comprobante emitido, incluyendo estado y código de error. |

---

### UC-16e: Reenviar Comprobante

| Campo | Detalle |
|:---|:---|
| **ID** | UC-16e |
| **Nombre** | Reenviar comprobante rechazado |
| **Actor** | Vendedor |
| **Módulo** | Ventas / Legal |
| **Permiso** | `VENTA_REENVIAR_COMPROBANTE` |
| **Descripción** | Corrige datos observados por SUNAT y reenvía el comprobante. |

---

### UC-17: Generar Cotización

| Campo | Detalle |
|:---|:---|
| **ID** | UC-17 |
| **Nombre** | Generar cotización |
| **Actor** | Recepcionista / Asesor de Servicio |
| **Módulo** | Cotizaciones |
| **Permiso** | `COTIZACION_CREAR` |
| **Descripción** | Crea cotización detallada con repuestos, insumos y mano de obra. |
| **Flujo Principal** | 1. Actor selecciona cliente y vehículo. 2. Agrega servicios/repuestos con cantidades y precios. 3. Sistema calcula total. 4. Actor envía al cliente (UC-18). |

---

### UC-18: Enviar Cotización al Cliente

| Campo | Detalle |
|:---|:---|
| **ID** | UC-18 |
| **Nombre** | Enviar cotización al cliente |
| **Actor** | Recepcionista / Asesor |
| **Módulo** | Cotizaciones |
| **Permiso** | `COTIZACION_ENVIAR_CLIENTE` |
| **Descripción** | Envía la cotización al cliente vía WhatsApp o Email en PDF. |

---

### UC-19: Convertir Cotización en OT

| Campo | Detalle |
|:---|:---|
| **ID** | UC-19 |
| **Nombre** | Convertir cotización aprobada en OT |
| **Actor** | Recepcionista / Asesor |
| **Módulo** | Cotizaciones / OT |
| **Permiso** | `COTIZACION_CONVERTIR_OT` |
| **Descripción** | Toma una cotización aprobada por el cliente y genera automáticamente una Orden de Trabajo. |
| **Precondición** | Cotización en estado "Aprobada por cliente". |
| **Postcondición** | OT creada con todos los items de la cotización. |
| **Flujo Principal** | 1. Actor selecciona cotización aprobada. 2. Confirma conversión. 3. Sistema genera OT con servicios, repuestos, técnico pendiente de asignar. 4. Redirige a la OT creada. |

---

### UC-20: Ver Historial de Cotizaciones

| Campo | Detalle |
|:---|:---|
| **ID** | UC-20 |
| **Nombre** | Ver historial de cotizaciones |
| **Actor** | Administrador / Gerente |
| **Módulo** | Cotizaciones |
| **Permiso** | `COTIZACION_VER_HISTORIAL` |
| **Descripción** | Lista de cotizaciones filtrables por estado (pendientes, aprobadas, rechazadas). |

---

### UC-21: Crear Orden de Trabajo

| Campo | Detalle |
|:---|:---|
| **ID** | UC-21 |
| **Nombre** | Crear ORden de Trabajo |
| **Actor** | Recepcionista / Asesor de Servicio |
| **Módulo** | Órdenes de Trabajo |
| **Permiso** | `OT_CREAR` |
| **Descripción** | Crea una OT vinculando cliente, vehículo, servicios y técnico. |
| **Precondición** | Cliente y vehículo registrados. |
| **Postcondición** | OT creada en estado "Pendiente" o "En Progreso". |
| **Flujo Principal** | 1. Actor selecciona cliente. 2. Selecciona vehículo del cliente. 3. Describe el servicio solicitado. 4. Asigna técnico (opcional). 5. Agrega insumos/repuestos estimados (opcional). 6. Confirma creación. |
| **Flujo Alterno** | 1a. Si viene de cotización (UC-19), datos precargados. 1b. Si viene de check-in (UC-40), datos y fotos precargados. |

---

### UC-22: Agregar Insumos a OT

| Campo | Detalle |
|:---|:---|
| **ID** | UC-22 |
| **Nombre** | Gestionar insumos en OT |
| **Actor** | Técnico |
| **Módulo** | OT / Inventario |
| **Permiso** | `OT_GESTIONAR_INSUMOS` |
| **Descripción** | El técnico agrega repuestos/insumos consumidos durante el servicio, descontando del inventario automáticamente. |
| **Precondición** | Stock suficiente. |
| **Postcondición** | Inventario actualizado. Costo de insumos registrado en la OT. |
| **Flujo Principal** | 1. Técnico abre OT activa. 2. Agrega producto con cantidad consumida. 3. Sistema descuenta stock y registra en OT. |

---

### UC-23: Registrar Horas de Mano de Obra

| Campo | Detalle |
|:---|:---|
| **ID** | UC-23 |
| **Nombre** | Registrar horas de mano de obra |
| **Actor** | Técnico |
| **Módulo** | OT |
| **Permiso** | `OT_GESTIONAR_MANO_OBRA` |
| **Descripción** | El técnico registra las horas trabajadas en la OT para su facturación. |
| **Flujo Principal** | 1. Técnico inicia temporizador al comenzar. 2. Pausa/retoma según sea necesario. 3. Al finalizar, registra horas totales. 4. Sistema calcula costo según tarifa del técnico. |

---

### UC-24: Ver Tablero Kanban

| Campo | Detalle |
|:---|:---|
| **ID** | UC-24 |
| **Nombre** | Visualizar tablero Kanban de OTs |
| **Actor** | Técnico, Recepcionista, Administrador |
| **Módulo** | OT |
| **Permiso** | `OT_VER_TABLERO` |
| **Descripción** | Tablero con columnas por estado de OT, actualizado en tiempo real vía WebSocket. |
| **Columnas** | Pendiente → En Progreso → En Revisión → Cerrado. |

---

### UC-25: Cerrar OT

| Campo | Detalle |
|:---|:---|
| **ID** | UC-25 |
| **Nombre** | Cerrar Orden de Trabajo |
| **Actor** | Recepcionista / Asesor |
| **Módulo** | OT |
| **Permiso** | `OT_CERRAR` |
| **Descripción** | Cierra la OT calculando total automático (repuestos + horas) y dejándola lista para facturación. |
| **Precondición** | Todos los insumos y horas registrados. |
| **Postcondición** | OT en estado "Cerrado". Total calculado. |
| **Flujo Principal** | 1. Actor verifica insumos y horas. 2. Sistema calcula total. 3. Actor confirma cierre. 4. Sistema cierra la OT. 5. (Opcional) Procede a facturación. |

---

### UC-26: Agendar Cita

| Campo | Detalle |
|:---|:---|
| **ID** | UC-26 |
| **Nombre** | Agendar cita |
| **Actor** | Recepcionista |
| **Módulo** | Citas & Reservas |
| **Permiso** | `CITA_CREAR` |
| **Descripción** | Agenda una cita para un cliente con vehículo, servicio, fecha y hora. |
| **Precondición** | Cliente registrado. Vehículo registrado o creado. |
| **Postcondición** | Cita creada. Recordatorio programado. |
| **Flujo Principal** | 1. Actor selecciona cliente. 2. Selecciona vehículo. 3. Selecciona servicio y técnico (opcional). 4. Elige fecha y hora disponibles. 5. Confirma. 6. Sistema agenda y programa recordatorio 24h antes. |

---

### UC-27: Ver Calendario

| Campo | Detalle |
|:---|:---|
| **ID** | UC-27 |
| **Nombre** | Ver disponibilidad en calendario |
| **Actor** | Recepcionista |
| **Módulo** | Citas & Reservas |
| **Permiso** | `CITA_VER_CALENDARIO` |
| **Descripción** | Visualiza la disponibilidad de técnicos y boxes en un calendario interactivo. |

---

### UC-28: Enviar Recordatorio Automático

| Campo | Detalle |
|:---|:---|
| **ID** | UC-28 |
| **Nombre** | Enviar recordatorio de cita |
| **Actor** | Sistema (CRON) |
| **Módulo** | Citas & Reservas |
| **Permiso** | `CITA_CONFIGURAR_NOTIFICACIONES` |
| **Descripción** | Envía recordatorio automático 24h antes de la cita vía WhatsApp/Email. |
| **Flujo Principal** | 1. CRON cada hora verifica citas con 24h de diferencia. 2. Envía notificación al cliente. |

---

### UC-29: Crear Ficha de Vehículo

| Campo | Detalle |
|:---|:---|
| **ID** | UC-29 |
| **Nombre** | Crear ficha de vehículo |
| **Actor** | Recepcionista |
| **Módulo** | Vehículos |
| **Permiso** | `VEHICULO_CREAR` |
| **Descripción** | Registra un nuevo vehículo con placa, chasis, motor, marca, modelo y kilometraje. |

---

### UC-30: Ver Historial del Vehículo

| Campo | Detalle |
|:---|:---|
| **ID** | UC-30 |
| **Nombre** | Consultar historial de servicios del vehículo |
| **Actor** | Recepcionista, Asesor |
| **Módulo** | Vehículos |
| **Permiso** | `VEHICULO_VER_HISTORIAL` |
| **Descripción** | Muestra todas las OTs y servicios realizados al vehículo. |

---

### UC-31: Vincular Vehículo a Cliente

| Campo | Detalle |
|:---|:---|
| **ID** | UC-31 |
| **Nombre** | Asociar vehículo a cliente |
| **Actor** | Administrador |
| **Módulo** | Vehículos / Clientes |
| **Permiso** | `VEHICULO_VINCULAR_CLIENTE` |
| **Descripción** | Asocia uno o varios vehículos a la ficha de un cliente. Un cliente puede tener múltiples vehículos. |

---

### UC-32: Registrar Cliente

| Campo | Detalle |
|:---|:---|
| **ID** | UC-32 |
| **Nombre** | Registrar cliente |
| **Actor** | Recepcionista, Vendedor |
| **Módulo** | Clientes |
| **Permiso** | `CLIENTE_CREAR` |
| **Descripción** | Registra un nuevo cliente con datos personales, de contacto y fiscales. |
| **Flujo Principal** | 1. Actor ingresa datos: nombres, DNI/RUC, teléfono, email, dirección. 2. Actor marca consentimiento de datos personales (Ley 29733). 3. Confirma. 4. Sistema registra cliente. |
| **Reglas de Negocio** | DNI o RUC único por tenant. Consentimiento obligatorio para almacenar datos. |

---

### UC-33: Ver Historial de Trabajos del Cliente

| Campo | Detalle |
|:---|:---|
| **ID** | UC-33 |
| **Nombre** | Consultar historial de trabajos del cliente |
| **Actor** | Recepcionista, Asesor |
| **Módulo** | Clientes |
| **Permiso** | `CLIENTE_VER_HISTORIAL_TRABAJOS` |
| **Descripción** | Muestra todas las OTs realizadas a todos los vehículos del cliente. |

---

### UC-34: Ver Historial de Compras del Cliente

| Campo | Detalle |
|:---|:---|
| **ID** | UC-34 |
| **Nombre** | Consultar historial de compras de mostrador |
| **Actor** | Vendedor |
| **Módulo** | Clientes |
| **Permiso** | `CLIENTE_VER_HISTORIAL_COMPRAS` |
| **Descripción** | Muestra el historial de compras directas (mostrador) del cliente. |

---

### UC-35: Ver Rentabilidad por Cliente

| Campo | Detalle |
|:---|:---|
| **ID** | UC-35 |
| **Nombre** | Visualizar facturación acumulada por cliente |
| **Actor** | Administrador / Gerente |
| **Módulo** | Clientes |
| **Permiso** | `CLIENTE_VER_RENTABILIDAD` |
| **Descripción** | Muestra total facturado, margen y rentabilidad por cliente. |

---

### UC-36: Registrar Técnico

| Campo | Detalle |
|:---|:---|
| **ID** | UC-36 |
| **Nombre** | Registrar técnico |
| **Actor** | Administrador |
| **Módulo** | Técnicos |
| **Permiso** | `TECNICO_CREAR` |
| **Descripción** | Registra un técnico con especialidades (mecánica general, electricidad, chapa/pintura, etc.). |

---

### UC-37: Ver Carga de Trabajo del Técnico

| Campo | Detalle |
|:---|:---|
| **ID** | UC-37 |
| **Nombre** | Visualizar carga de trabajo del técnico |
| **Actor** | Administrador / Gerente |
| **Módulo** | Técnicos |
| **Permiso** | `TECNICO_VER_CARGA_TRABAJO` |
| **Descripción** | Muestra las OTs activas y horas asignadas por técnico. |

---

### UC-38: Capturar Fotos de Check-in

| Campo | Detalle |
|:---|:---|
| **ID** | UC-38 |
| **Nombre** | Capturar fotos del vehículo al ingreso |
| **Actor** | Recepcionista |
| **Módulo** | Check-in Digital |
| **Permiso** | `CHECKIN_CAPTURAR_FOTOS` |
| **Descripción** | Toma fotos del vehículo desde la app móvil (PWA): tablero, exterior (4 vistas), interior, daños preexistentes. |
| **Flujo Principal** | 1. Actor inicia check-in. 2. Selecciona tipo de foto. 3. Usa cámara del dispositivo. 4. Sistema almacena foto con metadata (fecha, ubicación). 5. Repite hasta completar set. |

---

### UC-39: Firmar Acta de Ingreso

| Campo | Detalle |
|:---|:---|
| **ID** | UC-39 |
| **Nombre** | Firmar acta de ingreso |
| **Actor** | Recepcionista, Cliente |
| **Módulo** | Check-in Digital |
| **Permiso** | `CHECKIN_FIRMAR_ACTA` |
| **Descripción** | El cliente firma digitalmente el acta de conformidad del estado del vehículo. Se genera PDF/A firmado con validez legal (Ley 27269). |
| **Flujo Principal** | 1. Sistema muestra resumen del check-in: fotos, kilometraje, observaciones. 2. Cliente firma en pantalla táctil. 3. Sistema captura firma, genera PDF/A, sella con timestamp y almacena. 4. Entrega copia al cliente. |

---

### UC-40: Convertir Check-in en OT

| Campo | Detalle |
|:---|:---|
| **ID** | UC-40 |
| **Nombre** | Generar OT desde check-in |
| **Actor** | Recepcionista |
| **Módulo** | Check-in / OT |
| **Permiso** | `CHECKIN_CONVERTIR_OT` |
| **Descripción** | Crea automáticamente una OT con los datos y fotos capturados en el check-in. |

---

### UC-41: Reabrir OT en Garantía

| Campo | Detalle |
|:---|:---|
| **ID** | UC-41 |
| **Nombre** | Reabrir OT para garantía |
| **Actor** | Recepcionista / Asesor |
| **Módulo** | Garantías |
| **Permiso** | `GARANTIA_REABRIR_OT` |
| **Descripción** | Reabre una OT cerrada para atender un reclamo de garantía, indicando el motivo. |

---

### UC-44: Registrar Cobro

| Campo | Detalle |
|:---|:---|
| **ID** | UC-44 |
| **Nombre** | Registrar cobro |
| **Actor** | Cajero |
| **Módulo** | Caja & Arqueo |
| **Permiso** | `CAJA_REGISTRAR_COBRO` |
| **Descripción** | Registra el cobro asociado a una factura/boleta indicando método de pago. |
| **Métodos de Pago** | Efectivo, Tarjeta (Visa/MC), Yape/Plin, Transferencia bancaria. |

---

### UC-45: Ejecutar Cierre de Caja

| Campo | Detalle |
|:---|:---|
| **ID** | UC-45 |
| **Nombre** | Ejecutar cierre de caja diario |
| **Actor** | Cajero |
| **Módulo** | Caja & Arqueo |
| **Permiso** | `CAJA_EJECUTAR_CIERRE` |
| **Descripción** | Cierra la caja del día calculando descuadres entre lo esperado y lo registrado. |

---

### UC-47: Registrar Asistencia

| Campo | Detalle |
|:---|:---|
| **ID** | UC-47 |
| **Nombre** | Registrar asistencia diaria |
| **Actor** | Administrador |
| **Módulo** | RRHH |
| **Permiso** | `RRHH_REGISTRAR_ASISTENCIA` |
| **Descripción** | Registra la entrada y salida del personal. |

---

### UC-49b: Generar Boleta de Pago Electrónica

| Campo | Detalle |
|:---|:---|
| **ID** | UC-49b |
| **Nombre** | Generar boleta de pago electrónica |
| **Actor** | Administrador |
| **Módulo** | RRHH / Legal |
| **Permiso** | `RRHH_GENERAR_BOLETA_PAGO` |
| **Descripción** | Genera la boleta de pago mensual para cada trabajador en PDF/A con firma digital. Incluye haberes, descuentos (ONP/AFP), ESSALUD y neto a pagar. |
| **Precondición** | Asistencia registrada del mes. Comisiones calculadas. Configuración de comisiones activa. |
| **Postcondición** | PDF/A firmado almacenado. Enviado al trabajador. |
| **Flujo Principal** | 1. Actor selecciona período y trabajador (o "todos"). 2. Sistema calcula: ingresos (básico, horas extra, comisiones, asignación familiar), descuentos (ONP/AFP), ESSALUD. 3. Genera PDF/A con todos los datos. 4. Firma digitalmente. 5. Almacena y envía al trabajador. |

---

### UC-49c: Generar PLAME

| Campo | Detalle |
|:---|:---|
| **ID** | UC-49c |
| **Nombre** | Generar PLAME |
| **Actor** | Administrador |
| **Módulo** | RRHH / Legal |
| **Permiso** | `RRHH_GENERAR_PLAME` |
| **Descripción** | Genera el archivo PLAME (Planilla Mensual de Pagos) consolidado para declaración a SUNAT. |

---

### UC-50: Ver Dashboard de KPIs

| Campo | Detalle |
|:---|:---|
| **ID** | UC-50 |
| **Nombre** | Visualizar dashboard de KPIs |
| **Actor** | Administrador / Gerente |
| **Módulo** | Dashboard & Reportes |
| **Permiso** | `DASHBOARD_VER_KPIS` |
| **Descripción** | Dashboard en tiempo real con indicadores: facturación del día, OTs abiertas, stock crítico, citas del día, productividad. Actualización vía WebSocket. |

---

### UC-52: Exportar Reportes

| Campo | Detalle |
|:---|:---|
| **ID** | UC-52 |
| **Nombre** | Exportar reportes |
| **Actor** | Administrador / Gerente |
| **Módulo** | Dashboard & Reportes |
| **Permiso** | `REPORTE_EXPORTAR` |
| **Descripción** | Exporta reportes operativos y financieros a Excel/PDF. |

---

### UC-56: Registrar Nuevo Tenant

| Campo | Detalle |
|:---|:---|
| **ID** | UC-56 |
| **Nombre** | Registrar nuevo taller (Tenant) |
| **Actor** | Super Admin (SaaS) |
| **Módulo** | Multitenant & Administración |
| **Permiso** | `SAAS_TENANT_CREAR` |
| **Descripción** | Crea un nuevo tenant en la plataforma con su plan, RUC, datos fiscales y certificado digital. |
| **Flujo Principal** | 1. Super Admin ingresa datos del taller (RUC, razón social, plan). 2. Sistema valida RUC contra SUNAT. 3. Crea esquema aislado. 4. Configura usuario admin inicial. 5. Envía credenciales al taller. |

---

### UC-58: Gestionar Usuarios y Roles

| Campo | Detalle |
|:---|:---|
| **ID** | UC-58 |
| **Nombre** | Gestionar usuarios del taller |
| **Actor** | Administrador del Taller |
| **Módulo** | Multitenant & Administración |
| **Permiso** | `CONFIG_USUARIO_CREAR` |
| **Descripción** | CRUD de usuarios internos del taller con asignación de roles y permisos (PBAC). |

---

### UC-60: Autenticarse en el Sistema

| Campo | Detalle |
|:---|:---|
| **ID** | UC-60 |
| **Nombre** | Iniciar sesión |
| **Actor** | Todos los actores |
| **Módulo** | Seguridad (base) |
| **Descripción** | El usuario ingresa con email + contraseña. El sistema valida credenciales, tenant activo, y retorna token JWT con permisos. |
| **Flujo Principal** | 1. Actor ingresa email y contraseña. 2. Sistema valida contra base de datos. 3. Sistema verifica tenant activo. 4. Genera token JWT con permisos. 5. Redirige al dashboard según rol. |
| **Flujo Alterno** | 2a. Credenciales incorrectas → mensaje de error. 2b. Tenant suspendido → mensaje de bloqueo. 3a. Conexión WebSocket se establece automáticamente tras login. |

---

## Matriz Actor vs Casos de Uso

| Actor | Casos de Uso |
|:---|:---|
| **SUPER_ADMIN** | UC-56 (crear tenant), UC-57 (aislamiento), UC-59 (métricas globales), UC-58 (solo si aplica a su tenant). No opera talleres. |
| **ADMIN** | Todos los casos de uso dentro de su tenant (acceso total implícito). |
| **PUBLIC** | Depende de los permisos que el ADMIN le asigne individualmente. Ej: un usuario con permisos de ventas + caja puede ejecutar UC-11, UC-12, UC-44, UC-45. |
| **SUNAT (externo)** | UC-12, UC-13, UC-14, UC-16b, UC-16c (receptor SOAP) |
| **Cliente (externo)** | UC-18, UC-28, UC-39 (receptor de documentos / firmante) |
| **Sistema (automático CRON)** | UC-16c, UC-28 |

> **Nota:** No hay una asignación fija de casos de uso por puesto. El ADMIN configura qué permisos tiene cada usuario, y eso determina a qué casos de uso accede. Por ejemplo, un mismo usuario puede ejecutar UC-11 (vender) y UC-22 (agregar insumos a OT) si tiene ambos permisos.
**Total: 51 casos de uso documentados**
