package com.lunorion.labs.core.checkin.domain.ports.in;

import com.lunorion.labs.core.checkin.application.dto.in.CreateCheckinRequest;
import com.lunorion.labs.core.checkin.application.dto.out.CheckinResponse;

public interface ICheckinCommandPort {
    CheckinResponse create(CreateCheckinRequest request);
    void asignarOt(String checkinId, String otId);
    void uploadFoto(String checkinId, String fileName, byte[] fileContent);
    void deleteFoto(String checkinId, String fotoId);
    CheckinResponse firmarActa(String id, String nombreFirma);
    CheckinResponse convertirEnOt(String id);
}
