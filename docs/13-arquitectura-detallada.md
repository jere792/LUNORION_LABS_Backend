# Arquitectura Detallada — LUNORION LABS

---

## 1. Arquitectura Hexagonal (Puertos y Adaptadores)

Cada módulo del monolito sigue una arquitectura hexagonal con 3 capas internas:

```
┌──────────────────────────────────────────────────────────┐
│                    ADAPTADORES ENTRADA                      │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐  │
│  │ REST     │  │ WebSocket│  │ SOAP     │  │ CRON     │  │
│  │Controller│  │Handler   │  │Endpoint  │  │ Scheduler│  │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  └────┬─────┘  │
│       │             │             │             │         │
├───────┴─────────────┴─────────────┴─────────────┴─────────┤
│                    PUERTOS DE ENTRADA                        │
│              (interfaces que el dominio expone)              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │             ApplicationService (casos de uso)         │  │
│  └──────────────────────┬───────────────────────────────┘  │
│                         │                                  │
├─────────────────────────┴──────────────────────────────────┤
│                     DOMINIO (core)                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │ Entidades    │  │ Value Objects│  │ Domain Services  │  │
│  │ (Aggregate   │  │ (Money, RUC, │  │ (reglas de       │  │
│  │  Root)       │  │  Email, etc) │  │  negocio puras)  │  │
│  └──────────────┘  └──────────────┘  └──────────────────┘  │
│  ┌──────────────┐  ┌──────────────┐                        │
│  │ Domain Events│  │ Repositories │                        │
│  │ (factura     │  │ (interfaces) │                        │
│  │  emitida)    │  │              │                        │
│  └──────────────┘  └──────────────┘                        │
├────────────────────────────────────────────────────────────┤
│                    PUERTOS DE SALIDA                          │
│              (interfaces que el dominio necesita)            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │ Repositorio  │  │ Proveedor    │  │ Cliente SOAP     │  │
│  │ (interface)  │  │ Fecha/Hora   │  │ SUNAT (interfaz) │  │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────────┘  │
│         │                 │                 │              │
├─────────┴─────────────────┴─────────────────┴──────────────┤
│                    ADAPTADORES SALIDA                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │ JPA          │  │ Clock        │  │ SunatSoapClient  │  │
│  │ Repository   │  │ SystemImpl   │  │ (WS Template)    │  │
│  └──────────────┘  └──────────────┘  └──────────────────┘  │
│  ┌──────────────┐  ┌──────────────┐                        │
│  │ Event Publisher│  │ File Storage │                        │
│  │ (RabbitMQ /   │  │ (PDF/A, XML) │                        │
│  │  Spring Event)│  │              │                        │
│  └──────────────┘  └──────────────┘                        │
└────────────────────────────────────────────────────────────┘
```

### Reglas de Dependencia

| Capa | Depende de | No depende de |
|:---|:---|:---|
| **Dominio** | Solo Java/Kotlin estándar | Spring, JPA, HTTP, base de datos |
| **Aplicación** | Interfaces del dominio | Frameworks externos |
| **Infraestructura** | Interfaces del dominio | Otras implementaciones concretas |

---

## 2. Módulos del Monolito Modular

```
┌──────────────────────────────────────────────────────────┐
│                    API Gateway (compartido)                 │
│  Seguridad (JWT, tenant filter, rate limit, auditoría)   │
└──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬──┘
       │      │      │      │      │      │      │      │
┌──────▼─┐ ┌──▼───┐ ┌▼────┐ ┌▼───┐ ┌▼────┐ ┌▼────┐ ┌▼───┐ ┌▼───┐
│Invent. │ │Ventas│ │ OT  │ │Caja│ │RRHH │ │Citas│ │Admin│ │Docs│
│& Stock │ │      │ │     │ │    │ │Leg. │ │     │ │     │ │Leg.│
└───┬────┘ └──┬───┘ └──┬──┘ └──┬─┘ └──┬──┘ └──┬──┘ └──┬──┘ └──┬──┘
    │        │        │       │      │       │       │       │
    └────────┴────────┴───────┴──────┴───────┴───────┴───────┘
                           │
              ┌────────────▼────────────┐
              │    Kernel Compartido     │
              │  (entidades base, value  │
              │   objects, utilidades)   │
              └─────────────────────────┘
```

### Dependencias entre Módulos

