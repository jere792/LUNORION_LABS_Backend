package com.lunorion.labs.core.vehiculo.application.mapper;

import com.lunorion.labs.core.vehiculo.application.dto.in.CreateVehiculoRequest;
import com.lunorion.labs.core.vehiculo.application.dto.out.VehiculoResponse;
import com.lunorion.labs.core.vehiculo.domain.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    public Vehiculo toDomain(CreateVehiculoRequest request) {
        return Vehiculo.create(
            request.getTenantId(),
            request.getPlaca(),
            request.getMarca(),
            request.getModelo(),
            request.getAnio(),
            request.getColor(),
            request.getNumeroVin(),
            request.getClienteId()
        );
    }

    public VehiculoResponse toResponse(Vehiculo vehiculo) {
        VehiculoResponse response = new VehiculoResponse();
        response.setId(vehiculo.getId().toString());
        response.setTenantId(vehiculo.getTenantId());
        response.setPlaca(vehiculo.getPlaca());
        response.setMarca(vehiculo.getMarca());
        response.setModelo(vehiculo.getModelo());
        response.setAnio(vehiculo.getAnio());
        response.setColor(vehiculo.getColor());
        response.setNumeroVin(vehiculo.getNumeroVin());
        response.setClienteId(vehiculo.getClienteId());
        response.setActivo(vehiculo.isActivo());
        return response;
    }
}
