package com.lunorion.labs.core.cotizacion.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class CotizacionItem {
    private UUID id;
    private String cotizacionId;
    private String descripcion;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public CotizacionItem() {}

    public CotizacionItem(String descripcion, Integer cantidad, BigDecimal precioUnitario) {
        this.id = UUID.randomUUID();
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public CotizacionItem(UUID id, String cotizacionId, String descripcion, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.id = id;
        this.cotizacionId = cotizacionId;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCotizacionId() { return cotizacionId; }
    public void setCotizacionId(String cotizacionId) { this.cotizacionId = cotizacionId; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
