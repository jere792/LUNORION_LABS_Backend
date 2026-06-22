package com.lunorion.labs.core.orden_trabajo.domain.entity;

import com.lunorion.labs.shared.domain.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class OrdenTrabajo extends BaseEntity {
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
    private LocalDate fechaCierre;
    private BigDecimal totalRepuestos;
    private BigDecimal totalManoObra;
    private BigDecimal total;
    private String otOrigenId;
    private String usuarioCreoId;
    private String usuarioCerroId;

    public OrdenTrabajo() {}

    public OrdenTrabajo(UUID id, String tenantId, String clienteId, String vehiculoId, String tecnicoId,
                        String numeroOt, String estado, String motivoIngreso, String usuarioCreoId) {
        super(id);
        this.tenantId = tenantId;
        this.clienteId = clienteId;
        this.vehiculoId = vehiculoId;
        this.tecnicoId = tecnicoId;
        this.numeroOt = numeroOt;
        this.estado = estado;
        this.motivoIngreso = motivoIngreso;
        this.usuarioCreoId = usuarioCreoId;
        this.totalRepuestos = BigDecimal.ZERO;
        this.totalManoObra = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    public static OrdenTrabajo create(String tenantId, String clienteId, String vehiculoId, String tecnicoId,
                                       String numeroOt, String estado, String motivoIngreso, String usuarioCreoId) {
        return new OrdenTrabajo(UUID.randomUUID(), tenantId, clienteId, vehiculoId, tecnicoId,
                numeroOt, estado, motivoIngreso, usuarioCreoId);
    }

    public void actualizar(String clienteId, String vehiculoId, String tecnicoId, String motivoIngreso,
                           Integer kilometrajeIngreso, Integer kilometrajeSalida, LocalDate fechaPrometida) {
        this.clienteId = clienteId;
        this.vehiculoId = vehiculoId;
        this.tecnicoId = tecnicoId;
        this.motivoIngreso = motivoIngreso;
        this.kilometrajeIngreso = kilometrajeIngreso;
        this.kilometrajeSalida = kilometrajeSalida;
        this.fechaPrometida = fechaPrometida;
        markUpdated();
    }

    public void cerrar(String usuarioCerroId) {
        this.estado = "CERRADO";
        this.usuarioCerroId = usuarioCerroId;
        this.fechaCierre = LocalDate.now();
        markUpdated();
    }

    public void reabrir() {
        this.estado = "EN_PROCESO";
        this.fechaCierre = null;
        this.usuarioCerroId = null;
        markUpdated();
    }

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
    public LocalDate getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDate fechaCierre) { this.fechaCierre = fechaCierre; }
    public BigDecimal getTotalRepuestos() { return totalRepuestos; }
    public void setTotalRepuestos(BigDecimal totalRepuestos) { this.totalRepuestos = totalRepuestos; }
    public BigDecimal getTotalManoObra() { return totalManoObra; }
    public void setTotalManoObra(BigDecimal totalManoObra) { this.totalManoObra = totalManoObra; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getOtOrigenId() { return otOrigenId; }
    public void setOtOrigenId(String otOrigenId) { this.otOrigenId = otOrigenId; }
    public String getUsuarioCreoId() { return usuarioCreoId; }
    public void setUsuarioCreoId(String usuarioCreoId) { this.usuarioCreoId = usuarioCreoId; }
    public String getUsuarioCerroId() { return usuarioCerroId; }
    public void setUsuarioCerroId(String usuarioCerroId) { this.usuarioCerroId = usuarioCerroId; }
}
