package com.lunorion.labs.core.orden_trabajo.application.dto.in;

import java.math.BigDecimal;

public class AddInsumoRequest {
    private String productoId;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }
    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
}
