# Sprint 02 — Erick (Base de Datos)

## Resumen

Responsable de crear las nuevas migraciones para el módulo de facturación electrónica: ventas, comprobantes, CDR, resumen diario, PLE y configuración SUNAT. También seed data de permisos para los nuevos endpoints.

---

## Tareas

| # | Tarea | Entidad | Detalle |
|:---:|:---|:---|:---|
| 1 | Migration: `configuracion_sunat` | Config | RUC, clave SOL, certificado PFX, endpoint homologación/producción, FK a tenant |
| 2 | Migration: `venta`, `venta_item`, `venta_pago` | Ventas | Items con producto_id, cantidades, precios, FK a stock; métodos de pago |
| 3 | Migration: `comprobante`, `comprobante_item` | Comprobantes | Serie, número, tipo (01, 03, 07, 08), estado_sunat, xml_firmado, cdr, FK a venta |
| 4 | Migration: `cdr` | CDR | Código respuesta, descripción, fecha SUNAT, FK a comprobante |
| 5 | Migration: `resumen_diario`, `resumen_diario_item` | RDB | FK a tenant, fecha, ticket, estado, boletas incluidas |
| 6 | Migration: `ple_registro` | PLE | FK a tenant, período, tipo (ventas/compras), estado, ruta_txt |
| 7 | Índices y constraints | Todas | Búsquedas por cliente, fecha, serie-número, tenant, tipo comprobante |
| 8 | Seed data: `permiso` para módulo facturación | Data | `VENTA_REGISTRAR_MOSTRADOR`, `VENTA_EMITIR_FACTURA`, `VENTA_EMITIR_BOLETA`, `VENTA_EMITIR_NOTA_CREDITO`, `VENTA_EMITIR_NOTA_DEBITO`, `VENTA_VER_CDR`, `VENTA_REENVIAR_COMPROBANTE`, `VENTA_GENERAR_RESUMEN_DIARIO`, `VENTA_VER_REPORTE_FACTURACION`, `VENTA_VER_HISTORIAL_CLIENTE`, `VENTA_GENERAR_PLE` |
| 9 | Seed data: `configuracion_sunat` demo (modo homologación) | Data | Para test en homologación SUNAT |

---

## Dependencias

| # | Depende de |
|:---:|:---|
| — | Sin dependencias externas |

---

## Estado Global

**Progreso:** 0/9 (0%)
