package com.lunorion.labs.core.usuario.application.service.query;

import com.lunorion.labs.core.usuario.application.dto.out.PermisoResponse;
import com.lunorion.labs.core.usuario.application.dto.out.UsuarioResponse;
import com.lunorion.labs.core.usuario.application.mapper.UsuarioMapper;
import com.lunorion.labs.core.usuario.domain.ports.in.IUsuarioQueryPort;
import com.lunorion.labs.core.usuario.domain.ports.out.IPermisoRepositoryPort;
import com.lunorion.labs.core.usuario.domain.ports.out.IUsuarioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UsuarioQueryService implements IUsuarioQueryPort {

    private final IUsuarioRepositoryPort repository;
    private final IPermisoRepositoryPort permisoRepository;
    private final UsuarioMapper mapper;

    public UsuarioQueryService(IUsuarioRepositoryPort repository,
                               IPermisoRepositoryPort permisoRepository,
                               UsuarioMapper mapper) {
        this.repository = repository;
        this.permisoRepository = permisoRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<UsuarioResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public Optional<UsuarioResponse> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toResponse);
    }

    @Override
    public List<UsuarioResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermisoResponse> listarPermisos(String tenantId) {
        return permisoRepository.findAll().stream()
                .map(p -> {
                    PermisoResponse r = new PermisoResponse();
                    r.setId(p.getId().toString());
                    r.setCodigo(p.getCodigo());
                    r.setNombre(p.getNombre());
                    r.setModulo(p.getModulo());
                    return r;
                })
                .collect(Collectors.toList());
    }
}
