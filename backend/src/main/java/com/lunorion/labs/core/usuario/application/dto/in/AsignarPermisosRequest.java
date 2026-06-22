package com.lunorion.labs.core.usuario.application.dto.in;

import java.util.List;

public class AsignarPermisosRequest {
    private List<String> permisos;

    public List<String> getPermisos() { return permisos; }
    public void setPermisos(List<String> permisos) { this.permisos = permisos; }
}
