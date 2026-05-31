# Matriz de Permisos (PBAC) — LUNORION LABS

Control de Acceso Basado en Permisos. Cada operación del sistema requiere un permiso específico. Los permisos se asignan **individualmente a cada usuario**, no por roles fijos.

---

## Roles del Sistema

Existen 3 niveles. Todo el control fino se maneja asignando permisos específicos por usuario.

| Rol | Código | Ámbito | Propósito |
|:---|:---|:---|:---|
| **Super Admin** | `SUPER_ADMIN` | Global (SaaS) | Dueño de la plataforma SaaS. Gestiona tenants (talleres), métricas globales, certificados digitales. Crea el ADMIN de cada taller. |
| **Admin del Taller** | `ADMIN` | Por tenant | Dueño/gerente del taller. Acceso total a su tenant. Crea usuarios PUBLIC y asigna permisos. |
| **Público** | `PUBLIC` | Por tenant | Cliente del servicio — empleado del taller (cajero, técnico, vendedor, recepcionista, asesor). Solo accede a los permisos que el ADMIN del taller le asigne. |

No hay roles predefinidos como "CAJERO" o "TECNICO". Cada usuario PUBLIC puede tener **cualquier combinación de permisos** según lo que el ADMIN del taller le configure.

---

## Esquema de Asignación

```
SUPER_ADMIN (SaaS)
├── Crea tenants (talleres)
├── Asigna ADMIN de cada taller
├── Ve métricas globales
└── Gestiona certificados digitales

ADMIN (dueño/gerente del taller)
├── Permisos: TODOS dentro de su tenant (implícito)
├── Crea usuarios PUBLIC
├── Asigna permisos individuales a cada PUBLIC
└── Ej: Juan Pérez (ADMIN) → acceso completo

PUBLIC (empleados del taller)
    ├── Permisos: según lo que el ADMIN le asigne
    ├── Ej: María (cajera) → CAJA_REGISTRAR_COBRO + CAJA_EJECUTAR_CIERRE + CAJA_VER_HISTORIAL
    ├── Ej: Pedro (técnico) → OT_GESTIONAR_INSUMOS + OT_GESTIONAR_MANO_OBRA + INVENTARIO_VER_STOCK
    └── Ej: Lucía (vendedora+recepción) → VENTA_REGISTRAR_MOSTRADOR + VENTA_EMITIR_FACTURA + CITA_CREAR + CLIENTE_CREAR
```

El ADMIN puede asignar permisos de forma granular sin estar limitado por roles rígidos. Un cajero puede ver el módulo de inventario si el ADMIN lo decide.

---

## Catálogo Completo de Permisos

Catálogo de todos los permisos disponibles en el sistema. El ADMIN marca los que necesita cada usuario.

### Inventario & Stock

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-01 | `INVENTARIO_REGISTRAR_INGRESO` | Registrar ingreso de mercadería al depósito |
| P-02 | `INVENTARIO_REGISTRAR_EGRESO` | Registrar egreso de producto (venta/consumo) |
| P-03 | `INVENTARIO_VER_STOCK` | Consultar stock actual en tiempo real |
| P-04 | `INVENTARIO_VER_ALERTAS` | Recibir alertas de stock mínimo |
| P-05 | `INVENTARIO_VER_HISTORIAL` | Ver historial de movimientos de un producto |
| P-06 | `INVENTARIO_VER_REPORTES` | Ver panel de rotación de productos |

### Compras & Proveedores

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-07 | `COMPRA_CREAR_ORDEN` | Crear orden de compra |
| P-08 | `COMPRA_RECIBIR_ORDEN` | Confirmar recepción de compra y actualizar stock |
| P-09 | `COMPRA_VER_REPORTE_GASTOS` | Ver reporte de gasto en compras |
| P-10 | `PROVEEDOR_GESTIONAR` | Gestionar proveedores (CRUD) |

### Ventas

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-11 | `VENTA_REGISTRAR_MOSTRADOR` | Registrar venta de mostrador con descuento de stock |
| P-12 | `VENTA_EMITIR_BOLETA` | Emitir boleta de venta electrónica (SUNAT vía SOAP) |
| P-13 | `VENTA_EMITIR_FACTURA` | Emitir factura electrónica (SUNAT vía SOAP) |
| P-14 | `VENTA_EMITIR_NOTA_CREDITO` | Emitir nota de crédito electrónica |
| P-15 | `VENTA_EMITIR_NOTA_DEBITO` | Emitir nota de débito electrónica |
| P-16 | `VENTA_GENERAR_RESUMEN_DIARIO` | Generar resumen diario de boletas |
| P-17 | `VENTA_VER_CDR` | Consultar CDR de SUNAT |
| P-18 | `VENTA_REENVIAR_COMPROBANTE` | Reenviar comprobante rechazado por SUNAT |
| P-19 | `VENTA_GENERAR_PLE` | Generar libros electrónicos (PLE) |
| P-20 | `VENTA_VER_REPORTE_FACTURACION` | Ver reporte de facturación |
| P-21 | `VENTA_VER_HISTORIAL_CLIENTE` | Ver historial de compras y servicios del cliente desde ventas |