| Módulo | Depende de | Dependencias externas |
|:---|:---|:---|
| **Kernel** | — | PostgreSQL |
| **Inventario & Stock** | Kernel | PostgreSQL |
| **Compras & Proveedores** | Kernel, Inventario | PostgreSQL |
| **Ventas** | Kernel, Inventario, Clientes | PostgreSQL, SOAP SUNAT |
| **OT** | Kernel, Inventario, Clientes, Vehículos | PostgreSQL |
| **Cotizaciones** | Kernel, Clientes, Inventario | PostgreSQL |
| **Caja** | Kernel, Ventas | PostgreSQL |
| **Clientes** | Kernel | PostgreSQL |
| **Vehículos** | Kernel, Clientes | PostgreSQL |
| **Citas** | Kernel, Clientes, Vehículos | PostgreSQL |
| **Check-in** | Kernel, Clientes, Vehículos, OT | PostgreSQL, File Storage |
| **RRHH** | Kernel | PostgreSQL |
| **Admin/Multitenant** | Kernel | PostgreSQL |
| **Legal (SUNAT)** | Kernel, Ventas | PostgreSQL, SOAP SUNAT, PSE |

> **Nota:** Todas las dependencias entre módulos son vía **interfaces/eventos**, nunca llamadas directas a repositorios de otro módulo.

---

## 3. Estructura de Paquetes (Spring Boot) — Vertical Slicing

Cada módulo se organiza en **vertical slices** independientes. Cada slice representa un caso de uso o entidad del dominio, con su propio hexágono (application/domain/infrastructure).

