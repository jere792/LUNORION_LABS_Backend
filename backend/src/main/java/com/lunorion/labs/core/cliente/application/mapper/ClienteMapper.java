package com.lunorion.labs.core.cliente.application.mapper;

import com.lunorion.labs.core.cliente.application.dto.in.CreateClienteRequest;
import com.lunorion.labs.core.cliente.application.dto.out.ClienteResponse;
import com.lunorion.labs.core.cliente.domain.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toDomain(CreateClienteRequest request) {
        return Cliente.create(
            request.getTenantId(),
            request.getTipoDocumento(),
            request.getNumeroDocumento(),
            request.getNombres(),
            request.getApellidos(),
            request.getRazonSocial(),
            request.getDireccion(),
            request.getTelefono(),
            request.getEmail()
        );
    }

    public ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId().toString());
        response.setTipoDocumento(cliente.getTipoDocumento());
        response.setNumeroDocumento(cliente.getNumeroDocumento());
        response.setNombres(cliente.getNombres());
        response.setApellidos(cliente.getApellidos());
        response.setRazonSocial(cliente.getRazonSocial());
        response.setDireccion(cliente.getDireccion());
        response.setTelefono(cliente.getTelefono());
        response.setEmail(cliente.getEmail());
        response.setActivo(cliente.isActivo());
        return response;
    }

    public void updateDomain(Cliente cliente, CreateClienteRequest request) {
        if (request.getTipoDocumento() != null) cliente.setTipoDocumento(request.getTipoDocumento());
        if (request.getNumeroDocumento() != null) cliente.setNumeroDocumento(request.getNumeroDocumento());
        if (request.getNombres() != null) cliente.setNombres(request.getNombres());
        if (request.getApellidos() != null) cliente.setApellidos(request.getApellidos());
        if (request.getRazonSocial() != null) cliente.setRazonSocial(request.getRazonSocial());
        if (request.getDireccion() != null) cliente.setDireccion(request.getDireccion());
        if (request.getTelefono() != null) cliente.setTelefono(request.getTelefono());
        if (request.getEmail() != null) cliente.setEmail(request.getEmail());
        cliente.actualizar();
    }
}
