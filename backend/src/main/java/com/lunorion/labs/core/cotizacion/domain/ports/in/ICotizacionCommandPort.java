package com.lunorion.labs.core.cotizacion.domain.ports.in;

import com.lunorion.labs.core.cotizacion.application.dto.in.CreateCotizacionRequest;
import com.lunorion.labs.core.cotizacion.application.dto.out.CotizacionResponse;

public interface ICotizacionCommandPort {
    CotizacionResponse create(CreateCotizacionRequest request);
    CotizacionResponse update(String id, CreateCotizacionRequest request);
    void enviar(String id);
    void aprobar(String id);
    void rechazar(String id);
    void convertirEnOt(String id);
}