```
com.lunorion.labs
│
├── kernel/                                  ← Kernel Compartido
│   ├── domain/
│   │   ├── entity/                          ← Entidades base (Auditable)
│   │   ├── vo/                              ← Value Objects (Money, RUC, Email, Placa)
│   │   └── event/                           ← Eventos de dominio base
│   └── infrastructure/
│       ├── config/                          ← Config global (CORS, Jackson, Security)
│       ├── jpa/                             ← BaseEntity, TenantFilter
│       ├── security/                        ← JWT, PBAC, TenantContext
│       └── util/                            ← DateUtils, FileUtils
│
├── inventory/                               ← Módulo Inventario & Stock
│   ├── inventory.module.ts                  ← NestJS @Module (o @Configuration en Spring)
│   │
│   ├── registrar-ingreso/                   ← Vertical slice
│   │   ├── application/
│   │   │   ├── dto/          ← RegistrarIngresoRequest.java, RegistrarIngresoResponse.java
│   │   │   ├── mapper/       ← IngresoMapper.java
│   │   │   └── service/      ← RegistrarIngresoUseCase.java
│   │   ├── domain/
│   │   │   ├── entity/       ← MovimientoStock.java, Producto.java
│   │   │   ├── models/       ← StockInsuficienteException.java
│   │   │   └── ports/        ← MovimientoStockRepository.java (interface)
│   │   └── infrastructure/
│   │       ├── adapters/     ← RegistrarIngresoController.java
│   │       └── persistence/  ← JpaMovimientoStockRepository.java
│   │
│   ├── registrar-egreso/                    ← Vertical slice
│   │   ├── application/
│   │   │   ├── dto/
│   │   │   ├── mapper/
│   │   │   └── service/
│   │   ├── domain/
│   │   │   └── ports/
│   │   └── infrastructure/
│   │       ├── adapters/
│   │       └── persistence/
│   │
│   ├── consultar-stock/                     ← Vertical slice
│   │   └── ... mismo patrón
│   │
│   ├── producto/                            ← Vertical slice (entidad raíz del módulo)
│   │   ├── domain/
│   │   │   ├── entity/       ← Producto.java (aggregate root)
│   │   │   ├── models/       ← CategoriaProducto.java
│   │   │   └── ports/        ← ProductoRepository.java
│   │   └── infrastructure/
│   │       └── persistence/  ← JpaProductoRepository.java
│   │
│   └── alertas-stock-minimo/                ← Vertical slice
│       └── ...
│
├── sales/                                   ← Módulo Ventas
│   ├── sales.module.ts
│   │
│   ├── registrar-venta/
│   │   ├── application/
│   │   │   ├── dto/
│   │   │   ├── mapper/
│   │   │   └── service/      ← RegistrarVentaUseCase.java
│   │   ├── domain/
│   │   │   ├── entity/       ← Venta.java, ItemVenta.java
│   │   │   └── ports/        ← VentaRepository.java
│   │   └── infrastructure/
│   │       ├── adapters/     ← VentaController.java
│   │       └── persistence/  ← JpaVentaRepository.java
│   │
│   ├── emitir-factura/
│   │   ├── domain/
│   │   │   ├── entity/       ← ComprobanteElectronico.java
│   │   │   ├── models/       ← SunatResponse.java
│   │   │   └── ports/        ← SunatSender.java (puerto salida SOAP)
│   │   └── infrastructure/
│   │       ├── adapters/     ← FacturaController.java
│   │       └── persistence/  ← JpaComprobanteRepository.java, SunatSoapAdapter.java
│   │
│   ├── emitir-boleta/
│   │   └── ... mismo patrón
│   │
│   ├── emitir-nota-credito/
│   │   └── ...
│   │
│   ├── nota-debito/
│   │   └── ...
│   │
│   ├── resumen-diario-boletas/
│   │   └── ...
│   │
│   └── generar-ple/
│       └── ...
│
├── workorder/                               ← Módulo Órdenes de Trabajo
│   ├── workorder.module.ts
│   ├── crear-ot/
│   ├── gestionar-insumos/
│   ├── gestionar-mano-obra/
│   ├── tablero-kanban/
│   ├── cerrar-ot/
│   └── reabrir-garantia/
│
├── cashier/                                 ← Módulo Caja
│   ├── cashier.module.ts
│   ├── registrar-cobro/
│   ├── ejecutar-cierre/
│   └── historial-caja/
│
├── customers/                               ← Módulo Clientes
│   ├── customers.module.ts
│   ├── registrar-cliente/
│   ├── historial-trabajos/
│   ├── historial-compras/
│   └── rentabilidad-cliente/
│
├── vehicles/                                ← Módulo Vehículos
│   ├── vehicles.module.ts
│   ├── crear-vehiculo/
│   └── vincular-cliente/
│
├── appointments/                            ← Módulo Citas
│   ├── appointments.module.ts
│   ├── agendar-cita/
│   ├── calendario-disponibilidad/
│   └── configurar-notificaciones/
│
├── checkin/                                 ← Módulo Check-in Digital
│   ├── checkin.module.ts
│   ├── capturar-fotos/
│   ├── firmar-acta/
│   └── convertir-en-ot/
│
├── purchasing/                              ← Módulo Compras & Proveedores
│   ├── purchasing.module.ts
│   ├── crear-orden-compra/
│   ├── recibir-orden/
│   └── gestionar-proveedores/
│
├── quotes/                                  ← Módulo Cotizaciones
│   ├── quotes.module.ts
│   ├── crear-cotizacion/
│   ├── enviar-cliente/
│   └── convertir-en-ot/
│
├── hr/                                      ← Módulo RRHH
│   ├── hr.module.ts
│   ├── registrar-asistencia/
│   ├── configurar-comisiones/
│   ├── generar-boleta-pago/
│   └── generar-plame/
│
├── legal/                                   ← Módulo Legal (SUNAT)
│   ├── legal.module.ts
│   ├── firmar-xml/
│   ├── enviar-sunat/
│   ├── consultar-cdr/
│   └── generar-ple/
│
├── admin/                                   ← Módulo Administración / Multitenant
│   ├── admin.module.ts
│   ├── crear-tenant/
│   ├── gestionar-usuarios/
│   ├── asignar-permisos/
│   └── metricas-globales/
│
├── dashboard/                               ← Módulo Dashboard
│   ├── dashboard.module.ts
│   ├── ver-kpis/
│   ├── rentabilidad/
│   └── exportar-reportes/
│
└── shared/
    ├── infrastructure/
    │   ├── websocket/                       ← Config STOMP, handlers
    │   ├── soap/                            ← Config WS compartida (marshaller, template)
    │   └── audit/                           ← Auditoría transversal (Spring AOP)
    └── config/                              ← ApplicationProperties, TenantInterceptor, Jackson
```

---

## 4. Diagrama de Deployment

