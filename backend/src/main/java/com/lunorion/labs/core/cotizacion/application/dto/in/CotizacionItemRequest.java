package com.lunorion.labs.core.cotizacion.application.dto.in;

import java.math.BigDecimal;

public class CotizacionItemRequest {
    private String descripcion;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
}
