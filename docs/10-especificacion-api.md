# Especificación de API — LUNORION LABS

Endpoints REST + operaciones SOAP para facturación electrónica SUNAT.

---

## Convenciones Generales

| Atributo | Valor |
|:---|:---|
| **Base URL REST** | `https://api.lunorionlabs.com/v1` |
| **Base URL SOAP** | `https://api.lunorionlabs.com/soap/sunat` |
| **Autenticación** | JWT en header `Authorization: Bearer <token>` |
| **Content-Type** | `application/json` (REST) / `text/xml` (SOAP) |
| **Idioma** | Códigos de error en español |
| **Paginación** | `?page=0&size=20&sort=createdAt,desc` |
| **Tenant ID** | Se extrae del JWT, no se envía en el body |
| **Auditoría** | Todos los POST/PUT/PATCH generan registro en `auditoria` |

---

## Autenticación

### POST /auth/login

Inicia sesión y retorna JWT.

```
Request:
{
  "email": "admin@taller.com",
  "password": "********"
}

Response 200:
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "tokenType": "Bearer",
  "expiresIn": 86400,
  "usuario": {
    "id": "uuid",
    "nombres": "Juan",
    "apellidos": "Pérez",
    "email": "admin@taller.com",
    "rol": "ADMIN",
    "permisos": ["INVENTARIO_REGISTRAR_INGRESO", "VENTA_EMITIR_FACTURA", ...]
  },
  "tenant": {
    "id": "uuid",
    "ruc": "20123456789",
    "razonSocial": "Taller Mecánico SAC"
  }
}
```

### POST /auth/refresh

Refresca el token antes de que expire.

---

## Inventario & Stock

### Productos

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/products` | `INVENTARIO_VER_STOCK` | Lista productos (filtros: categoria, tipo, activo, search) |
| `GET` | `/products/{id}` | `INVENTARIO_VER_STOCK` | Detalle de producto |
| `POST` | `/products` | `PRODUCTO_CREAR` | Crear producto |
| `PUT` | `/products/{id}` | `PRODUCTO_CREAR` | Actualizar producto |
| `PATCH` | `/products/{id}/stock` | `INVENTARIO_REGISTRAR_INGRESO` | Ajustar stock manual |
| `GET` | `/products/{id}/movements` | `INVENTARIO_VER_HISTORIAL` | Historial de movimientos |
| `GET` | `/products/stock-alerts` | `INVENTARIO_VER_ALERTAS` | Productos con stock crítico |
| `GET` | `/products/rotation-report` | `INVENTARIO_VER_REPORTES` | Reporte de rotación |
| `POST` | `/products/quick-create` | `PRODUCTO_CREAR_RAPIDO` | Creación rápida (desde compra/venta) |
| `GET` | `/categories` | `PRODUCTO_GESTIONAR_CATEGORIAS` | Lista categorías |
| `POST` | `/categories` | `PRODUCTO_GESTIONAR_CATEGORIAS` | Crear categoría |

**POST /products**
```json
{
  "codigo": "ACE-20W50",
  "codigoBarra": "7750123456789",
  "nombre": "Aceite 20W50 1L",
  "categoriaId": "uuid",
  "marca": "Mobil",
  "precioCompra": 12.50,
  "precioVenta": 25.00,
  "stockMinimo": 10,
  "tipo": "PRODUCTO"
}
```

### Movimientos de Stock

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `POST` | `/stock-movements/entry` | `INVENTARIO_REGISTRAR_INGRESO` | Registrar ingreso |
| `POST` | `/stock-movements/exit` | `INVENTARIO_REGISTRAR_EGRESO` | Registrar egreso |
| `GET` | `/stock-movements` | `INVENTARIO_VER_HISTORIAL` | Listar movimientos (filtros: producto, fecha, tipo) |

### Proveedores

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/suppliers` | `PROVEEDOR_GESTIONAR` | Lista proveedores |
| `POST` | `/suppliers` | `PROVEEDOR_GESTIONAR` | Crear proveedor |
| `PUT` | `/suppliers/{id}` | `PROVEEDOR_GESTIONAR` | Actualizar proveedor |