```
┌──────────────────────────────────────────────────────────────┐
│                     CLIENTE (Browser / PWA)                    │
│               Angular Standalone + SockJS (WS)                │
└──────────┬──────────────────────────────┬────────────────────┘
           │ HTTPS (443)                  │ WSS (443)
           │ REST JSON                    │ STOMP over SockJS
┌──────────▼──────────────────────────────▼────────────────────┐
│              LOAD BALANCER (nginx / HAProxy)                    │
│       · SSL termination · Rate limiting · Routing              │
└──────────────────────────┬───────────────────────────────────┘
                           │
┌──────────────────────────▼───────────────────────────────────┐
│              API GATEWAY (Spring Cloud Gateway)                │
│  · JWT Validation · Tenant Resolution · Request Logging      │
│  · Rate Limiting · CORS                                       │
└──────┬───────────────────────────────────────────┬───────────┘
       │ REST                                      │ WS
┌──────▼───────────────────────────────────────────▼───────────┐
│              SPRING BOOT (Monolito Modular)                    │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐ │
│  │  Módulos: inventory, sales, workorder, cashier, hr,     │ │
│  │  legal, admin, appointments, checkin, customers, quotes │ │
│  │  ┌──────────── Kernel Compartido ───────────────┐       │ │
│  │  │ PBAC, JWT, Auditoría, TenantFilter, EventBus │       │ │ │
│  │  └──────────────────────────────────────────────┘       │ │
│  └──────────────────────────────────────────────────────────┘ │
│                                                                 │
│  ┌──────────────────────┐  ┌──────────────────────────────┐  │
│  │ WebSocket (STOMP)    │  │ SOAP Client (Spring WS)      │  │
│  │ - /topic/dashboard   │  │ - sendBill (factura, NC, ND) │  │
│  │ - /topic/stock       │  │ - sendSummary (RDB)          │  │
│  │ - /topic/invoices    │  │ - getStatus                  │  │
│  │ - /topic/kanban      │  └──────────┬───────────────────┘  │
│  └──────────────────────┘             │                       │
└───────────────────────────────────────┼───────────────────────┘
                                        │
┌───────────────────────────────────────▼───────────────────────┐
│              POSTGRESQL (RDS / Cloud SQL)                      │
│  · Aislamiento por tenant_id (fila)                           │
│  · Esquema compartido entre módulos                           │
│  · Índices compuestos: (tenant_id, created_at)                │
│  · Particionamiento por mes para auditoría y movimientos      │
└───────────────────────────────────────────────────────────────┘
                                        │
                    ┌───────────────────┼───────────────────┐
                    │                   │                   │
┌───────────────────▼────┐  ┌──────────▼────────┐  ┌──────▼───────────┐
│   SUNAT OSE / OSB      │  │  File Storage      │  │  Redis            │
│   (SOAP over HTTPS)    │  │  (PDF/A, XML, CDR) │  │  (Caching,        │
│   · WSDL fijo SUNAT    │  │  S3 / MinIO /      │  │   Session Store,  │
│   · WS-Security        │  │  Local FS           │  │   Rate Limit)     │
│   · Certificado .p12   │  └────────────────────┘  └──────────────────┘
└────────────────────────┘
```

---

## 5. Patrones de Diseño

| Patrón | Capa | Uso | Ejemplo |
|:---|:---|:---|:---|
| **Hexagonal (Ports & Adapters)** | Global | Separar dominio de infraestructura | Cada módulo tiene domain/application/infrastructure |
| **CQRS** | Aplicación | Separar commands de queries | `RegistrarVentaUseCase` (command) vs `ConsultarStockUseCase` (query) |
| **Repository** | Infraestructura | Abstraer persistencia | `JpaProductoRepository` implementa `ProductoRepository` |
| **Domain Event** | Dominio | Notificar cambios entre módulos | `StockMinimoExcedidoEvent` → WebSocket alert |
| **Strategy** | Aplicación | Métodos de pago | `PagoEfectivo`, `PagoTarjeta`, `PagoTransferencia` |
| **Factory** | Dominio | Crear aggregates complejos | `ComprobanteFactory.crearFactura(...)` |
| **Specification** | Dominio | Reglas de negocio reutilizables | `StockSuficienteSpec`, `RUCValidoSpec` |
| **DTO** | Aplicación | No exponer entidades en API | `VentaRequest`, `VentaResponse` |
| **Adapter** | Infraestructura | Envolver librerías externas | `SunatSoapAdapter` envuelve `WebServiceTemplate` |
| **Interceptor** | Infraestructura | Auditoría transversal | `AuditInterceptor` via Spring AOP |
| **Filter** | Infraestructura | Seguridad transversal | `TenantFilter` extrae tenant_id del JWT |

---

## 6. Flujo de un Request (Ejemplo: Crear OT)

