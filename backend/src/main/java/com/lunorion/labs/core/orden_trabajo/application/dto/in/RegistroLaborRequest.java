package com.lunorion.labs.core.orden_trabajo.application.dto.in;

import java.math.BigDecimal;

public class RegistroLaborRequest {
    private String tecnicoId;
    private String servicioDescripcion;
    private BigDecimal horas;
    private BigDecimal tarifaHora;

    public String getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(String tecnicoId) { this.tecnicoId = tecnicoId; }
    public String getServicioDescripcion() { return servicioDescripcion; }
    public void setServicioDescripcion(String servicioDescripcion) { this.servicioDescripcion = servicioDescripcion; }
    public BigDecimal getHoras() { return horas; }
    public void setHoras(BigDecimal horas) { this.horas = horas; }
    public BigDecimal getTarifaHora() { return tarifaHora; }
    public void setTarifaHora(BigDecimal tarifaHora) { this.tarifaHora = tarifaHora; }
}
