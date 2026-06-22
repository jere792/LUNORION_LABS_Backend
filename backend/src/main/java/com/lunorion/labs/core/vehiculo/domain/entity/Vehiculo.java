package com.lunorion.labs.core.vehiculo.domain.entity;

import com.lunorion.labs.shared.domain.BaseEntity;
import java.util.UUID;

public class Vehiculo extends BaseEntity {
    private String tenantId;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String numeroVin;
    private String clienteId;
    private boolean activo;

    public Vehiculo() {}

    public Vehiculo(UUID id, String tenantId, String placa, String marca, String modelo, Integer anio, String color, String numeroVin, String clienteId, boolean activo) {
        super(id);
        this.tenantId = tenantId;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.numeroVin = numeroVin;
        this.clienteId = clienteId;
        this.activo = activo;
    }

    public static Vehiculo create(String tenantId, String placa, String marca, String modelo, Integer anio, String color, String numeroVin, String clienteId) {
        return new Vehiculo(UUID.randomUUID(), tenantId, placa, marca, modelo, anio, color, numeroVin, clienteId, true);
    }

    public String getTenantId() { return tenantId; }
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public Integer getAnio() { return anio; }
    public String getColor() { return color; }
    public String getNumeroVin() { return numeroVin; }
    public String getClienteId() { return clienteId; }
    public boolean isActivo() { return activo; }

    public void actualizar(String placa, String marca, String modelo, Integer anio, String color, String numeroVin, String clienteId) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.numeroVin = numeroVin;
        this.clienteId = clienteId;
        markUpdated();
    }

    public void asignarCliente(String clienteId) { this.clienteId = clienteId; markUpdated(); }
    public void desactivar() { this.activo = false; markUpdated(); }
}