### Órdenes de Compra

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/purchase-orders` | `COMPRA_CREAR_ORDEN` | Lista órdenes de compra |
| `POST` | `/purchase-orders` | `COMPRA_CREAR_ORDEN` | Crear orden de compra |
| `GET` | `/purchase-orders/{id}` | `COMPRA_CREAR_ORDEN` | Detalle de orden |
| `POST` | `/purchase-orders/{id}/receive` | `COMPRA_RECIBIR_ORDEN` | Recibir (parcial/total) |
| `GET` | `/purchase-orders/spending-report` | `COMPRA_VER_REPORTE_GASTOS` | Reporte de gastos |

---

## Ventas

### Ventas de Mostrador

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/sales` | `VENTA_REGISTRAR_MOSTRADOR` | Lista ventas |
| `POST` | `/sales` | `VENTA_REGISTRAR_MOSTRADOR` | Registrar venta |
| `GET` | `/sales/{id}` | `VENTA_REGISTRAR_MOSTRADOR` | Detalle de venta |

**POST /sales**
```json
{
  "clienteId": "uuid",
  "items": [
    {
      "productoId": "uuid",
      "cantidad": 2,
      "precioUnitario": 25.00
    }
  ],
  "metodoPago": "EFECTIVO",
  "emitirComprobante": true,
  "tipoComprobante": "03"
}
```

### Comprobantes Electrónicos (REST)

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `POST` | `/invoices` | `VENTA_EMITIR_FACTURA` | Emitir factura (tipo 01) |
| `POST` | `/invoices/receipt` | `VENTA_EMITIR_BOLETA` | Emitir boleta (tipo 03) |
| `POST` | `/invoices/credit-note` | `VENTA_EMITIR_NOTA_CREDITO` | Emitir nota de crédito (tipo 07) |
| `POST` | `/invoices/debit-note` | `VENTA_EMITIR_NOTA_DEBITO` | Emitir nota de débito (tipo 08) |
| `GET` | `/invoices` | `VENTA_VER_CDR` | Lista comprobantes emitidos |
| `GET` | `/invoices/{id}` | `VENTA_VER_CDR` | Detalle + CDR del comprobante |
| `GET` | `/invoices/{id}/cdr` | `VENTA_VER_CDR` | Descargar XML del CDR |
| `GET` | `/invoices/{id}/xml` | `VENTA_VER_CDR` | Descargar XML firmado |
| `POST` | `/invoices/{id}/resend` | `VENTA_REENVIAR_COMPROBANTE` | Reenviar comprobante rechazado |
| `POST` | `/invoices/daily-summary` | `VENTA_GENERAR_RESUMEN_DIARIO` | Generar y enviar RDB |
| `GET` | `/invoices/daily-summary/{id}` | `VENTA_GENERAR_RESUMEN_DIARIO` | Estado del RDB |
| `GET` | `/invoices/billing-report` | `VENTA_VER_REPORTE_FACTURACION` | Reporte de facturación |
| `GET` | `/sales/customers/{id}/history` | `VENTA_VER_HISTORIAL_CLIENTE` | Historial de compras y servicios del cliente desde ventas |
| `GET` | `/invoices/ple` | `VENTA_GENERAR_PLE` | Generar PLE del período |
| `GET` | `/invoices/ple/download` | `VENTA_GENERAR_PLE` | Descargar TXT del PLE |

**POST /invoices (emitir factura)**
```json
{
  "ventaId": "uuid",
  "tipo": "01",
  "serie": "F001",
  "clienteRuc": "20123456789",
  "clienteRazonSocial": "Empresa SAC",
  "clienteDireccion": "Av. Principal 123",
  "items": [
    {
      "descripcion": "Aceite 20W50 1L",
      "cantidad": 2,
      "precioUnitario": 25.00,
      "subtotal": 50.00
    }
  ]
}
```