```
Angular                     API Gateway            Módulo OT                    PostgreSQL
   │                            │                      │                          │
   │  POST /work-orders         │                      │                          │
   │  Authorization: Bearer JWT │                      │                          │
   │───────────────────────────>│                      │                          │
   │                            │  Validar JWT         │                          │
   │                            │  Extraer tenant_id   │                          │
   │                            │  Extraer permisos    │                          │
   │                            │──────────────────────│─────────────────────────>│
   │                            │                      │                          │
   │                            │  POST /internal/ot   │                          │
   │                            │  (con tenant_id,     │                          │
   │                            │   usuario, permisos  │                          │
   │                            │   en headers internos)                          │
   │                            │─────────────────────>│                          │
   │                            │                      │                          │
   │                            │                      │  Validar @PreAuthorize   │
   │                            │                      │  ("OT_CREAR")            │
   │                            │                      │                          │
   │                            │                      │  CrearOTUseCase          │
   │                            │                      │  ├─ Validar cliente existe│
   │                            │                      │  ├─ Validar vehículo     │
   │                            │                      │  ├─ Validar técnico     │
   │                            │                      │  ├─ Calcular fechas     │
   │                            │                      │  └─ OTRepository.save() │
   │                            │                      │─────────────────────────>│
   │                            │                      │<─────────────────────────│
   │                            │                      │  OT(id, numero, estado) │
   │                            │                      │                          │
   │                            │                      │  Publicar evento         │
   │                            │                      │  (OT Creada)             │
   │                            │                      │  → WebSocket /topic/kanban│
   │                            │                      │                          │
   │                            │<─────────────────────│                          │
   │                            │                      │                          │
   │  HTTP 201 + OTResponse     │                      │                          │
   │<───────────────────────────│                      │                          │
```

---

## 7. Arquitectura Frontend (Angular Standalone)

```
src/
├── app/
│   ├── core/                          ← Singleton, una instancia
│   │   ├── guards/                    ← CanActivate (permisos)
│   │   ├── interceptors/              ← JWT interceptor, error handler
│   │   ├── services/                  ← AuthService, WebSocketService
│   │   └── store/                     ← Estado global (NgRx o Signals)
│   │
│   ├── shared/                        ← Componentes reutilizables
│   │   ├── components/                ← Botones, modales, tablas
│   │   ├── pipes/                     ← Fechas, moneda (S/.)
│   │   └── validators/                ← RUC, DNI, placa, email
│   │
│   ├── features/                      ← Módulos funcionales (lazy loading)
│   │   ├── auth/                      ← Login, recuperación
│   │   ├── inventory/                 ← Productos, stock, movimientos
│   │   ├── sales/                     ← Ventas, facturación
│   │   ├── work-orders/               ← OT, Kanban
│   │   ├── cashier/                   ← Caja, arqueo
│   │   ├── customers/                 ← Clientes
│   │   ├── vehicles/                  ← Vehículos
│   │   ├── appointments/              ← Citas, calendario
│   │   ├── checkin/                   ← Check-in digital
│   │   ├── hr/                        ← RRHH, asistencia, boletas
│   │   ├── admin/                     ← Usuarios, permisos, tenant
│   │   └── dashboard/                 ← KPIs, reportes
│   │
│   └── app.config.ts                  ← Config de rutas, providers
│
├── assets/
│   ├── i18n/                          ← Español (futuro multi-idioma)
│   └── images/
│
└── environments/
    ├── environment.ts                  ← Dev
    └── environment.prod.ts             ← Prod
```

### Comunicación Frontend ↔ Backend

| Canal | Protocolo | Uso |
|:---|:---|:---|
| REST | HTTPS + JSON + JWT | CRUD de todas las entidades |
| WebSocket | WSS + STOMP + SockJS | Tiempo real: KPIs, alertas stock, CDR, kanban |
| Descarga | HTTPS + Blob | PDF (boletas, actas), XML (CDR, comprobantes) |

---

## 8. Estrategia de Multitenant

```
┌──────────────────────────────────────────────────┐
│                    JWT Token                        │
│  {                                                  │
│    "sub": "uuid-usuario",                          │
│    "tenant_id": "uuid-tenant",                     │
│    "rol": "ADMIN",                                 │
│    "permisos": ["INVENTARIO_VER_STOCK", ...],      │
│    "exp": 1700000000                               │
│  }                                                  │
└──────────────────────┬───────────────────────────┘
                       │
┌──────────────────────▼───────────────────────────┐
│           TenantFilter (OncePerRequestFilter)      │
│  1. Extrae tenant_id del JWT                       │
│  2. Lo setea en TenantContext (ThreadLocal)        │
│  3. Hibernate Filter: WHERE tenant_id = :tenant   │
│  4. Si no coincide con el recurso solicitado → 403 │
└────────────────────────────────────────────────┘
```

