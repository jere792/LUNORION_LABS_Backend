package com.lunorion.labs.core.checkin.application.service.query;

import com.lunorion.labs.core.checkin.application.dto.out.CheckinResponse;
import com.lunorion.labs.core.checkin.application.mapper.CheckinMapper;
import com.lunorion.labs.core.checkin.domain.ports.in.ICheckinQueryPort;
import com.lunorion.labs.core.checkin.domain.ports.out.ICheckinRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CheckinQueryService implements ICheckinQueryPort {

    private final ICheckinRepositoryPort repository;
    private final CheckinMapper mapper;

    public CheckinQueryService(ICheckinRepositoryPort repository, CheckinMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CheckinResponse> findById(String id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Override
    public List<CheckinResponse> findByTenantId(String tenantId) {
        return repository.findByTenantId(tenantId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CheckinResponse> findByOtId(String otId) {
        return repository.findByOtId(otId).map(mapper::toResponse);
    }

    @Override
    public byte[] descargarActa(String id) {
        return repository.findById(id)
                .map(c -> c.getPdfActa() != null ? c.getPdfActa().getBytes() : new byte[0])
                .orElse(new byte[0]);
    }
}