**Response:**
```json
{
  "id": "uuid",
  "serie": "F001",
  "numero": 123,
  "estadoSunat": "ACEPTADO",
  "cdr": {
    "codigo": "0",
    "descripcion": "La Factura [...] fue aceptada",
    "fechaRecepcion": "2026-05-30T14:30:00"
  },
  "xmlUrl": "/api/v1/invoices/uuid/xml",
  "cdrUrl": "/api/v1/invoices/uuid/cdr"
}
```

---

## Cotizaciones

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/quotes` | `COTIZACION_VER_HISTORIAL` | Lista cotizaciones |
| `POST` | `/quotes` | `COTIZACION_CREAR` | Crear cotización |
| `GET` | `/quotes/{id}` | `COTIZACION_VER_HISTORIAL` | Detalle |
| `PUT` | `/quotes/{id}` | `COTIZACION_CREAR` | Actualizar |
| `POST` | `/quotes/{id}/send` | `COTIZACION_ENVIAR_CLIENTE` | Enviar al cliente |
| `POST` | `/quotes/{id}/convert-to-wo` | `COTIZACION_CONVERTIR_OT` | Convertir en OT |
| `PATCH` | `/quotes/{id}/status` | `COTIZACION_CREAR` | Cambiar estado (APROBADA, RECHAZADA) |

---

## Órdenes de Trabajo

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/work-orders` | `OT_VER_TABLERO` | Lista OTs (filtros: estado, tecnico, fecha) |
| `POST` | `/work-orders` | `OT_CREAR` | Crear OT |
| `GET` | `/work-orders/{id}` | `OT_VER_TABLERO` | Detalle OT |
| `PUT` | `/work-orders/{id}` | `OT_CREAR` | Actualizar OT |
| `PATCH` | `/work-orders/{id}/status` | `OT_CERRAR` | Cambiar estado |
| `POST` | `/work-orders/{id}/insumos` | `OT_GESTIONAR_INSUMOS` | Agregar insumo |
| `DELETE` | `/work-orders/{id}/insumos/{insumoId}` | `OT_GESTIONAR_INSUMOS` | Quitar insumo |
| `POST` | `/work-orders/{id}/labor` | `OT_GESTIONAR_MANO_OBRA` | Registrar horas |
| `PUT` | `/work-orders/{id}/labor/{laborId}` | `OT_GESTIONAR_MANO_OBRA` | Actualizar horas |
| `POST` | `/work-orders/{id}/close` | `OT_CERRAR` | Cerrar OT (calcula total) |
| `POST` | `/work-orders/{id}/reopen` | `GARANTIA_REABRIR_OT` | Reabrir por garantía |
| `GET` | `/work-orders/kanban` | `OT_VER_TABLERO` | Tablero Kanban agrupado por estado |

**POST /work-orders**
```json
{
  "clienteId": "uuid",
  "vehiculoId": "uuid",
  "tecnicoId": "uuid",
  "motivoIngreso": "Cambio de aceite y filtros",
  "kilometrajeIngreso": 45678,
  "fechaPrometida": "2026-05-31",
  "servicios": [
    {
      "descripcion": "Cambio de aceite",
      "horas": 1,
      "tarifaHora": 45.00
    }
  ]
}
```

**POST /work-orders/{id}/close Response:**
```json
{
  "id": "uuid",
  "estado": "CERRADO",
  "totalRepuestos": 85.50,
  "totalManoObra": 135.00,
  "total": 220.50,
  "puedeFacturar": true
}
```

---

## Garantías

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `POST` | `/warranties` | `GARANTIA_REGISTRAR_COSTOS` | Registrar costos de garantía para OT reabierta |
| `GET` | `/warranties` | `GARANTIA_VER_REPORTE` | Lista de garantías registradas |
| `GET` | `/warranties/report` | `GARANTIA_VER_REPORTE` | Reporte de pérdidas por garantías |

---

