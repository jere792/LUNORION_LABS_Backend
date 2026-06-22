package com.lunorion.labs.core.checkin.application.service.command;

import com.lunorion.labs.core.checkin.application.dto.in.CreateCheckinRequest;
import com.lunorion.labs.core.checkin.application.dto.out.CheckinResponse;
import com.lunorion.labs.core.checkin.application.mapper.CheckinMapper;
import com.lunorion.labs.core.checkin.domain.entity.Checkin;
import com.lunorion.labs.core.checkin.domain.entity.CheckinFoto;
import com.lunorion.labs.core.checkin.domain.ports.in.ICheckinCommandPort;
import com.lunorion.labs.core.checkin.domain.ports.out.ICheckinRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckinCommandService implements ICheckinCommandPort {

    private final ICheckinRepositoryPort repository;
    private final CheckinMapper mapper;

    public CheckinCommandService(ICheckinRepositoryPort repository, CheckinMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CheckinResponse create(CreateCheckinRequest request) {
        Checkin checkin = mapper.toDomain(request);
        Checkin saved = repository.save(checkin);
        return mapper.toResponse(saved);
    }

    @Override
    public void asignarOt(String checkinId, String otId) {
        repository.findById(checkinId).ifPresent(checkin -> {
            checkin.asignarOt(otId);
            repository.save(checkin);
        });
    }

    @Override
    public void uploadFoto(String checkinId, String fileName, byte[] fileContent) {
        repository.findById(checkinId).ifPresent(checkin -> {
            CheckinFoto foto = CheckinFoto.create(checkinId, null, null, fileName);
            repository.saveFoto(foto);
        });
    }

    @Override
    public void deleteFoto(String checkinId, String fotoId) {
        repository.findById(checkinId).ifPresent(checkin -> {
            repository.deleteFoto(fotoId);
        });
    }

    @Override
    public CheckinResponse firmarActa(String id, String nombreFirma) {
        Checkin checkin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checkin not found with id: " + id));
        checkin.firmarActa(nombreFirma);
        Checkin saved = repository.save(checkin);
        return mapper.toResponse(saved);
    }

    @Override
    public CheckinResponse convertirEnOt(String id) {
        Checkin checkin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checkin not found with id: " + id));
        String otId = "OT-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        checkin.asignarOt(otId);
        Checkin saved = repository.save(checkin);
        return mapper.toResponse(saved);
    }
}
