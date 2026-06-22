package com.lunorion.labs.core.cotizacion.domain.entity;

import com.lunorion.labs.shared.domain.BaseEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cotizacion extends BaseEntity {

    private static final BigDecimal IGV_RATE = new BigDecimal("0.18");

    private String tenantId;
    private String clienteId;
    private String vehiculoId;
    private LocalDate fechaEmision;
    private LocalDate fechaValidez;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private String notas;
    private boolean activo;
    private List<CotizacionItem> items;

    public Cotizacion() {}

    public Cotizacion(UUID id, String tenantId, String clienteId, String vehiculoId,
                      LocalDate fechaEmision, LocalDate fechaValidez, String notas) {
        super(id);
        this.tenantId = tenantId;
        this.clienteId = clienteId;
        this.vehiculoId = vehiculoId;
        this.fechaEmision = fechaEmision;
        this.fechaValidez = fechaValidez;
        this.estado = "BORRADOR";
        this.notas = notas;
        this.activo = true;
        this.items = new ArrayList<>();
        this.subtotal = BigDecimal.ZERO;
        this.igv = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    public static Cotizacion create(String tenantId, String clienteId, String vehiculoId,
                                    LocalDate fechaEmision, LocalDate fechaValidez, String notas) {
        return new Cotizacion(UUID.randomUUID(), tenantId, clienteId, vehiculoId,
                fechaEmision, fechaValidez, notas);
    }

    public void enviar() {
        if (!"BORRADOR".equals(estado)) {
            throw new IllegalStateException("Solo cotizaciones en BORRADOR pueden enviarse");
        }
        this.estado = "ENVIADA";
        markUpdated();
    }

    public void aprobar() {
        if (!"ENVIADA".equals(estado)) {
            throw new IllegalStateException("Solo cotizaciones ENVIADA pueden aprobarse");
        }
        this.estado = "APROBADA";
        markUpdated();
    }

    public void rechazar() {
        if (!"ENVIADA".equals(estado)) {
            throw new IllegalStateException("Solo cotizaciones ENVIADA pueden rechazarse");
        }
        this.estado = "RECHAZADA";
        markUpdated();
    }

    public void convertirEnOt() {
        if (!"APROBADA".equals(estado)) {
            throw new IllegalStateException("Solo cotizaciones APROBADA pueden convertirse en OT");
        }
        this.estado = "CONVERTIDA";
        markUpdated();
    }

    public void actualizar(String clienteId, String vehiculoId, String notas) {
        if (!"BORRADOR".equals(estado)) {
            throw new IllegalStateException("Solo cotizaciones en BORRADOR pueden actualizarse");
        }
        this.clienteId = clienteId;
        this.vehiculoId = vehiculoId;
        this.notas = notas;
        markUpdated();
    }

    public void agregarItem(String descripcion, int cantidad, BigDecimal precioUnitario) {
        CotizacionItem item = new CotizacionItem(descripcion, cantidad, precioUnitario);
        item.setCotizacionId(getId().toString());
        this.items.add(item);
        calcularTotal();
    }

    public void limpiarItems() {
        this.items.clear();
        calcularTotal();
    }

    public void calcularTotal() {
        this.subtotal = items.stream()
                .map(CotizacionItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.igv = subtotal.multiply(IGV_RATE).setScale(2, RoundingMode.HALF_UP);
        this.total = subtotal.add(igv);
    }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public String getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(String vehiculoId) { this.vehiculoId = vehiculoId; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public LocalDate getFechaValidez() { return fechaValidez; }
    public void setFechaValidez(LocalDate fechaValidez) { this.fechaValidez = fechaValidez; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getIgv() { return igv; }
    public void setIgv(BigDecimal igv) { this.igv = igv; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public List<CotizacionItem> getItems() { return items; }
    public void setItems(List<CotizacionItem> items) { this.items = items; }
}