## Citas

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/appointments` | `CITA_VER_CALENDARIO` | Lista citas |
| `POST` | `/appointments` | `CITA_CREAR` | Agendar cita |
| `PUT` | `/appointments/{id}` | `CITA_CREAR` | Reprogramar |
| `PATCH` | `/appointments/{id}/status` | `CITA_CREAR` | Confirmar/cancelar |
| `GET` | `/appointments/calendar` | `CITA_VER_CALENDARIO` | Datos para calendario |
| `GET` | `/appointments/availability` | `CITA_VER_CALENDARIO` | Disponibilidad por fecha |
| `GET` | `/appointments/notifications-config` | `CITA_CONFIGURAR_NOTIFICACIONES` | Ver configuración de recordatorios |
| `PUT` | `/appointments/notifications-config` | `CITA_CONFIGURAR_NOTIFICACIONES` | Configurar recordatorios automáticos |

---

## Clientes

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/customers` | `CLIENTE_CREAR` | Lista clientes |
| `POST` | `/customers` | `CLIENTE_CREAR` | Registrar cliente |
| `GET` | `/customers/{id}` | `CLIENTE_CREAR` | Detalle cliente |
| `PUT` | `/customers/{id}` | `CLIENTE_CREAR` | Actualizar |
| `GET` | `/customers/{id}/work-history` | `CLIENTE_VER_HISTORIAL_TRABAJOS` | Historial de OTs |
| `GET` | `/customers/{id}/purchase-history` | `CLIENTE_VER_HISTORIAL_COMPRAS` | Historial de compras |
| `GET` | `/customers/{id}/profitability` | `CLIENTE_VER_RENTABILIDAD` | Rentabilidad del cliente |

---

## Vehículos

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/vehicles` | `VEHICULO_CREAR` | Lista vehículos |
| `POST` | `/vehicles` | `VEHICULO_CREAR` | Crear vehículo |
| `GET` | `/vehicles/{id}` | `VEHICULO_VER_HISTORIAL` | Detalle + historial |
| `PUT` | `/vehicles/{id}` | `VEHICULO_CREAR` | Actualizar |
| `POST` | `/vehicles/{id}/assign-customer` | `VEHICULO_VINCULAR_CLIENTE` | Asignar a cliente |

---

## Check-in Digital

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `POST` | `/checkins` | `CHECKIN_CAPTURAR_FOTOS` | Iniciar check-in |
| `POST` | `/checkins/{id}/photos` | `CHECKIN_CAPTURAR_FOTOS` | Subir foto (multipart) |
| `DELETE` | `/checkins/{id}/photos/{photoId}` | `CHECKIN_CAPTURAR_FOTOS` | Eliminar foto |
| `POST` | `/checkins/{id}/sign` | `CHECKIN_FIRMAR_ACTA` | Firmar acta |
| `GET` | `/checkins/{id}/acta` | `CHECKIN_FIRMAR_ACTA` | Descargar PDF del acta |
| `POST` | `/checkins/{id}/convert-to-wo` | `CHECKIN_CONVERTIR_OT` | Convertir en OT |

---

## Caja

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `POST` | `/cash-register/payments` | `CAJA_REGISTRAR_COBRO` | Registrar cobro |
| `GET` | `/cash-register/current` | `CAJA_REGISTRAR_COBRO` | Estado actual de caja |
| `POST` | `/cash-register/close` | `CAJA_EJECUTAR_CIERRE` | Cerrar caja del día |
| `GET` | `/cash-register/history` | `CAJA_VER_HISTORIAL` | Historial de cierres |

---

## RRHH

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/technicians` | `TECNICO_CREAR` | Lista técnicos |
| `POST` | `/technicians` | `TECNICO_CREAR` | Registrar técnico |
| `GET` | `/technicians/{id}/workload` | `TECNICO_VER_CARGA_TRABAJO` | Carga de trabajo |
| `POST` | `/attendance` | `RRHH_REGISTRAR_ASISTENCIA` | Registrar entrada/salida |
| `GET` | `/attendance` | `RRHH_REGISTRAR_ASISTENCIA` | Reporte de asistencia |
| `GET` | `/attendance/productivity` | `RRHH_VER_PRODUCTIVIDAD` | Productividad |
| `GET` | `/commissions/config` | `RRHH_CONFIGURAR_COMISIONES` | Config. comisiones |
| `POST` | `/commissions/config` | `RRHH_CONFIGURAR_COMISIONES` | Crear regla de comisión |
| `POST` | `/payroll/generate` | `RRHH_GENERAR_BOLETA_PAGO` | Generar boletas de pago |
| `GET` | `/payroll` | `RRHH_GENERAR_BOLETA_PAGO` | Lista boletas generadas |
| `GET` | `/payroll/{id}/pdf` | `RRHH_GENERAR_BOLETA_PAGO` | Descargar PDF |
| `POST` | `/payroll/plame` | `RRHH_GENERAR_PLAME` | Generar PLAME |