### Cotizaciones

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-22 | `COTIZACION_CREAR` | Generar cotización detallada |
| P-23 | `COTIZACION_ENVIAR_CLIENTE` | Enviar cotización al cliente por WhatsApp/Email |
| P-24 | `COTIZACION_CONVERTIR_OT` | Convertir cotización aprobada en OT |
| P-25 | `COTIZACION_VER_HISTORIAL` | Ver historial de cotizaciones |

### Órdenes de Trabajo

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-26 | `OT_CREAR` | Crear orden de trabajo |
| P-27 | `OT_GESTIONAR_INSUMOS` | Agregar/quitar insumos a OT y descontar stock |
| P-28 | `OT_GESTIONAR_MANO_OBRA` | Registrar horas de mano de obra en OT |
| P-29 | `OT_VER_TABLERO` | Ver tablero Kanban de OTs |
| P-30 | `OT_CERRAR` | Cerrar OT y calcular total |
| P-31 | `GARANTIA_REABRIR_OT` | Reabrir OT cerrada para garantía |
| P-32 | `GARANTIA_REGISTRAR_COSTOS` | Registrar costos de garantía |
| P-33 | `GARANTIA_VER_REPORTE` | Ver reporte de pérdidas por garantías |

### Citas & Reservas

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-34 | `CITA_CREAR` | Agendar cita |
| P-35 | `CITA_VER_CALENDARIO` | Ver calendario de disponibilidad |
| P-36 | `CITA_CONFIGURAR_NOTIFICACIONES` | Configurar recordatorios automáticos |

### Vehículos

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-37 | `VEHICULO_CREAR` | Crear ficha de vehículo |
| P-38 | `VEHICULO_VER_HISTORIAL` | Ver historial de servicios del vehículo |
| P-39 | `VEHICULO_VINCULAR_CLIENTE` | Asociar vehículo a cliente |

### Clientes

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-40 | `CLIENTE_CREAR` | Registrar cliente |
| P-41 | `CLIENTE_VER_HISTORIAL_TRABAJOS` | Ver historial de trabajos del cliente |
| P-42 | `CLIENTE_VER_HISTORIAL_COMPRAS` | Ver historial de compras del cliente |
| P-43 | `CLIENTE_VER_RENTABILIDAD` | Ver facturación acumulada por cliente |

### Técnicos

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-44 | `TECNICO_CREAR` | Registrar técnico |
| P-45 | `TECNICO_VER_CARGA_TRABAJO` | Ver carga de trabajo del técnico |

### Check-in Digital

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-46 | `CHECKIN_CAPTURAR_FOTOS` | Capturar fotos del vehículo al ingreso |
| P-47 | `CHECKIN_FIRMAR_ACTA` | Firmar acta de ingreso digital |
| P-48 | `CHECKIN_CONVERTIR_OT` | Convertir check-in en OT |

### Caja & Arqueo

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-49 | `CAJA_REGISTRAR_COBRO` | Registrar cobro con método de pago |
| P-50 | `CAJA_EJECUTAR_CIERRE` | Ejecutar cierre de caja diario |
| P-51 | `CAJA_VER_HISTORIAL` | Ver historial de cierres de caja |

### RRHH

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-52 | `RRHH_REGISTRAR_ASISTENCIA` | Registrar asistencia diaria del personal |
| P-53 | `RRHH_VER_PRODUCTIVIDAD` | Ver productividad del personal |
| P-54 | `RRHH_CONFIGURAR_COMISIONES` | Configurar reglas de comisión |
| P-55 | `RRHH_GENERAR_BOLETA_PAGO` | Generar boleta de pago electrónica |
| P-56 | `RRHH_GENERAR_PLAME` | Generar PLAME mensual |

### Dashboard & Reportes

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-57 | `DASHBOARD_VER_KPIS` | Ver dashboard de KPIs en tiempo real |
| P-58 | `REPORTE_VER_RENTABILIDAD` | Ver rentabilidad por tipo de servicio |
| P-59 | `REPORTE_EXPORTAR` | Exportar reportes a Excel/PDF |

