package com.lunorion.labs.core.auth.domain.ports.in;

import com.lunorion.labs.core.auth.application.dto.in.LoginRequest;
import com.lunorion.labs.core.auth.application.dto.out.LoginResponse;

public interface ILoginPort {
    LoginResponse login(LoginRequest request);
    LoginResponse refresh(String token);
}
