package com.lunorion.labs.core.vehiculo.domain.ports.in;

import com.lunorion.labs.core.vehiculo.application.dto.in.CreateVehiculoRequest;
import com.lunorion.labs.core.vehiculo.application.dto.out.VehiculoResponse;

public interface IVehiculoCommandPort {
    VehiculoResponse create(CreateVehiculoRequest request);
    VehiculoResponse update(String id, CreateVehiculoRequest request);
    void desactivar(String id);
    void asignarCliente(String id, String clienteId);
}
