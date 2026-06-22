package com.lunorion.labs.core.cita.application.dto.in;

public class ReprogramarCitaRequest {
    private String fechaHora;
    private String motivo;

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