### Catálogo de Productos

| ID | Código Permiso | Descripción |
|:---|:---|:---|
| P-60 | `PRODUCTO_GESTIONAR_CATEGORIAS` | Gestionar categorías de productos |
| P-61 | `PRODUCTO_CREAR` | Crear o editar producto |
| P-62 | `PRODUCTO_CREAR_RAPIDO` | Crear producto rápido desde compra/venta |

### Administración

| ID | Código Permiso | Descripción | Asignable a |
|:---|:---|:---|:---|
| P-63 | `CONFIG_USUARIO_CREAR` | Crear usuarios PUBLIC y asignar permisos | ADMIN, SUPER_ADMIN |
| P-64 | `SAAS_TENANT_CREAR` | Registrar nuevo taller (tenant) con su RUC y plan | SUPER_ADMIN |
| P-65 | `SAAS_VER_METRICAS` | Ver métricas globales de todos los tenants | SUPER_ADMIN |

---

## Ejemplos de Configuración por Usuario

| Usuario | Puesto | Rol | Permisos Asignados |
|:---|:---|:---|:---|
| **Carlos** | Dueño de la plataforma | SUPER_ADMIN | Todos (global). Crea tenants, ve métricas globales |
| **María** | Dueña del Taller "AutoFix" | ADMIN | Todos dentro de su tenant (implícito) |
| **Lucía** | Cajera | PUBLIC | P-49 (CAJA_REGISTRAR_COBRO), P-50 (CAJA_EJECUTAR_CIERRE), P-51 (CAJA_VER_HISTORIAL), P-57 (DASHBOARD_VER_KPIS) |
| **Pedro** | Técnico | PUBLIC | P-27 (OT_GESTIONAR_INSUMOS), P-28 (OT_GESTIONAR_MANO_OBRA), P-29 (OT_VER_TABLERO), P-03 (INVENTARIO_VER_STOCK) |
| **Lucía** | Vendedora + Recepción | PUBLIC | P-11 (VENTA_REGISTRAR_MOSTRADOR), P-12 (VENTA_EMITIR_BOLETA), P-13 (VENTA_EMITIR_FACTURA), P-14 (VENTA_EMITIR_NOTA_CREDITO), P-34 (CITA_CREAR), P-40 (CLIENTE_CREAR), P-46 (CHECKIN_CAPTURAR_FOTOS), P-47 (CHECKIN_FIRMAR_ACTA) |
| **Roberto** | Asesor de servicio | PUBLIC | P-22 (COTIZACION_CREAR), P-24 (COTIZACION_CONVERTIR_OT), P-26 (OT_CREAR), P-30 (OT_CERRAR), P-37 (VEHICULO_CREAR), P-40 (CLIENTE_CREAR) |
| **Jorge** | Administrativo | PUBLIC | P-07 (COMPRA_CREAR_ORDEN), P-08 (COMPRA_RECIBIR_ORDEN), P-10 (PROVEEDOR_GESTIONAR), P-52 (RRHH_REGISTRAR_ASISTENCIA), P-54 (RRHH_CONFIGURAR_COMISIONES) |

---

## Implementación en Backend

```java
// Entidad: usuario_permiso
@Entity
@Table(name = "usuario_permiso")
public class UsuarioPermiso {
    @Id private UUID id;
    private UUID usuarioId;
    private String permisoCodigo;  // Ej: "VENTA_EMITIR_FACTURA"
    private LocalDateTime createdAt;
}

// Los permisos se cargan al hacer login y se incluyen en el JWT
// Cada endpoint verifica si el usuario tiene el permiso específico

@PreAuthorize("hasAuthority('VENTA_EMITIR_FACTURA')")
@PostMapping("/invoices")
public ResponseEntity<InvoiceResponse> emitInvoice(...) {
    // ...
}
```

**Reglas:**
1. `SUPER_ADMIN` tiene todos los permisos globales (puede ver todos los tenants)
2. `ADMIN` tiene todos los permisos dentro de su tenant (implícito, no necesita asignación)
3. `PUBLIC` solo tiene los permisos que el `ADMIN` de su taller le asigna en `usuario_permiso`
4. Si un usuario no tiene el permiso → HTTP 403
5. El `tenant_id` se extrae del JWT, nunca del request body
6. Todas las queries filtran por `tenant_id` del JWT

---

**Total: 65 permisos en el catálogo**