---

## Dashboard

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `GET` | `/dashboard/kpis` | `DASHBOARD_VER_KPIS` | KPIs en tiempo real |
| `GET` | `/dashboard/profitability` | `REPORTE_VER_RENTABILIDAD` | Rentabilidad por servicio |
| `GET` | `/dashboard/export` | `REPORTE_EXPORTAR` | Exportar reporte (?formato=EXCEL|PDF) |

---

## Administración / Multitenant

| Método | Path | Permiso | Descripción |
|:---|:---|:---|:---|
| `POST` | `/admin/tenants` | `SAAS_TENANT_CREAR` | Registrar nuevo taller |
| `GET` | `/admin/tenants` | `SAAS_TENANT_CREAR` | Lista de tenants |
| `GET` | `/admin/tenants/{id}/metrics` | `SAAS_VER_METRICAS` | Métricas del tenant |
| `GET` | `/admin/global-metrics` | `SAAS_VER_METRICAS` | Métricas globales |
| `GET` | `/admin/users` | `CONFIG_USUARIO_CREAR` | Usuarios del tenant |
| `POST` | `/admin/users` | `CONFIG_USUARIO_CREAR` | Crear usuario PUBLIC |
| `PUT` | `/admin/users/{id}/permissions` | `CONFIG_USUARIO_CREAR` | Asignar permisos a usuario |
| `GET` | `/admin/permissions` | `CONFIG_USUARIO_CREAR` | Listar catálogo de permisos |

---

## WebSocket (STOMP)

### Conexión

```
Endpoint: wss://api.lunorionlabs.com/ws
Header: Authorization: Bearer <jwt>
```

### Canales (Topics)

| Topic | Evento | Payload | Descripción |
|:---|:---|:---|:---|
| `/topic/dashboard/kpis` | KPI_UPDATE | `{ facturacionHoy, stockCritico, otsAbiertas, citasHoy }` | Dashboard en vivo |
| `/topic/stock/alerts` | STOCK_ALERT | `{ productoId, nombre, stockActual, stockMinimo }` | Alerta stock crítico |
| `/topic/invoices/status` | CDR_RECEIVED | `{ comprobanteId, serie, numero, estado, codigoError }` | CDR recibido |
| `/topic/work-orders/kanban` | OT_STATUS_CHANGE | `{ otId, numeroOt, estadoAnterior, estadoNuevo }` | Cambio de estado OT |
| `/topic/appointments/checkin` | CHECKIN_ARRIVED | `{ citaId, cliente, placa }` | Cliente llegó |
| `/topic/notifications` | NOTIFICATION | `{ tipo, titulo, mensaje, url }` | Notificaciones generales |

### Envío desde Backend

```java
// Ejemplo: notificar CDR recibido
messagingTemplate.convertAndSendToUser(
    usuarioId,
    "/topic/invoices/status",
    new CDRNotification(invoiceId, serie, numero, estado, codigoError)
);
```

---

## API SOAP — SUNAT (Facturación Electrónica)

### WSDL Base

Los WSDL son provistos por SUNAT. El sistema implementa **cliente SOAP** que consume estos servicios:

| Servicio SUNAT | Operación SOAP | Propósito |
|:---|:---|:---|
| `billService` | `sendBill` | Envío de factura, NC, ND (individual) |
| `billService` | `sendSummary` | Envío de resumen diario de boletas |
| `billService` | `getStatus` | Consultar estado de un comprobante enviado |
| `billService` | `sendPack` | Envío masivo (opcional) |

### Estructura del Cliente SOAP (Spring WS)

