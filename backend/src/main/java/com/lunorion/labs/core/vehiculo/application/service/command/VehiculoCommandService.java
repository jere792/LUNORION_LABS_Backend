package com.lunorion.labs.core.vehiculo.application.service.command;

import com.lunorion.labs.core.vehiculo.application.dto.in.CreateVehiculoRequest;
import com.lunorion.labs.core.vehiculo.application.dto.out.VehiculoResponse;
import com.lunorion.labs.core.vehiculo.application.mapper.VehiculoMapper;
import com.lunorion.labs.core.vehiculo.domain.entity.Vehiculo;
import com.lunorion.labs.core.vehiculo.domain.ports.in.IVehiculoCommandPort;
import com.lunorion.labs.core.vehiculo.domain.ports.out.IVehiculoRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehiculoCommandService implements IVehiculoCommandPort {

    private final IVehiculoRepositoryPort repository;
    private final VehiculoMapper mapper;

    public VehiculoCommandService(IVehiculoRepositoryPort repository, VehiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public VehiculoResponse create(CreateVehiculoRequest request) {
        Vehiculo vehiculo = mapper.toDomain(request);
        Vehiculo saved = repository.save(vehiculo);
        return mapper.toResponse(saved);
    }

    @Override
    public VehiculoResponse update(String id, CreateVehiculoRequest request) {
        Vehiculo vehiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehiculo not found with id: " + id));
        vehiculo.actualizar(
            request.getPlaca(),
            request.getMarca(),
            request.getModelo(),
            request.getAnio(),
            request.getColor(),
            request.getNumeroVin(),
            request.getClienteId()
        );
        Vehiculo saved = repository.save(vehiculo);
        return mapper.toResponse(saved);
    }

    @Override
    public void desactivar(String id) {
        repository.findById(id).ifPresent(vehiculo -> {
            vehiculo.desactivar();
            repository.save(vehiculo);
        });
    }

    @Override
    public void asignarCliente(String id, String clienteId) {
        repository.findById(id).ifPresent(vehiculo -> {
            vehiculo.asignarCliente(clienteId);
            repository.save(vehiculo);
        });
    }
}
