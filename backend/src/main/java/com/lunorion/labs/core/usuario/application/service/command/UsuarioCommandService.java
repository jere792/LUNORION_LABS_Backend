package com.lunorion.labs.core.usuario.application.service.command;

import com.lunorion.labs.core.usuario.application.dto.in.AsignarPermisosRequest;
import com.lunorion.labs.core.usuario.application.dto.in.CreateUsuarioRequest;
import com.lunorion.labs.core.usuario.application.dto.out.UsuarioResponse;
import com.lunorion.labs.core.usuario.application.mapper.UsuarioMapper;
import com.lunorion.labs.core.usuario.domain.entity.Permiso;
import com.lunorion.labs.core.usuario.domain.entity.Usuario;
import com.lunorion.labs.core.usuario.domain.ports.in.IUsuarioCommandPort;
import com.lunorion.labs.core.usuario.domain.ports.out.IPermisoRepositoryPort;
import com.lunorion.labs.core.usuario.domain.ports.out.IUsuarioRepositoryPort;
import com.lunorion.labs.core.usuario.infrastructure.adapters.out.persistence.entity.UsuarioPermisoEntity;
import com.lunorion.labs.core.usuario.infrastructure.adapters.out.persistence.repository.UsuarioPermisoJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UsuarioCommandService implements IUsuarioCommandPort {

    private final IUsuarioRepositoryPort repository;
    private final IPermisoRepositoryPort permisoRepository;
    private final UsuarioPermisoJpaRepository usuarioPermisoJpaRepository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioCommandService(IUsuarioRepositoryPort repository,
                                 IPermisoRepositoryPort permisoRepository,
                                 UsuarioPermisoJpaRepository usuarioPermisoJpaRepository,
                                 UsuarioMapper mapper,
                                 PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.permisoRepository = permisoRepository;
        this.usuarioPermisoJpaRepository = usuarioPermisoJpaRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponse create(CreateUsuarioRequest request) {
        Usuario usuario = mapper.toDomain(request);
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        Usuario saved = repository.save(usuario);
        return mapper.toResponse(saved);
    }

    @Override
    public void desactivar(String id) {
        repository.findById(id).ifPresent(usuario -> {
            usuario.desactivar();
            repository.save(usuario);
        });
    }

    @Override
    public void asignarPermisos(String usuarioId, AsignarPermisosRequest request) {
        UUID uid = UUID.fromString(usuarioId);
        usuarioPermisoJpaRepository.deleteByUsuarioId(uid);
        if (request.getPermisos() != null) {
            List<Permiso> permisos = permisoRepository.findAllByCodigoIn(request.getPermisos());
            permisos.forEach(p -> {
                UsuarioPermisoEntity up = new UsuarioPermisoEntity();
                up.setUsuarioId(uid);
                up.setPermisoId(p.getId());
                usuarioPermisoJpaRepository.save(up);
            });
        }
    }
}
