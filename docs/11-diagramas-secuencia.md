# Diagramas de Secuencia — LUNORION LABS

Flujos críticos del sistema con énfasis en procesos legales (facturación SUNAT vía SOAP, RRHH, garantías).

Los diagramas están escritos en PlantUML. Renderizar en https://www.plantuml.com/plantuml/uml/ o IDE con plugin.

---

## 1. Facturación Electrónica — Ciclo Completo (SOAP)

Flujo crítico legal: desde que el vendedor registra la venta hasta que SUNAT acepta el comprobante y se notifica al cliente.

```plantuml
@startuml FacturacionElectronica

actor Vendedor as v
participant "Frontend\nAngular" as fe
participant "API Gateway" as gw
participant "Módulo\nVentas" as ventas
participant "Módulo\nLegal SUNAT" as legal
database "PostgreSQL" as db
participant "SUNAT\nOSE/SOAP" as sunat

== 1. Registrar Venta ==
v -> fe: Selecciona productos, cliente, tipo comprobante
fe -> gw: POST /sales (con items, tipoComprobante)
gw -> ventas: Valida JWT, permisos, tenant
ventas -> db: Verificar stock suficiente
db --> ventas: Stock OK
ventas -> db: Registrar venta + items (venta.id)
ventas -> legal: Emitir comprobante (ventaId)

== 2. Generar XML y Firmar ==
legal -> db: Cargar certificado digital del tenant (.p12)
db --> legal: Certificado digital
legal -> legal: Generar XML UBL 2.1 según tipo (01/03)
legal -> legal: Firmar XML (XAdES-EPES, RSA-SHA256)
legal -> db: Guardar XML firmado + hash SHA-256
legal -> db: Guardar comprobante (estado: FIRMADO)

note over legal: Timeout: 10s conexión + 30s lectura

== 3. Enviar a SUNAT vía SOAP ==
loop Reintento máximo 3 veces con backoff
  legal -> sunat: SOAP sendBill (fileName + contentFile Base64)
  alt SUNAT responde
    sunat --> legal: SOAP Response (CDR en Base64)
    legal -> legal: Decodificar CDR, validar firma de SUNAT
    legal -> db: Guardar CDR + estado (ACEPTADO / RECHAZADO)
  else Timeout / Error de conexión
    legal -> legal: Incrementar contador de reintento
    note right: Esperar 5s, 10s, 20s (exponential backoff)
  end
end

== 4. Rechazo por SUNAT ==
alt Estado = RECHAZADO (código error)
  legal -> db: Guardar código_error + descripción
  legal -> fe: WebSocket /topic/invoices/status (RECHAZADO, error)
  v -> fe: Corrige datos y reenvía
  fe -> gw: POST /invoices/{id}/resend
  note over gw: Vuelve al paso 2 con datos corregidos
end

== 5. Aceptación y Notificación ==
alt Estado = ACEPTADO
  legal -> fe: WebSocket /topic/invoices/status (ACEPTADO)
  fe --> v: Notificación visual "Comprobante aceptado"
  legal -> db: Registrar en auditoria (evento: INVOICE_ACCEPTED)
  legal -> legal: Generar PDF representación impresa
  legal -> legal: Enviar email al cliente con XML + PDF adjuntos
end

== 6. Actualizar Stock ==
ventas -> db: Descontar stock de productos vendidos
ventas -> db: Registrar movimientos de stock
ventas -> db: Verificar stock mínimo
ventas -> fe: WebSocket /topic/stock/alerts (si aplica)

@enduml
```

---

## 2. Check-in → OT → Cierre → Facturación

Flujo completo del servicio: desde que el cliente llega al taller hasta que paga.