```xml
<!-- Configuración en Spring -->
<bean id="sunatMarshaller" class="org.springframework.oxm.jaxb.Jaxb2Marshaller">
    <property name="contextPaths" value="sunat.wsdl"/>
</bean>

<bean id="sunatWebServiceTemplate" class="org.springframework.ws.client.core.WebServiceTemplate">
    <property name="defaultUri" value="https://ose.sunat.gob.pe:443/ol-ti-itcpfsegem-service/billService"/>
    <property name="marshaller" ref="sunatMarshaller"/>
    <property name="unmarshaller" ref="sunatMarshaller"/>
    <property name="messageSender">
        <bean class="org.springframework.ws.transport.http.HttpUrlConnectionMessageSender">
            <property name="connectionTimeout" value="10000"/>
            <property name="readTimeout" value="30000"/>
        </bean>
    </property>
</bean>
```

### Formatos de Mensaje SOAP

**Envío de Comprobante (sendBill):**
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Header>
    <wsse:Security>
      <wsse:BinarySecurityToken
          EncodingType="wsse:Base64Binary"
          ValueType="wsse:X509v3"
          Id="X509Token">
          MIIDjDCCA...certificado_base64...
      </wsse:BinarySecurityToken>
    </wsse:Security>
  </soap:Header>
  <soap:Body>
    <sendBill>
      <fileName>F001-0000123.xml</fileName>
      <contentFile>
          XML_del_comprobante_firmado_en_base64
      </contentFile>
    </sendBill>
  </soap:Body>
</soap:Envelope>
```

**Respuesta (CDR):**
```xml
<soap:Envelope>
  <soap:Body>
    <sendBillResponse>
      <applicationResponse>
          CDR_en_base64_con_firma_de_SUNAT
      </applicationResponse>
    </sendBillResponse>
  </soap:Body>
</soap:Envelope>
```

### Implementación Java

```java
package com.lunorion.labs.infrastructure.soap;

@Service
public class SunatSoapClient {

    private final WebServiceTemplate webServiceTemplate;

    public void sendInvoice(InvoiceDocument invoice) {
        try {
            String xmlSigned = signInvoice(invoice); // Firma XAdES-EPES
            String xmlBase64 = Base64.getEncoder().encodeToString(xmlSigned.getBytes());

            SendBillRequest request = new SendBillRequest();
            request.setFileName(invoice.getSerie() + "-" + invoice.getNumero() + ".xml");
            request.setContentFile(xmlBase64);

            SendBillResponse response = (SendBillResponse)
                webServiceTemplate.marshalSendAndReceive(request);

            String cdrBase64 = response.getApplicationResponse();
            processCDR(cdrBase64, invoice);

        } catch (WebServiceIOException e) {
            // Reintentar con backoff
            enqueueForRetry(invoice);
        }
    }
}
```

---

## Códigos de Estado HTTP

| Código | Significado | Uso |
|:---:|:---|:---|
| 200 | OK | GET, PUT, PATCH exitosos |
| 201 | Created | POST exitoso |
| 204 | No Content | DELETE exitoso |
| 400 | Bad Request | Datos inválidos, validación fallida |
| 401 | Unauthorized | Token no enviado o inválido |
| 403 | Forbidden | Usuario sin permiso para la operación |
| 404 | Not Found | Recurso no existe |
| 409 | Conflict | Violación de regla de negocio (ej. duplicado) |
| 422 | Unprocessable Entity | Error de negocio (ej. stock insuficiente) |
| 429 | Too Many Requests | Rate limit excedido |
| 500 | Internal Server Error | Error inesperado |
| 502 | Bad Gateway | Error al conectar con SUNAT |
| 503 | Service Unavailable | Mantenimiento o sobrecarga |

---

## Rate Limiting

| Límite | Ámbito | Endpoints |
|:---|:---|:---|
| 100 req/min | Por usuario + tenant | Todos los endpoints REST |
| 10 req/min | Por tenant | Envío SOAP a SUNAT (sendBill) |
| 5 req/min | Por tenant | Generación de RDB |

Los headers de rate limiting se incluyen en cada respuesta:
```
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1622345600
```
