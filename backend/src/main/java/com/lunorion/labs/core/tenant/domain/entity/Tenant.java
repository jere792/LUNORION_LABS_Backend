package com.lunorion.labs.core.tenant.domain.entity;

import com.lunorion.labs.shared.domain.BaseEntity;

import java.time.LocalDate;
import java.util.UUID;

public class Tenant extends BaseEntity {
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String domicilioFiscal;
    private String email;
    private String telefono;
    private String regimenTributario;
    private String logoUrl;
    private String colorPrimario;
    private String colorSecundario;
    private String plan;
    private String estado;
    private byte[] certificadoP12;
    private String certificadoPassword;
    private LocalDate certificadoValidez;
    private Boolean rucValidadoSunat;

    public Tenant() {}

    public Tenant(UUID id, String ruc, String razonSocial) {
        super(id);
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.estado = "ACTIVO";
    }

    public static Tenant create(String ruc, String razonSocial, String nombreComercial, String domicilioFiscal, String email, String telefono) {
        Tenant tenant = new Tenant(UUID.randomUUID(), ruc, razonSocial);
        tenant.nombreComercial = nombreComercial;
        tenant.domicilioFiscal = domicilioFiscal;
        tenant.email = email;
        tenant.telefono = telefono;
        return tenant;
    }

    public void validarRucSunat() {
        this.rucValidadoSunat = true;
        markUpdated();
    }

    public void actualizarDatos(String razonSocial, String nombreComercial, String domicilioFiscal, String email, String telefono) {
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.domicilioFiscal = domicilioFiscal;
        this.email = email;
        this.telefono = telefono;
        markUpdated();
    }

    public String getRuc() { return ruc; }
    public String getRazonSocial() { return razonSocial; }
    public String getNombreComercial() { return nombreComercial; }
    public String getDomicilioFiscal() { return domicilioFiscal; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getRegimenTributario() { return regimenTributario; }
    public String getLogoUrl() { return logoUrl; }
    public String getColorPrimario() { return colorPrimario; }
    public String getColorSecundario() { return colorSecundario; }
    public String getPlan() { return plan; }
    public String getEstado() { return estado; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }
    public void setDomicilioFiscal(String domicilioFiscal) { this.domicilioFiscal = domicilioFiscal; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setRegimenTributario(String regimenTributario) { this.regimenTributario = regimenTributario; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public void setColorPrimario(String colorPrimario) { this.colorPrimario = colorPrimario; }
    public void setColorSecundario(String colorSecundario) { this.colorSecundario = colorSecundario; }
    public void setPlan(String plan) { this.plan = plan; }
    public void setEstado(String estado) { this.estado = estado; }
    public byte[] getCertificadoP12() { return certificadoP12; }
    public String getCertificadoPassword() { return certificadoPassword; }
    public LocalDate getCertificadoValidez() { return certificadoValidez; }
    public Boolean getRucValidadoSunat() { return rucValidadoSunat; }
}