```plantuml
@startuml Checkin_OT_Facturacion

actor Cliente as cli
actor Recepcionista as recep
actor Tecnico as tec
participant "Sistema" as sist
database DB as db
actor "SUNAT" as sunat

== 1. Check-in Digital ==
cli -> recep: Llega al taller con el vehículo
recep -> sist: INICIAR CHECK-IN (clienteId, vehiculoId)
recep -> sist: CAPTURAR FOTOS (tablero, exterior, interior)
sist -> db: Guardar fotos con timestamp y metadata
recep -> sist: REGISTRAR OBSERVACIONES y kilometraje
cli -> sist: FIRMAR ACTA DE INGRESO (pantalla táctil)
sist -> sist: Generar PDF/A del acta con firma digital
sist -> db: Guardar acta + fotos (checkin.id)
sist --> cli: Enviar copia del acta por WhatsApp/Email

== 2. Crear OT desde Check-in ==
recep -> sist: CONVERTIR CHECK-IN EN OT
sist -> db: Crear OT con datos del check-in
sist -> db: Estado: PENDIENTE
recep -> sist: ASIGNAR TÉCNICO y servicio
sist -> db: Actualizar OT (tecnicoId, servicios)

== 3. Ejecutar OT ==
tec -> sist: VER TABLERO KANBAN /topic/work-orders/kanban
tec -> sist: INICIAR OT (estado: EN_PROGRESO)
loop Durante la reparación
  tec -> sist: AGREGAR INSUMO consumido
  sist -> db: Descontar stock + registrar en OT
  tec -> sist: REGISTRAR HORAS de mano de obra
end
tec -> sist: MARCAR OT COMO "EN REVISIÓN"

== 4. Cierre de OT ==
recep -> sist: CERRAR OT
sist -> db: Calcular total (repuestos + mano de obra)
sist -> db: Actualizar estado (CERRADO)
sist --> recep: Mostrar resumen con total

== 5. Facturación ==
recep -> sist: EMITIR COMPROBANTE (factura o boleta)
sist -> sist: Ciclo de facturación electrónica
sist -> sunat: SOAP sendBill
sunat --> sist: CDR (ACEPTADO)
cli --> sist: Paga
sist -> sist: Registrar cobro (método de pago)
sist -> db: Actualizar caja
sist --> cli: Entregar comprobante + vehículo

@enduml
```

---

## 3. Cierre de Caja Diario

```plantuml
@startuml CierreCaja

actor Cajero as caj
participant "Sistema" as sist
database DB as db

== 1. Apertura de Caja (inicio del día) ==
caj -> sist: ABRIR CAJA (saldo inicial)
sist -> db: Registrar cierre_caja (apertura, saldo_inicial)
sist --> caj: Caja abierta: S/ 200.00

== 2. Durante el día (transacciones automáticas) ==
loop Cada venta/cobro
  caj -> sist: Registrar cobro (monto, método de pago)
  sist -> db: Insertar movimiento_caja
  alt Método = EFECTIVO
    sist -> sist: Incrementar saldo esperado
  else Método = TARJETA
    sist -> sist: Registrar en tarjetas del día
  else Método = YAPE
    sist -> sist: Registrar en Yape del día
  end
end

== 3. Cierre de Caja ==
caj -> sist: SOLICITAR CIERRE DE CAJA
sist -> db: Calcular totales del día
sist -> sist: Saldo esperado = inicial + ingresos - egresos
caj -> sist: INGRESAR SALDO REAL (conteo físico)
sist -> sist: Calcular descuadre (real - esperado)

alt Descuadre ≠ 0
  sist --> caj: Alerta: Descuadre de S/ {monto}
  caj -> sist: INGRESAR OBSERVACIÓN del descuadre
end

sist -> db: Cerrar caja (fecha_cierre, totales, descuadre)
sist -> db: Registrar en auditoría

== 4. Reporte ==
sist --> caj: Cierre exitoso - Resumen:
note right
  Total Efectivo: S/ 2,450.00
  Total Tarjeta:  S/ 1,200.00
  Total Yape:     S/   380.00
  Saldo Esperado: S/ 4,030.00
  Saldo Real:     S/ 4,025.00
  Descuadre:      S/   -5.00
end note

@enduml
```

---

## 4. Generación de Boleta de Pago Electrónica (RRHH)

```plantuml
@startuml BoletaPago

actor Administrador as admin
participant "Sistema" as sist
database DB as db

== 1. Cargar Datos del Período ==
admin -> sist: GENERAR BOLETAS (período: 202605)
sist -> db: Cargar técnicos activos
db --> sist: Lista de técnicos

loop Por cada técnico
  == 2. Calcular Ingresos ==
  sist -> db: Cargar sueldo básico del técnico
  db --> sist: S/ 1,200.00
  
  sist -> db: Sumar horas extras del mes (asistencia)
  db --> sist: 12 horas extra × tarifa_hora
  
  sist -> db: Sumar comisiones del mes (configuración + ventas asociadas)
  db --> sist: S/ 320.00 en comisiones
  
  sist -> sist: Calcular asignación familiar (10% RMV si aplica)
  
  == 3. Calcular Descuentos ==
  sist -> db: Obtener régimen pensionario (ONP/AFP)
  db --> sist: ONP (13%)
  sist -> sist: Descuento ONP = total_ingresos × 13%
  
  == 4. Calcular ESSALUD ==
  sist -> sist: ESSALUD = total_ingresos × 9%
  note right: ESSALUD es aporte del empleador, no descuenta del trabajador
  
  == 5. Generar Documento ==
  sist -> sist: Neto a pagar = ingresos - descuentos
  sist -> sist: Generar PDF/A con todos los datos
  sist -> sist: Firmar PDF digitalmente (PAdES)
  sist -> db: Guardar boleta_pago (todos los campos)
  admin -> sist: Visualizar boleta generada
end

== 6. Resumen ==
sist --> admin: 5 boletas generadas para mayo 2026
sist -> sist: Notificar a trabajadores por email

@enduml
```

