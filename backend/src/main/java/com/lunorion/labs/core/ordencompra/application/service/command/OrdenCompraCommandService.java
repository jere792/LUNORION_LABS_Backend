package com.lunorion.labs.core.ordencompra.application.service.command;

import com.lunorion.labs.core.ordencompra.application.dto.in.CreateOrdenCompraRequest;
import com.lunorion.labs.core.ordencompra.application.dto.in.RecibirOrdenRequest;
import com.lunorion.labs.core.ordencompra.application.dto.out.OrdenCompraResponse;
import com.lunorion.labs.core.ordencompra.application.mapper.OrdenCompraMapper;
import com.lunorion.labs.core.ordencompra.domain.entity.OrdenCompra;
import com.lunorion.labs.core.ordencompra.domain.ports.in.IOrdenCompraCommandPort;
import com.lunorion.labs.core.ordencompra.domain.ports.out.IOrdenCompraItemRepositoryPort;
import com.lunorion.labs.core.ordencompra.domain.ports.out.IOrdenCompraRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdenCompraCommandService implements IOrdenCompraCommandPort {

    private final IOrdenCompraRepositoryPort repository;
    private final IOrdenCompraItemRepositoryPort itemRepository;
    private final OrdenCompraMapper mapper;

    public OrdenCompraCommandService(IOrdenCompraRepositoryPort repository,
                                     IOrdenCompraItemRepositoryPort itemRepository,
                                     OrdenCompraMapper mapper) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    @Override
    public OrdenCompraResponse create(CreateOrdenCompraRequest request) {
        OrdenCompra ordenCompra = mapper.toDomain(request);
        OrdenCompra saved = repository.save(ordenCompra);
        return mapper.toResponse(saved);
    }

    @Override
    public void aprobar(String id) {
        repository.findById(id).ifPresent(oc -> {
            oc.aprobar();
            repository.save(oc);
        });
    }

    @Override
    public void completar(String id) {
        repository.findById(id).ifPresent(oc -> {
            oc.completar();
            repository.save(oc);
        });
    }

    @Override
    public void anular(String id) {
        repository.findById(id).ifPresent(oc -> {
            oc.anular();
            repository.save(oc);
        });
    }

    @Override
    public OrdenCompraResponse recibir(String id, RecibirOrdenRequest request) {
        return repository.findById(id).map(oc -> {
            if (request.getItems() != null) {
                request.getItems().forEach(item -> {
                    itemRepository.findById(item.getItemId()).ifPresent(oci -> {
                        oci.setCantidadRecibida(item.getCantidadRecibida());
                        itemRepository.save(oci);
                    });
                });
            }
            oc.completar();
            OrdenCompra saved = repository.save(oc);
            return mapper.toResponse(saved);
        }).orElseThrow(() -> new RuntimeException("Orden de compra no encontrada: " + id));
    }
}
