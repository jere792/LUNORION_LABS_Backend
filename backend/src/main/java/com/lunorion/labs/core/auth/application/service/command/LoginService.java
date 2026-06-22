package com.lunorion.labs.core.auth.application.service.command;

import com.lunorion.labs.core.auth.application.dto.in.LoginRequest;
import com.lunorion.labs.core.auth.application.dto.out.LoginResponse;
import com.lunorion.labs.core.auth.domain.ports.in.ILoginPort;
import com.lunorion.labs.core.auth.domain.ports.out.IUsuarioAuthPort;
import com.lunorion.labs.core.tenant.domain.ports.out.ITenantRepositoryPort;
import com.lunorion.labs.shared.infrastructure.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginPort {

    private final IUsuarioAuthPort usuarioAuthPort;
    private final JwtTokenProvider tokenProvider;
    private final ITenantRepositoryPort tenantRepo;

    public LoginService(IUsuarioAuthPort usuarioAuthPort, JwtTokenProvider tokenProvider,
                        ITenantRepositoryPort tenantRepo) {
        this.usuarioAuthPort = usuarioAuthPort;
        this.tokenProvider = tokenProvider;
        this.tenantRepo = tenantRepo;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        var userOpt = usuarioAuthPort.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Credenciales invalidas");
        }
        var user = userOpt.get();
        if (!user.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }
        if (!usuarioAuthPort.validarPassword(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Credenciales invalidas");
        }

        String token = tokenProvider.generateToken(user.getEmail(), user.getTenantId(),
                user.getRol(), user.getPermisos());

        var usuarioInfo = new LoginResponse.UsuarioInfo(
                user.getId(), user.getNombres(), user.getApellidos(),
                user.getEmail(), user.getRol(), user.getPermisos()
        );

        LoginResponse.TenantInfo tenantInfo = buildTenantInfo(user.getTenantId());

        return new LoginResponse(token, tokenProvider.getExpiration(), usuarioInfo, tenantInfo);
    }

    @Override
    public LoginResponse refresh(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw new RuntimeException("Token invalido o expirado");
        }
        String email = tokenProvider.getEmailFromToken(token);
        var userOpt = usuarioAuthPort.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        var user = userOpt.get();
        if (!user.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }
        String newToken = tokenProvider.generateToken(user.getEmail(), user.getTenantId(),
                user.getRol(), user.getPermisos());

        var usuarioInfo = new LoginResponse.UsuarioInfo(
                user.getId(), user.getNombres(), user.getApellidos(),
                user.getEmail(), user.getRol(), user.getPermisos()
        );

        LoginResponse.TenantInfo tenantInfo = buildTenantInfo(user.getTenantId());
        return new LoginResponse(newToken, tokenProvider.getExpiration(), usuarioInfo, tenantInfo);
    }

    private LoginResponse.TenantInfo buildTenantInfo(String tenantId) {
        return tenantRepo.findById(tenantId)
                .map(t -> new LoginResponse.TenantInfo(
                        t.getId().toString(),
                        t.getRuc(),
                        t.getRazonSocial(),
                        t.getNombreComercial(),
                        t.getLogoUrl(),
                        t.getColorPrimario(),
                        t.getColorSecundario()
                ))
                .orElse(null);
    }
}
