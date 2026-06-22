package com.lunorion.labs.core.dashboard.application.dto.in;

public class ExportarReporteRequest {

    private String formato;
    private String fechaInicio;
    private String fechaFin;
    private String tipo;

    public ExportarReporteRequest() {
    }

    public ExportarReporteRequest(String formato, String fechaInicio, String fechaFin, String tipo) {
        this.formato = formato;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
