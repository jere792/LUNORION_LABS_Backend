package com.lunorion.labs.core.usuario.domain.ports.in;

import com.lunorion.labs.core.usuario.application.dto.out.PermisoResponse;
import com.lunorion.labs.core.usuario.application.dto.out.UsuarioResponse;

import java.util.List;
import java.util.Optional;

public interface IUsuarioQueryPort {
    Optional<UsuarioResponse> findById(String id);
    Optional<UsuarioResponse> findByEmail(String email);
    List<UsuarioResponse> findByTenantId(String tenantId);
    List<PermisoResponse> listarPermisos(String tenantId);
}