### Aislamiento

| Estrategia | Cómo |
|:---|:---|
| **Por fila** | Toda tabla tiene `tenant_id` como FK. Hibernate filter `@Filter(name="tenantFilter")` |
| **Esquema** | Un esquema por tenant (PostgreSQL `SET search_path`). Escalable pero más complejo. |
| **Base de datos** | Una DB por tenant. Aislamiento total pero operación compleja. |

**Elección:** Aislamiento **por fila** (row-level security) con `tenant_id` en cada tabla. Usamos Spring `@Filter` + `TenantContext` (ThreadLocal).

---

## 9. Manejo de Eventos entre Módulos

```
┌─────────────┐          ┌─────────────────┐          ┌─────────────┐
│  Inventario  │          │  Spring EventBus  │          │  Ventas     │
│              │          │  (ApplicationEventPublisher)  │             │
│  Stock       │─────────>│  StockActualizadoEvent │──────>│  Actualiza  │
│  disminuido  │          └─────────────────┘          │  precio venta│
└─────────────┘                                        └─────────────┘
                              │
                              ▼
                    ┌─────────────────┐
                    │  WebSocket       │
                    │  Notificador     │
                    │  → /topic/stock  │
                    └─────────────────┘
```

**Eventos de Dominio definidos:**

| Evento | Emisor | Receptor(es) | Propósito |
|:---|:---|:---|:---|
| `StockMinimoExcedidoEvent` | Inventario | WebSocket, Dashboard | Alerta stock crítico |
| `StockActualizadoEvent` | Inventario | Ventas, Compras | Actualizar precios/reorden |
| `VentaRegistradaEvent` | Ventas | Caja, Legal | Iniciar cobro + facturación |
| `ComprobanteEmitidoEvent` | Legal | WebSocket, Dashboard | Notificar CDR |
| `OTCerradaEvent` | OT | Caja, Ventas | Habilitar cobro + factura |
| `CitaConfirmadaEvent` | Citas | Check-in, WebSocket | Preparar ingreso |
| `CheckinCompletadoEvent` | Check-in | OT | Crear OT automática |
| `UsuarioCreadoEvent` | Admin | Kernel | Inicializar permisos |

---

## 10. Consideraciones de Performance

| Aspecto | Estrategia |
|:---|:---|
| **Caching** | Redis para catálogos (productos, categorías, clientes frecuentes). TTL configurable. |
| **Paginación** | Obligatoria en todos los listados. Tamaño default 20, máximo 100. |
| **Índices** | `(tenant_id, created_at DESC)` en tablas transaccionales. `(tenant_id, codigo)` en catálogos. |
| **N+1 Queries** | Usar `@EntityGraph` o `JOIN FETCH` explícito. Prohibido `FetchType.EAGER` global. |
| **Timeouts** | SOAP SUNAT: 10s connect + 30s read. DB: 5s query timeout. REST: 30s default. |
| **Rate Limiting** | 100 req/min por usuario, 10 req/min SOAP, 5 req/min RDB. |
| **Batch Processing** | Resumen diario de boletas y PLAME en CRON nocturno, fuera del request del usuario. |
| **Compresión** | Gzip en respuestas REST > 1KB. XML SOAP comprimido si > 1MB. |

---

## 11. Stack de Dependencias (Backend)

| Dependencia | Versión | Propósito |
|:---|:---|:---|
| Spring Boot | 3.2+ | Framework base |
| Spring Web | 3.2+ | REST Controllers |
| Spring Security | 6.x | JWT + PBAC |
| Spring Data JPA | 3.2+ | Persistencia |
| Spring Web Services | 4.x | Cliente SOAP SUNAT |
| Spring WebSocket | 6.x | STOMP + SockJS |
| PostgreSQL Driver | 42.x | Driver JDBC |
| Flyway | 10.x | Migraciones DB |
| JJWT (io.jsonwebtoken) | 0.12.x | Generación JWT |
| OpenPDF | 2.x | PDF (actas, boletas, cotizaciones) |
| Apache Santuario | 3.x | Firma XML (XAdES) |
| Lombok | — | Reducir boilerplate |
| MapStruct | 1.6.x | Mapeo DTO ↔ Entidad |
| Redis (Spring Data Redis) | — | Caché y rate limiting |
| Testcontainers | — | Tests de integración |
| OpenAPI (SpringDoc) | 2.x | Documentación API |
