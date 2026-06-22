package com.lunorion.labs.core.planilla.application.service.query;

import com.lunorion.labs.core.planilla.application.dto.out.AsistenciaResponse;
import com.lunorion.labs.core.planilla.application.dto.out.BoletaPagoResponse;
import com.lunorion.labs.core.planilla.application.dto.out.ComisionConfigResponse;
import com.lunorion.labs.core.planilla.application.dto.out.ProductividadResponse;
import com.lunorion.labs.core.planilla.application.mapper.PlanillaMapper;
import com.lunorion.labs.core.planilla.domain.ports.in.IPlanillaQueryPort;
import com.lunorion.labs.core.planilla.domain.ports.out.IAsistenciaRepositoryPort;
import com.lunorion.labs.core.planilla.domain.ports.out.IBoletaPagoRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PlanillaQueryService implements IPlanillaQueryPort {

    private final IAsistenciaRepositoryPort asistenciaRepository;
    private final IBoletaPagoRepositoryPort boletaRepository;
    private final PlanillaMapper mapper;

    public PlanillaQueryService(IAsistenciaRepositoryPort asistenciaRepository,
                                 IBoletaPagoRepositoryPort boletaRepository,
                                 PlanillaMapper mapper) {
        this.asistenciaRepository = asistenciaRepository;
        this.boletaRepository = boletaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AsistenciaResponse> consultarAsistencia(String tecnicoId, LocalDate desde, LocalDate hasta) {
        return asistenciaRepository.findByTecnicoIdAndFechaBetween(tecnicoId, desde, hasta).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BoletaPagoResponse> consultarBoleta(String tecnicoId, String periodo) {
        return boletaRepository.findByTecnicoIdAndPeriodo(tecnicoId, periodo)
                .map(mapper::toResponse);
    }

    @Override
    public List<BoletaPagoResponse> consultarBoletasByPeriodo(String tenantId, String periodo) {
        return boletaRepository.findByTenantIdAndPeriodo(tenantId, periodo).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductividadResponse productividad(String tenantId, LocalDate desde, LocalDate hasta) {
        ProductividadResponse r = new ProductividadResponse();
        r.setTenantId(tenantId);
        r.setTecnicos(Collections.emptyList());
        return r;
    }

    @Override
    public List<ComisionConfigResponse> getComisionesConfig(String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public byte[] descargarPdf(String id) {
        return new byte[0];
    }
}
