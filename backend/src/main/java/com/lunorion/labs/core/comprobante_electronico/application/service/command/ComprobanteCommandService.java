package com.lunorion.labs.core.comprobante_electronico.application.service.command;

import com.lunorion.labs.core.comprobante_electronico.application.dto.in.*;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ComprobanteResponse;
import com.lunorion.labs.core.comprobante_electronico.application.dto.out.ResumenDiarioResponse;
import com.lunorion.labs.core.comprobante_electronico.application.mapper.ComprobanteMapper;
import com.lunorion.labs.core.comprobante_electronico.application.mapper.ResumenDiarioMapper;
import com.lunorion.labs.core.comprobante_electronico.domain.entity.ComprobanteElectronico;
import com.lunorion.labs.core.comprobante_electronico.domain.entity.ResumenDiario;
import com.lunorion.labs.core.comprobante_electronico.domain.ports.in.IComprobanteCommandPort;
import com.lunorion.labs.core.comprobante_electronico.domain.ports.out.IComprobanteRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComprobanteCommandService implements IComprobanteCommandPort {

    private final IComprobanteRepositoryPort repository;
    private final ComprobanteMapper mapper;
    private final ResumenDiarioMapper resumenDiarioMapper;

    public ComprobanteCommandService(IComprobanteRepositoryPort repository, ComprobanteMapper mapper,
                                     ResumenDiarioMapper resumenDiarioMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.resumenDiarioMapper = resumenDiarioMapper;
    }

    @Override
    public ComprobanteResponse create(CreateComprobanteRequest request) {
        ComprobanteElectronico domain = mapper.toDomain(request);
        ComprobanteElectronico saved = repository.save(domain);
        return mapper.toResponse(saved);
    }

    @Override
    public ComprobanteResponse emitirBoleta(EmitirBoletaRequest request) {
        ComprobanteElectronico domain = ComprobanteElectronico.create(
                request.getTenantId(),
                request.getVentaId(),
                "03",
                request.getSerie(),
                request.getNumero(),
                java.time.LocalDate.parse(request.getFechaEmision()),
                java.time.LocalTime.parse(request.getHoraEmision()),
                request.getMontoTotal(),
                request.getRucCliente(),
                request.getRazonSocialCliente(),
                request.getEnviadoPorId()
        );
        ComprobanteElectronico saved = repository.save(domain);
        return mapper.toResponse(saved);
    }

    @Override
    public ComprobanteResponse emitirNotaCredito(NotaCreditoRequest request) {
        ComprobanteElectronico domain = ComprobanteElectronico.create(
                request.getTenantId(),
                request.getVentaId(),
                "07",
                request.getSerie(),
                request.getNumero(),
                java.time.LocalDate.parse(request.getFechaEmision()),
                java.time.LocalTime.parse(request.getHoraEmision()),
                request.getMontoTotal(),
                request.getRucCliente(),
                request.getRazonSocialCliente(),
                request.getEnviadoPorId()
        );
        domain.setComprobanteReferenciaId(request.getComprobanteReferenciaId());
        ComprobanteElectronico saved = repository.save(domain);
        return mapper.toResponse(saved);
    }

    @Override
    public ComprobanteResponse emitirNotaDebito(NotaDebitoRequest request) {
        ComprobanteElectronico domain = ComprobanteElectronico.create(
                request.getTenantId(),
                request.getVentaId(),
                "08",
                request.getSerie(),
                request.getNumero(),
                java.time.LocalDate.parse(request.getFechaEmision()),
                java.time.LocalTime.parse(request.getHoraEmision()),
                request.getMontoTotal(),
                request.getRucCliente(),
                request.getRazonSocialCliente(),
                request.getEnviadoPorId()
        );
        domain.setComprobanteReferenciaId(request.getComprobanteReferenciaId());
        ComprobanteElectronico saved = repository.save(domain);
        return mapper.toResponse(saved);
    }

    @Override
    public void firmar(String id) {
        repository.findById(id).ifPresent(c -> {
            c.firmar();
            repository.save(c);
        });
    }

    @Override
    public void enviarSunat(String id) {
        repository.findById(id).ifPresent(c -> {
            c.enviarSunat();
            repository.save(c);
        });
    }

    @Override
    public void aceptar(String id) {
        repository.findById(id).ifPresent(c -> {
            c.aceptar();
            repository.save(c);
        });
    }

    @Override
    public void rechazar(String id, String error) {
        repository.findById(id).ifPresent(c -> {
            c.rechazar(error);
            repository.save(c);
        });
    }

    @Override
    public void reenviar(String id) {
        repository.findById(id).ifPresent(c -> {
            c.enviarSunat();
            c.setCodigoErrorSunat(null);
            c.setDescripcionError(null);
            repository.save(c);
        });
    }

    @Override
    public ResumenDiarioResponse generarResumenDiario(CreateResumenDiarioRequest request) {
        ResumenDiario domain = resumenDiarioMapper.toDomain(request);
        ResumenDiario saved = repository.saveResumenDiario(domain);
        return resumenDiarioMapper.toResponse(saved);
    }
}
