# Glosario de Términos del Dominio — LUNORION LABS

Vocabulario uniforme para el equipo de desarrollo, organizado por categorías del dominio automotriz, legal-tributario y técnico.

---

## Dominio del Taller Automotriz

| Término | Definición | Contexto / Ejemplo |
|:---|:---|:---|
| **Orden de Trabajo (OT)** | Documento interno que registra los servicios, repuestos y mano de obra para un vehículo en reparación. Es la unidad principal de facturación del taller. | Una OT vincula: cliente → vehículo → técnico → servicios → insumos |
| **Cotización** | Documento previo a la OT. Es una estimación de costos que se envía al cliente para aprobación. Al ser aprobada se convierte en OT. | Cotización → Aprobación del cliente → OT |
| **Check-in** | Proceso de recepción del vehículo al ingreso. Incluye captura de fotos, registro de kilometraje, estado del vehículo y firma del acta de ingreso. | "Hacer el check-in del Toyota Yaris antes de empezar la OT" |
| **Box/Taller** | Espacio físico asignado para la reparación de un vehículo. | Calendario muestra 4 boxes disponibles |
| **Mano de obra** | Horas de trabajo del técnico aplicadas a una OT. Se factura según una tarifa por hora. | "3 horas de mano de obra a S/ 45/h" |
| **Insumo** | Producto o repuesto consumido durante el servicio de una OT (ej. aceite, filtro, pastillas de freno). | "Agregar insumo: Filtro de aceite — 1 unidad" |
| **Servicio** | Tipo de trabajo ofrecido por el taller (ej. cambio de aceite, afinamiento, revisión de frenos). | "Servicio de alineamiento y balanceo" |
| **Rotación de producto** | Indicador de la velocidad con que un producto se vende o consume. Ayuda a decidir compras. | "Los filtros de aceite tienen alta rotación" |
| **Stock mínimo** | Cantidad mínima de un producto antes de generar alerta de reposición. | "Stock mínimo del aceite 20W50: 5 unidades" |
| **Historial del vehículo** | Registro cronológico de todas las OTs, servicios y reparaciones realizadas a un vehículo. | "El vehículo tiene 3 OTs previas en su historial" |
| **Carga de trabajo** | Cantidad de OTs activas y horas asignadas a un técnico en un momento dado. | "Técnico Pedro tiene 8 horas asignadas hoy" |

---

## Legal-Tributario (SUNAT / Perú)

