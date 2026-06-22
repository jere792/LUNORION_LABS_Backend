package com.lunorion.labs.core.orden_trabajo.application.dto.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreateOrdenTrabajoRequest {
    private String tenantId;
    private String clienteId;
    private String vehiculoId;
    private String tecnicoId;
    private String numeroOt;
    private String estado;
    private String motivoIngreso;
    private Integer kilometrajeIngreso;
    private Integer kilometrajeSalida;
    private LocalDate fechaPrometida;
    private String usuarioCreoId;
    private List<CreateOtInsumoRequest> insumos;
    private List<CreateOtManoObraRequest> manosObra;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public String getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(String vehiculoId) { this.vehiculoId = vehiculoId; }
    public String getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(String tecnicoId) { this.tecnicoId = tecnicoId; }
    public String getNumeroOt() { return numeroOt; }
    public void setNumeroOt(String numeroOt) { this.numeroOt = numeroOt; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMotivoIngreso() { return motivoIngreso; }
    public void setMotivoIngreso(String motivoIngreso) { this.motivoIngreso = motivoIngreso; }
    public Integer getKilometrajeIngreso() { return kilometrajeIngreso; }
    public void setKilometrajeIngreso(Integer kilometrajeIngreso) { this.kilometrajeIngreso = kilometrajeIngreso; }
    public Integer getKilometrajeSalida() { return kilometrajeSalida; }
    public void setKilometrajeSalida(Integer kilometrajeSalida) { this.kilometrajeSalida = kilometrajeSalida; }
    public LocalDate getFechaPrometida() { return fechaPrometida; }
    public void setFechaPrometida(LocalDate fechaPrometida) { this.fechaPrometida = fechaPrometida; }
    public String getUsuarioCreoId() { return usuarioCreoId; }
    public void setUsuarioCreoId(String usuarioCreoId) { this.usuarioCreoId = usuarioCreoId; }
    public List<CreateOtInsumoRequest> getInsumos() { return insumos; }
    public void setInsumos(List<CreateOtInsumoRequest> insumos) { this.insumos = insumos; }
    public List<CreateOtManoObraRequest> getManosObra() { return manosObra; }
    public void setManosObra(List<CreateOtManoObraRequest> manosObra) { this.manosObra = manosObra; }

    public static class CreateOtInsumoRequest {
        private String productoId;
        private BigDecimal cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;

        public String getProductoId() { return productoId; }
        public void setProductoId(String productoId) { this.productoId = productoId; }
        public BigDecimal getCantidad() { return cantidad; }
        public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    }

    public static class CreateOtManoObraRequest {
        private String tecnicoId;
        private String servicioDescripcion;
        private BigDecimal horas;
        private BigDecimal tarifaHora;
        private BigDecimal subtotal;

        public String getTecnicoId() { return tecnicoId; }
        public void setTecnicoId(String tecnicoId) { this.tecnicoId = tecnicoId; }
        public String getServicioDescripcion() { return servicioDescripcion; }
        public void setServicioDescripcion(String servicioDescripcion) { this.servicioDescripcion = servicioDescripcion; }
        public BigDecimal getHoras() { return horas; }
        public void setHoras(BigDecimal horas) { this.horas = horas; }
        public BigDecimal getTarifaHora() { return tarifaHora; }
        public void setTarifaHora(BigDecimal tarifaHora) { this.tarifaHora = tarifaHora; }
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    }
}
