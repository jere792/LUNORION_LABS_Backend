package com.lunorion.labs.core.auth.infrastructure.adapters.in.http;

import com.lunorion.labs.core.auth.application.dto.in.LoginRequest;
import com.lunorion.labs.core.auth.application.dto.out.LoginResponse;
import com.lunorion.labs.core.auth.domain.ports.in.ILoginPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ILoginPort loginPort;

    public AuthController(ILoginPort loginPort) {
        this.loginPort = loginPort;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginPort.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String token = authHeader.substring(7);
        return ResponseEntity.ok(loginPort.refresh(token));
    }
}
