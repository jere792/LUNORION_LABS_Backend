package com.lunorion.labs.core.cita.application.dto.in;

public class NotificacionesConfigRequest {
    private String tenantId;
    private boolean notificarWhatsapp;
    private boolean recordatorioAutomatico;
    private int anticipacionMinutos;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public boolean isNotificarWhatsapp() { return notificarWhatsapp; }
    public void setNotificarWhatsapp(boolean notificarWhatsapp) { this.notificarWhatsapp = notificarWhatsapp; }
    public boolean isRecordatorioAutomatico() { return recordatorioAutomatico; }
    public void setRecordatorioAutomatico(boolean recordatorioAutomatico) { this.recordatorioAutomatico = recordatorioAutomatico; }
    public int getAnticipacionMinutos() { return anticipacionMinutos; }
    public void setAnticipacionMinutos(int anticipacionMinutos) { this.anticipacionMinutos = anticipacionMinutos; }
}