---

## 5. Resumen Diario de Boletas (RDB)

Ejecutado automáticamente por CRON al final del día. Las boletas (tipo 03) se agrupan y envían a SUNAT.

```plantuml
@startuml ResumenDiario

participant "CRON\n(23:00)" as cron
participant "Módulo\nLegal SUNAT" as legal
database DB as db
participant "SUNAT\nSOAP" as sunat

== 1. Agrupar Boletas del Día ==
cron -> legal: EJECUTAR RESUMEN DIARIO
legal -> db: Buscar boletas del día SIN resumen
db --> legal: Lista de boletas (tipo 03)

alt No hay boletas pendientes
  legal --> cron: Sin boletas, finaliza
end

== 2. Generar Resumen ==
legal -> legal: Agrupar boletas + anulaciones del día
legal -> legal: Generar XML de resumen (UBL 2.1)
legal -> legal: Firmar XML con certificado digital

== 3. Enviar a SUNAT ==
loop Reintento máx. 3
  legal -> sunat: SOAP sendSummary (fileName + contentFile)
  alt Éxito
    sunat --> legal: CDR del resumen
    legal -> db: Guardar resumen (ACEPTADO)
    legal -> db: Marcar boletas como "Comunicadas a SUNAT"
    db --> legal: OK
    legal --> cron: Resumen diario completado
  else Error / Timeout
    legal -> legal: Reintentar con backoff
  end
end

@enduml
```

---

## 6. Flujo de Garantía (Reapertura de OT)

```plantuml
@startuml Garantia

actor Cliente as cli
actor Asesor as asesor
participant "Sistema" as sist
database DB as db

== 1. Reclamo del Cliente ==
cli -> asesor: Reporta problema con reparación previa
asesor -> sist: BUSCAR OT ORIGINAL (cerrada)
sist -> db: Obtener OT por cliente/vehículo
db --> sist: OT original con datos

== 2. Reabrir OT como Garantía ==
asesor -> sist: REABRIR OT (otOriginalId, motivo)
sist -> sist: Validar que OT original existe y está cerrada
sist -> db: Crear OT_garantía con referencia a original
sist -> db: Estado OT_garantía: EN_PROGRESO
sist -> db: Registrar en garantia (motivo, costos en 0)

== 3. Ejecutar Servicio en Garantía ==
loop Reparación
  asesor -> sist: Agregar insumos (costo cero)
  sist -> db: Descontar stock (costo al taller)
  asesor -> sist: Registrar horas (costo cero)
end

== 4. Cerrar Garantía ==
asesor -> sist: CERRAR OT DE GARANTÍA
sist -> sist: Calcular costo total de la garantía
sist -> db: Actualizar garantia (costo_real)
sist -> db: Registrar en reporte de garantías (RF-43)

@enduml
```

---

## 7. Ciclo de Vida del Certificado Digital

```plantuml
@startuml CertificadoDigital

actor "Super Admin" as admin
participant "Sistema" as sist
database DB as db
actor "Entidad Certificadora" as cert

== 1. Registro del Certificado ==
admin -> sist: SUBCIR CERTIFICADO (.p12) del tenant
sist -> sist: Validar contraseña del keystore
sist -> sist: Extraer metadatos (emisión, vencimiento, titular)
sist -> db: Guardar certificado + metadata
sist -> db: Guardar fecha de vencimiento

== 2. Verificación Diaria ==
loop Cada día (CRON)
  sist -> db: Consultar certificados próximos a vencer
  alt Vence en ≤ 30 días
    sist -> admin: Notificar "Certificado vence en N días"
  else Vencido
    sist -> db: Bloquear emisión de comprobantes para el tenant
    sist -> admin: Notificar "Certificado vencido - Bloqueado"
  end
end

== 3. Renovación ==
admin -> cert: Renovar certificado digital
admin -> sist: SUBCIR NUEVO CERTIFICADO
sist -> db: Actualizar certificado del tenant
sist -> db: Reactivar emisión de comprobantes
sist --> admin: Certificado actualizado correctamente

@enduml
```

---

## Leyenda de Colores para Diagramas

| Color | Significado |
|:---|:---|
| **Actor** | Persona o sistema externo que inicia la interacción |
| **Participant** | Módulo interno del sistema |
| **Database** | PostgreSQL |
| **Línea continua** | Llamada síncrona (REST, consulta DB, método) |
| **Línea punteada** | Respuesta, callback o WebSocket |
| **Loop** | Repetición (reintentos, iteraciones) |
| **Alt** | Condicional (if/else) |
| **Note** | Comentario o detalle adicional |
