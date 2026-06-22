package com.lunorion.labs.core.checkin.domain.entity;

import com.lunorion.labs.shared.domain.BaseEntity;
import java.util.UUID;

public class Checkin extends BaseEntity {
    private String tenantId;
    private String clienteId;
    private String vehiculoId;
    private Integer kilometraje;
    private String nivelCombustible;
    private String observacionesCliente;
    private String firmaCliente;
    private String pdfActa;
    private String otId;
    private String usuarioId;

    public Checkin() {}

    public Checkin(UUID id, String tenantId, String clienteId, String vehiculoId,
                   Integer kilometraje, String nivelCombustible, String observacionesCliente,
                   String firmaCliente, String usuarioId) {
        super(id);
        this.tenantId = tenantId;
        this.clienteId = clienteId;
        this.vehiculoId = vehiculoId;
        this.kilometraje = kilometraje;
        this.nivelCombustible = nivelCombustible;
        this.observacionesCliente = observacionesCliente;
        this.firmaCliente = firmaCliente;
        this.usuarioId = usuarioId;
    }

    public static Checkin create(String tenantId, String clienteId, String vehiculoId,
                                  Integer kilometraje, String nivelCombustible,
                                  String observacionesCliente, String firmaCliente, String usuarioId) {
        return new Checkin(UUID.randomUUID(), tenantId, clienteId, vehiculoId,
                kilometraje, nivelCombustible, observacionesCliente, firmaCliente, usuarioId);
    }

    public void asignarOt(String otId) {
        this.otId = otId;
        markUpdated();
    }

    public void firmarActa(String firmaCliente) {
        this.firmaCliente = firmaCliente;
        markUpdated();
    }

    public void adjuntarActa(String pdfActa) {
        this.pdfActa = pdfActa;
        markUpdated();
    }

    public String getTenantId() { return tenantId; }
    public String getClienteId() { return clienteId; }
    public String getVehiculoId() { return vehiculoId; }
    public Integer getKilometraje() { return kilometraje; }
    public String getNivelCombustible() { return nivelCombustible; }
    public String getObservacionesCliente() { return observacionesCliente; }
    public String getFirmaCliente() { return firmaCliente; }
    public String getPdfActa() { return pdfActa; }
    public String getOtId() { return otId; }
    public String getUsuarioId() { return usuarioId; }
}
