package com.lunorion.labs.core.cliente.domain.ports.in;

import com.lunorion.labs.core.cliente.application.dto.in.CreateClienteRequest;
import com.lunorion.labs.core.cliente.application.dto.out.ClienteResponse;

public interface IClienteCommandPort {
    ClienteResponse create(CreateClienteRequest request);
    ClienteResponse update(String id, CreateClienteRequest request);
    void desactivar(String id);
}
