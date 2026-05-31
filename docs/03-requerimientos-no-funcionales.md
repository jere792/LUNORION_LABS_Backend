# Requerimientos No Funcionales — LUNORION LABS

---

## RNF-01: Aislamiento Multitenant

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-01 |
| **Categoría** | Seguridad / Arquitectura |
| **Descripción** | Los datos de cada tenant (taller) deben estar completamente aislados a nivel de fila mediante `tenant_id`. |
| **Criterio de Aceptación** | Ninguna consulta o transacción puede retornar o modificar datos de un tenant distinto al autenticado. |
| **Prioridad** | Crítica |

---

## RNF-02: Control de Acceso por Permisos (PBAC)

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-02 |
| **Categoría** | Seguridad |
| **Descripción** | Cada operación del sistema requiere un permiso específico validado en backend. No basta con ocultar botones en frontend. |
| **Criterio de Aceptación** | El backend rechaza con 403 cualquier endpoint invocado sin el permiso correspondiente. |
| **Prioridad** | Crítica |

---

## RNF-03: Tiempo Real en Consultas Críticas

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-03 |
| **Categoría** | Performance |
| **Descripción** | La consulta de stock actual (RF-03) y el dashboard de KPIs (RF-50) deben reflejar cambios ≤ 2 segundos después de una transacción. Las notificaciones vía WebSocket (RF-52b, RF-52c, RF-52d) deben entregarse en < 500ms. |
| **Criterio de Aceptación** | Time to response < 500ms para stock y < 2s para dashboard con datos actualizados. Eventos WebSocket entregados en < 500ms al destinatario. |
| **Prioridad** | Alta |

---

## RNF-04: Facturación Electrónica (SUNAT)

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-04 |
| **Categoría** | Integración / Compliance |
| **Descripción** | El sistema debe integrarse con SUNAT (OSE/OSB) vía SOAP con WS-Security y firma digital XML para emisión de boletas, facturas, notas de crédito, notas de débito y resúmenes diarios según normativa fiscal peruana (RS 188-2020/SUNAT). |
| **Criterio de Aceptación** | Los comprobantes se envían vía SOAP a SUNAT y se obtiene el CDR (Comprobante de Recepción) con estado aceptado/rechazado en < 30s. Los XML firmados se almacenan como respaldo legal. |
| **Prioridad** | Alta |

---

## RNF-05: Notificaciones Asíncronas

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-05 |
| **Categoría** | Integración / UX |
| **Descripción** | Envío de recordatorios de cita (RF-28) y cotizaciones (RF-18) vía WhatsApp/Email con latencia máxima de 1 minuto. |
| **Criterio de Aceptación** | El 95% de las notificaciones se entregan en < 60s. |
| **Prioridad** | Media |

---

## RNF-06: Concurrencia por Tenant

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-06 |
| **Categoría** | Performance / Escalabilidad |
| **Descripción** | Soportar al menos 50 usuarios concurrentes por tenant operando simultáneamente sin degradación perceptible. |
| **Criterio de Aceptación** | El sistema mantiene tiempos de respuesta < 2s con 50 usuarios concurrentes en un mismo tenant. |
| **Prioridad** | Alta |

---

## RNF-07: Disponibilidad

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-07 |
| **Categoría** | Confiabilidad |
| **Descripción** | 99.5% de uptime en horario comercial (lun–sáb, 7:00–20:00). Ventanas de mantenimiento programadas fuera de horario. |
| **Criterio de Aceptación** | Downtime máximo mensual ≤ 2 horas en horario comercial. |
| **Prioridad** | Alta |

---

## RNF-08: Auditoría y Trazabilidad

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-08 |
| **Categoría** | Seguridad / Compliance |
| **Descripción** | Todo movimiento de inventario (RF-05), cierre de caja (RF-46), modificación de OT y cambio de estado de comprobante debe registrar: usuario, timestamp, valor anterior, valor nuevo. |
| **Criterio de Aceptación** | Las tablas de auditoría son inmutables (solo insert, no update/delete) y consultables por entidad. |
| **Prioridad** | Alta |

---

## RNF-09: Exportación de Reportes

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-09 |
| **Categoría** | Interoperabilidad |
| **Descripción** | Los reportes exportados (RF-52) deben ser compatibles con Microsoft Excel 2016+ y formato PDF/A. |
| **Criterio de Aceptación** | El archivo XLSX se abre correctamente en Excel 2016 y el PDF/A pasa validación veraPDF. |
| **Prioridad** | Media |

