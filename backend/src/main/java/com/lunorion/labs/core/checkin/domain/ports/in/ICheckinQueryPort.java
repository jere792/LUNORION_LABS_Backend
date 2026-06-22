package com.lunorion.labs.core.checkin.domain.ports.in;

import com.lunorion.labs.core.checkin.application.dto.out.CheckinResponse;

import java.util.List;
import java.util.Optional;

public interface ICheckinQueryPort {
    Optional<CheckinResponse> findById(String id);
    List<CheckinResponse> findByTenantId(String tenantId);
    Optional<CheckinResponse> findByOtId(String otId);
    byte[] descargarActa(String id);
}