| Término | Definición | Base Legal / Notas |
|:---|:---|:---|
| **Comprobante Electrónico** | Documento tributario emitido en formato XML firmado digitalmente y enviado a SUNAT. Incluye Factura, Boleta, NC, ND. | RS 188-2020/SUNAT — Todo comprobante debe ser electrónico |
| **Factura Electrónica (tipo 01)** | Comprobante B2B que permite al comprador sustentar gasto tributario. Requiere RUC del cliente. Se envía individualmente vía SOAP. | Serie Fxxx. Permite crédito fiscal IGV |
| **Boleta de Venta Electrónica (tipo 03)** | Comprobante para consumidor final sin RUC. No permite crédito fiscal. Se agrupa en Resumen Diario. | Serie Bxxx. Se envía al día siguiente en RDB |
| **Nota de Crédito (tipo 07)** | Comprobante que anula total/parcial o descuenta un comprobante previo. | Serie FCxx (factura) o BCxx (boleta) |
| **Nota de Débito (tipo 08)** | Comprobante que incrementa el monto de un comprobante previo. | Serie FDxx (factura) |
| **Resumen Diario de Boletas (RDB)** | Archivo XML que agrupa todas las boletas emitidas en un día, enviado a SUNAT al día siguiente hábil. | RC + año + correlativo |
| **CDR (Comprobante de Recepción)** | XML firmado por SUNAT que confirma la recepción y estado de un comprobante. Indica si fue aceptado o rechazado. | Códigos: 0=Aceptado, 1=Observado, 2=Rechazado |
| **OSE (Operador de Servicios Electrónicos)** | Entidad autorizada por SUNAT que recibe y valida comprobantes electrónicos. Puede ser SUNAT misma o un PSE. | SOAP endpoint |
| **OSB (Operador de Servicios de Boletas)** | Similar a OSE pero para boletas y resúmenes diarios. | |
| **PSE (Proveedor de Servicios Electrónicos)** | Empresa privada certificada por SUNAT para operar como OSE/OSB (ej. KEBUTEC, E-CERT). | |
| **UBL 2.1** | Estándar XML de la Universidad de Oporto que define la estructura de los comprobantes electrónicos peruanos. | Los XML deben cumplir el esquema XSD de SUNAT basado en UBL 2.1 |
| **XML Firmado** | Comprobante electrónico firmado digitalmente con algoritmo RSA + SHA-256 usando el certificado digital del emisor. | Formato XAdES-EPES |
| **Firma Digital** | Mecanismo criptográfico que garantiza integridad y autenticidad de un documento electrónico. | Ley 27269 — Tiene la misma validez que la firma manuscrita |
| **Certificado Digital** | Archivo (.p12) emitido por una entidad acreditada (INDECOPI/RENIEC) que contiene la clave privada del contribuyente. | Se almacena en keystore PKCS#12 protegido |
| **XAdES-EPES** | Formato de firma digital XML usado en facturación electrónica peruana. | |
| **PAdES** | Formato de firma digital para documentos PDF. | Usado en boletas de pago y actas |
| **RUC** | Registro Único del Contribuyente. Número de 11 dígitos que identifica a una persona jurídica o natural ante SUNAT. | Todo taller necesita RUC activo para emitir comprobantes |
| **IGV** | Impuesto General a la Venta (18%). Se aplica sobre operaciones gravadas. | 18% = 16% IGV + 2% IPM |
| **Detracción (SPOT)** | Sistema de pago de obligaciones tributarias. Aplica a ciertos servicios, pero generalmente no a talleres automotrices. | Si el monto supera S/ 700 puede aplicar |
| **PLAME** | Planilla Mensual de Pagos. Declaración mensual a SUNAT con datos de trabajadores, ingresos, retenciones y aportes. | Sustituyó al PDT 601 |
| **T-Registro** | Registro de trabajadores ante SUNAT. Obligatorio antes del inicio de labores. | |
| **ESSALUD** | Aporte del 9% del empleador para seguro de salud del trabajador. | No descuenta del sueldo del trabajador |
| **ONP / AFP** | Sistemas de pensiones. ONP (13% del sueldo) o AFP (porcentaje variable según fondo). | Descuenta del sueldo del trabajador |
| **Boleta de Pago Electrónica** | Documento mensual que detalla ingresos, descuentos y neto de un trabajador. Debe firmarse digitalmente. | PDF/A con firma PAdES |
| **PLE (Programa de Libros Electrónicos)** | Sistema de SUNAT para llevar libros contables electrónicos: Registro de Ventas, Compras, Diario, Mayor. | Periodo mensual. Presentación hasta el día 15 del mes siguiente |
| **RVE (Registro de Ventas Electrónico)** | Libro electrónico que consolida todas las ventas del mes con sus comprobantes asociados. | Código SUNAT: 140100 |
| **Registro de Compras Electrónico** | Libro electrónico que consolida todas las compras del mes. | Código SUNAT: 080100 |
| **CDR (en PLE)** | No confundir con el CDR de comprobantes. En PLE es el Comprobante de Recepción que SUNAT devuelve al presentar los libros. | |

---

## RRHH / Laboral

| Término | Definición | Notas |
|:---|:---|:---|
| **Asignación Familiar** | Beneficio del 10% de la RMV (S/ 1,025 → S/ 102.50) para trabajadores con hijos menores o discapacitados. | |
| **CTS** | Compensación por Tiempo de Servicios. Depositada en mayo y noviembre. Equivale a 50% del sueldo + 1/6 de gratificación. | |
| **Gratificación** | Beneficio otorgado en julio y diciembre. Equivale a 1 sueldo completo + 9% ESSALUD. | |
| **Horas Extras** | Horas trabajadas fuera de la jornada. 25% adicional (2 primeras horas) y 35% (siguientes). | |
| **Comisión** | Porcentaje sobre ventas o servicios que se paga al técnico/vendedor como incentivo. | Configurable por producto y por vendedor |

---

## Técnico / Arquitectura

