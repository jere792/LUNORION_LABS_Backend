package com.lunorion.labs.core.usuario.domain.ports.in;

import com.lunorion.labs.core.usuario.application.dto.in.CreateUsuarioRequest;
import com.lunorion.labs.core.usuario.application.dto.in.AsignarPermisosRequest;
import com.lunorion.labs.core.usuario.application.dto.out.UsuarioResponse;

public interface IUsuarioCommandPort {
    UsuarioResponse create(CreateUsuarioRequest request);
    void desactivar(String id);
    void asignarPermisos(String usuarioId, AsignarPermisosRequest request);
}
