package com.lunorion.labs.core.comprobante_electronico.domain.ports.in;

import com.lunorion.labs.core.comprobante_electronico.application.dto.in.*;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ComprobanteResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ResumenDiarioResponse;

public interface IComprobanteCommandPort {
    ComprobanteResponse create(CreateComprobanteRequest request);
    ComprobanteResponse emitirBoleta(EmitirBoletaRequest request);
    ComprobanteResponse emitirNotaCredito(NotaCreditoRequest request);
    ComprobanteResponse emitirNotaDebito(NotaDebitoRequest request);
    void firmar(String id);
    void enviarSunat(String id);
    void aceptar(String id);
    void rechazar(String id, String error);
    void reenviar(String id);
    ResumenDiarioResponse generarResumenDiario(CreateResumenDiarioRequest request);
}