---

## RNF-10: Escalabilidad Horizontal

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-10 |
| **Categoría** | Arquitectura |
| **Descripción** | El backend debe poder ejecutar múltiples instancias detrás de un balanceador de carga. El `tenant_id` debe permitir sharding por tenant si el volumen lo requiere. |
| **Criterio de Aceptación** | Al duplicar instancias, el throughput se duplica linealmente (sin contención en base de datos). |
| **Prioridad** | Alta |

---

## RNF-11: Compatibilidad Móvil (Check-in Digital)

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-11 |
| **Categoría** | UX / Mobile |
| **Descripción** | El check-in digital (RF-38, RF-39) debe funcionar en dispositivos móviles con cámara y soporte táctil. Captura de fotos y firma digital sin necesidad de app nativa (PWA). |
| **Criterio de Aceptación** | Funcionalidad completa en Chrome/Android y Safari/iOS. |
| **Prioridad** | Alta |

---

## RNF-12: Backup y Recuperación

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-12 |
| **Categoría** | Confiabilidad |
| **Descripción** | Backup automatizado diario de la base de datos con retención mínima de 30 días. RPO ≤ 24h, RTO ≤ 4h. |
| **Criterio de Aceptación** | Restauración exitosa de un backup en un entorno de prueba verificada mensualmente. |
| **Prioridad** | Alta |

---

## RNF-13: Encriptación de Datos Sensibles

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-13 |
| **Categoría** | Seguridad |
| **Descripción** | Las contraseñas se almacenan con bcrypt/argon2. Datos personales de clientes en reposo cifrados (AES-256). Todo el tráfico usa HTTPS/TLS 1.3. |
| **Criterio de Aceptación** | Escaneo de vulnerabilidades (SonarQube/ZAP) sin hallazgos críticos en autenticación o cifrado. |
| **Prioridad** | Crítica |

---

## RNF-14: Mantenibilidad del Código

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-14 |
| **Categoría** | Calidad de Código |
| **Descripción** | El backend debe seguir arquitectura hexagonal con separación clara de dominio, aplicación e infraestructura. |
| **Criterio de Aceptación** | Cobertura de pruebas ≥ 70%. Code smells < 100. Deuda técnica < 1h en SonarQube. |
| **Prioridad** | Media |

---

## RNF-15: Comunicación en Tiempo Real (WebSocket)

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-15 |
| **Categoría** | Performance / UX |
| **Descripción** | El sistema debe incorporar WebSockets (STOMP sobre SockJS) para notificaciones push en vivo: alertas de stock crítico (RF-04), CDR recibido (RF-52b), asignación de OT (RF-52c), check-in de citas (RF-52d) y actualización del dashboard de KPIs (RF-50). |
| **Criterio de Aceptación** | La conexión WebSocket se establece al autenticarse el usuario y se reconecta automáticamente en caso de caída. La latencia del evento desde el backend hasta el frontend es < 500ms. |
| **Prioridad** | Alta |

---

## RNF-16: Retención y Libros Electrónicos (PLE)

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-16 |
| **Categoría** | Compliance / Legal |
| **Descripción** | Los comprobantes electrónicos, XML firmados, CDR, boletas de pago y registros PLE deben conservarse por un mínimo de 5 años según normativa SUNAT y Contaduría. Los XML originales deben poder ser descargados individualmente o en lote. |
| **Criterio de Aceptación** | El sistema retiene todos los XML+CDR firmados por cada transacción. Los reportes PLE (RVE, Registro de Compras) se generan sin error para cualquier mes dentro del período de retención. |
| **Prioridad** | Crítica |

---

## RNF-17: Integración SOAP — Resiliencia y Timeouts

| Atributo | Detalle |
|:---|:---|
| **ID** | RNF-17 |
| **Categoría** | Integración / Confiabilidad |
| **Descripción** | El cliente SOAP hacia SUNAT debe implementar: timeout de conexión (10s), timeout de lectura (30s), reintento automático (3 intentos con backoff exponencial), cola de respaldo ante caída del OSE y notificación al usuario del estado del envío. |
| **Criterio de Aceptación** | Si SUNAT no responde, el comprobante se encola y reintenta automáticamente. El usuario ve el estado "Pendiente de envío a SUNAT" hasta obtener el CDR. |
| **Prioridad** | Alta |

---

**Total: 17 Requerimientos No Funcionales**