| Término | Definición | Notas |
|:---|:---|:---|
| **Tenant** | Cliente SaaS. Cada taller es un tenant independiente con sus propios datos, usuarios y configuración. | Aislamiento por `tenant_id` |
| **PBAC** | Permission-Based Access Control. Cada operación del sistema está protegida por un permiso específico. | Ej: `VENTA_EMITIR_FACTURA` |
| **Monolito Modular** | Arquitectura donde hay un solo deploy pero el código está organizado en módulos con interfaces bien definidas. | Recomendado sobre microservicios para este proyecto |
| **Hexagonal** | Patrón de arquitectura que separa el dominio (lógica de negocio) de la infraestructura (bases de datos, APIs, SOAP). | También llamado "puertos y adaptadores" |
| **Soap Client** | Componente que se conecta al web service SOAP de SUNAT para enviar comprobantes y recibir CDR. | Spring Web Services |
| **WebSocket** | Canal de comunicación bidireccional en tiempo real entre frontend y backend. | STOMP sobre SockJS |
| **JWT** | JSON Web Token. Token de autenticación que contiene el tenant_id, user_id y lista de permisos del usuario. | Se envía en cada request (header Authorization) |
| **CRON** | Tarea programada que se ejecuta automáticamente en intervalos definidos. | Ej: envío de RDB a las 23:00, recordatorios de citas |
| **PWA** | Progressive Web App. Aplicación web que puede instalarse en el móvil y usar cámara, notificaciones push, etc. | Para el check-in digital |
| **API Gateway** | Punto único de entrada al backend. Maneja autenticación, rate limiting, ruteo por tenant. | Spring Cloud Gateway |
| **SOAP (Simple Object Access Protocol)** | Protocolo XML sobre HTTP usado para integrarse con SUNAT. Más estricto que REST, requiere WSDL. | Obligatorio para facturación electrónica en Perú |
| **REST (Representational State Transfer)** | Estilo de arquitectura API basado en JSON/HTTP. Usado para todas las operaciones del sistema excepto SUNAT. | |
| **WSDL** | Web Services Description Language. Archivo XML que describe los servicios SOAP disponibles. | SUNAT provee WSDL para cada operación |

---

## Documentos del Sistema

| Documento | Tipo | Legal | Canal de Emisión |
|:---|:---:|:---:|:---|
| Cotización | Comercial | No vinculante | PDF vía WhatsApp/Email |
| Orden de Trabajo (OT) | Contractual | Sí (servicio aceptado) | PDF interno + firma cliente |
| Acta de Ingreso | Contractual | Sí (Ley 27269) | PDF/A firmado digitalmente |
| Boleta de Venta Electrónica | Tributario | Sí (SUNAT) | XML firmado → SOAP → CDR |
| Factura Electrónica | Tributario | Sí (SUNAT) | XML firmado → SOAP → CDR |
| Nota de Crédito | Tributario | Sí (SUNAT) | XML firmado → SOAP → CDR |
| Nota de Débito | Tributario | Sí (SUNAT) | XML firmado → SOAP → CDR |
| Resumen Diario de Boletas | Tributario | Sí (SUNAT) | XML firmado → SOAP → CDR |
| Boleta de Pago (RRHH) | Laboral | Sí (SUNAT/PLAME) | PDF/A firmado digitalmente |
| PLAME | Laboral/Tributario | Sí (SUNAT) | TXT plano → SUNAT |
| PLE (Libros Electrónicos) | Contable | Sí (Contaduría/SUNAT) | TXT → SUNAT |

---

## Estados de una Orden de Trabajo

```
Pendiente → En Progreso → En Revisión → Cerrado
                                                ↘
                                            Reabierto (Garantía)
```

| Estado | Descripción |
|:---|:---|
| **Pendiente** | OT creada pero aún no iniciada. Esperando asignación de técnico o disponibilidad de box. |
| **En Progreso** | El técnico está trabajando en el vehículo. Se pueden agregar insumos y registrar horas. |
| **En Revisión** | Trabajo terminado. Un supervisor/asesor revisa la calidad y completa datos antes de cerrar. |
| **Cerrado** | OT finalizada. Total calculado. Lista para facturación. Ya no se pueden modificar insumos ni horas. |
| **Reabierto** | OT cerrada que se reabrió para atender un reclamo de garantía. |

---

## Estados de un Comprobante Electrónico

```
Creado → Firmado → Enviado a SUNAT → Aceptado
                     ↘                    ↘
                   Pendiente de       Rechazado → Corregido → Reenviado
                   Reintento
```

| Estado | Descripción |
|:---|:---|
| **Creado** | XML generado pero aún no firmado. |
| **Firmado** | XML firmado con certificado digital, listo para envío. |
| **Enviado a SUNAT** | Comprobante enviado vía SOAP. Esperando respuesta. |
| **Pendiente de Reintento** | SUNAT no respondió o hubo error de conexión. Se reintentará. |
| **Aceptado** | CDR recibido con estado 0 (Aceptado). |
| **Rechazado** | CDR recibido con estado 2 (Rechazado). Se debe corregir y reenviar. |
| **Observado** | CDR recibido con estado 1 (Observación). Puede requerir corrección. |
