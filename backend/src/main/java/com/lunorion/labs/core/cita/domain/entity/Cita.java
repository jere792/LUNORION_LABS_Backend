package com.lunorion.labs.core.cita.domain.entity;

import com.lunorion.labs.shared.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Cita extends BaseEntity {

    private String tenantId;
    private String clienteId;
    private String vehiculoId;
    private String tecnicoId;
    private String servicioDescripcion;
    private LocalDateTime fechaHora;
    private int duracionMinutos;
    private String estado;
    private boolean recordatorioEnviado;
    private boolean notificarWhatsapp;
    private String usuarioCreoId;

    public Cita() {}

    public Cita(UUID id, String tenantId, String clienteId, String vehiculoId,
                LocalDateTime fechaHora, int duracionMinutos, String usuarioCreoId) {
        super(id);
        this.tenantId = tenantId;
        this.clienteId = clienteId;
        this.vehiculoId = vehiculoId;
        this.fechaHora = fechaHora;
        this.duracionMinutos = duracionMinutos;
        this.usuarioCreoId = usuarioCreoId;
        this.estado = "PROGRAMADA";
        this.recordatorioEnviado = false;
    }

    public static Cita create(String tenantId, String clienteId, String vehiculoId, String tecnicoId,
                              String servicioDescripcion, LocalDateTime fechaHora, int duracionMinutos,
                              boolean notificarWhatsapp, String usuarioCreoId) {
        Cita cita = new Cita(UUID.randomUUID(), tenantId, clienteId, vehiculoId,
                fechaHora, duracionMinutos, usuarioCreoId);
        cita.tecnicoId = tecnicoId;
        cita.servicioDescripcion = servicioDescripcion;
        cita.notificarWhatsapp = notificarWhatsapp;
        return cita;
    }

    public void confirmar() {
        this.estado = "CONFIRMADA";
        markUpdated();
    }

    public void cancelar() {
        this.estado = "CANCELADA";
        markUpdated();
    }

    public void completar() {
        this.estado = "COMPLETADA";
        markUpdated();
    }

    public void marcarRecordatorioEnviado() {
        this.recordatorioEnviado = true;
        markUpdated();
    }

    public void reprogramar(LocalDateTime nuevaFechaHora) {
        this.fechaHora = nuevaFechaHora;
        this.estado = "REPROGRAMADA";
        markUpdated();
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        markUpdated();
    }

    public void setTecnicoId(String tecnicoId) { this.tecnicoId = tecnicoId; }
    public void setServicioDescripcion(String servicioDescripcion) { this.servicioDescripcion = servicioDescripcion; }
    public void setNotificarWhatsapp(boolean notificarWhatsapp) { this.notificarWhatsapp = notificarWhatsapp; }

    public String getTenantId() { return tenantId; }
    public String getClienteId() { return clienteId; }
    public String getVehiculoId() { return vehiculoId; }
    public String getTecnicoId() { return tecnicoId; }
    public String getServicioDescripcion() { return servicioDescripcion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public String getEstado() { return estado; }
    public boolean isRecordatorioEnviado() { return recordatorioEnviado; }
    public boolean isNotificarWhatsapp() { return notificarWhatsapp; }
    public String getUsuarioCreoId() { return usuarioCreoId; }
}
