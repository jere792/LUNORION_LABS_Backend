package com.lunorion.labs.core.cliente.domain.entity;

import com.lunorion.labs.shared.domain.BaseEntity;
import java.util.UUID;

public class Cliente extends BaseEntity {
    private String tenantId;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String email;
    private boolean activo;

    public Cliente() {}
    public Cliente(UUID id, String tenantId, String tipoDocumento, String numeroDocumento, String nombres, String apellidos, String razonSocial) {
        super(id); this.tenantId = tenantId; this.tipoDocumento = tipoDocumento; this.numeroDocumento = numeroDocumento;
        this.nombres = nombres; this.apellidos = apellidos; this.razonSocial = razonSocial; this.activo = true;
    }
    public static Cliente create(String tenantId, String tipoDocumento, String numeroDocumento, String nombres, String apellidos, String razonSocial, String direccion, String telefono, String email) {
        Cliente c = new Cliente(UUID.randomUUID(), tenantId, tipoDocumento, numeroDocumento, nombres, apellidos, razonSocial);
        c.direccion = direccion; c.telefono = telefono; c.email = email; return c;
    }
    public String getTenantId() { return tenantId; }
    public String getTipoDocumento() { return tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getRazonSocial() { return razonSocial; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public boolean isActivo() { return activo; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void desactivar() { this.activo = false; markUpdated(); }
    public void actualizar() { markUpdated(); }
}
