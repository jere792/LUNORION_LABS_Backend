package com.lunorion.labs.core.caja.application.service;

import com.lunorion.labs.core.caja.application.dto.in.AbrirCajaRequest;
import com.lunorion.labs.core.caja.application.dto.in.CerrarCajaRequest;
import com.lunorion.labs.core.caja.application.dto.in.RegistrarMovimientoRequest;
import com.lunorion.labs.core.caja.application.dto.out.CierreCajaResponse;
import com.lunorion.labs.core.caja.application.dto.out.EstadoCajaResponse;
import com.lunorion.labs.core.caja.application.dto.out.MovimientoCajaResponse;
import com.lunorion.labs.core.caja.application.mapper.CajaMapper;
import com.lunorion.labs.core.caja.domain.entity.CierreCaja;
import com.lunorion.labs.core.caja.domain.entity.MovimientoCaja;
import com.lunorion.labs.core.caja.infrastructure.adapters.out.persistence.entity.CierreCajaEntity;
import com.lunorion.labs.core.caja.infrastructure.adapters.out.persistence.entity.MovimientoCajaEntity;
import com.lunorion.labs.core.caja.infrastructure.adapters.out.persistence.mapper.CierreCajaEntityMapper;
import com.lunorion.labs.core.caja.infrastructure.adapters.out.persistence.mapper.MovimientoCajaEntityMapper;
import com.lunorion.labs.core.caja.infrastructure.adapters.out.persistence.repository.CierreCajaJpaRepository;
import com.lunorion.labs.core.caja.infrastructure.adapters.out.persistence.repository.MovimientoCajaJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CajaService {

    private final CierreCajaJpaRepository cierreRepo;
    private final MovimientoCajaJpaRepository movRepo;
    private final CajaMapper mapper;
    private final CierreCajaEntityMapper cierreEntityMapper;
    private final MovimientoCajaEntityMapper movEntityMapper;

    public CajaService(CierreCajaJpaRepository cierreRepo, MovimientoCajaJpaRepository movRepo,
                       CajaMapper mapper, CierreCajaEntityMapper cierreEntityMapper,
                       MovimientoCajaEntityMapper movEntityMapper) {
        this.cierreRepo = cierreRepo;
        this.movRepo = movRepo;
        this.mapper = mapper;
        this.cierreEntityMapper = cierreEntityMapper;
        this.movEntityMapper = movEntityMapper;
    }

    public CierreCajaResponse abrir(AbrirCajaRequest request) {
        CierreCaja domain = mapper.toDomain(request);
        CierreCajaEntity entity = cierreEntityMapper.toEntity(domain);
        entity = cierreRepo.save(entity);
        domain = cierreEntityMapper.toDomain(entity);
        return mapper.toResponse(domain);
    }

    public CierreCajaResponse cerrar(String id, CerrarCajaRequest request) {
        return cierreRepo.findById(UUID.fromString(id)).map(entity -> {
            CierreCaja domain = cierreEntityMapper.toDomain(entity);
            domain.cerrar(
                LocalTime.parse(request.getHoraCierre()),
                request.getSaldoReal(),
                request.getObservacion(),
                request.getUsuarioCierreId()
            );
            entity = cierreEntityMapper.toEntity(domain);
            entity = cierreRepo.save(entity);
            domain = cierreEntityMapper.toDomain(entity);
            return mapper.toResponse(domain);
        }).orElse(null);
    }

    public MovimientoCajaResponse registrarMovimiento(RegistrarMovimientoRequest request) {
        MovimientoCaja domain = mapper.toDomain(request);
        domain.setTenantId(obtenerTenantId(request.getCierreCajaId()));
        MovimientoCajaEntity entity = movEntityMapper.toEntity(domain);
        entity = movRepo.save(entity);
        domain = movEntityMapper.toDomain(entity);
        actualizarTotales(request);
        return mapper.toResponse(domain);
    }

    private String obtenerTenantId(String cierreCajaId) {
        return cierreRepo.findById(UUID.fromString(cierreCajaId))
                .map(e -> e.getTenantId().toString())
                .orElse(null);
    }

    private void actualizarTotales(RegistrarMovimientoRequest request) {
        cierreRepo.findById(UUID.fromString(request.getCierreCajaId())).ifPresent(c -> {
            if (request.getTipo().equals("INGRESO")) {
                c.setTotalIngresos(c.getTotalIngresos().add(request.getMonto()));
            } else {
                c.setTotalEgresos(c.getTotalEgresos().add(request.getMonto()));
            }
            cierreRepo.save(c);
        });
    }

    @Transactional(readOnly = true)
    public Optional<CierreCajaResponse> findCierreById(String id) {
        return cierreRepo.findById(UUID.fromString(id))
                .map(e -> mapper.toResponse(cierreEntityMapper.toDomain(e)));
    }

    @Transactional(readOnly = true)
    public Optional<CierreCajaResponse> findCierreAbierto(String tenantId) {
        return cierreRepo.findByTenantIdAndHoraCierreIsNull(UUID.fromString(tenantId))
                .map(e -> mapper.toResponse(cierreEntityMapper.toDomain(e)));
    }

    @Transactional(readOnly = true)
    public List<CierreCajaResponse> findCierresByTenant(String tenantId) {
        return cierreRepo.findByTenantId(UUID.fromString(tenantId)).stream()
                .map(e -> mapper.toResponse(cierreEntityMapper.toDomain(e)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovimientoCajaResponse> findMovimientosByCierre(String cierreCajaId) {
        return movRepo.findByCierreCajaId(UUID.fromString(cierreCajaId)).stream()
                .map(e -> mapper.toResponse(movEntityMapper.toDomain(e)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadoCajaResponse obtenerEstadoActual(String tenantId) {
        return cierreRepo.findByTenantIdAndHoraCierreIsNull(UUID.fromString(tenantId))
                .map(entity -> {
                    EstadoCajaResponse r = new EstadoCajaResponse();
                    r.setId(entity.getId().toString());
                    r.setTenantId(entity.getTenantId().toString());
                    r.setSaldoInicial(entity.getSaldoInicial());
                    r.setTotalIngresos(entity.getTotalIngresos());
                    r.setTotalEgresos(entity.getTotalEgresos());
                    r.setSaldoActual(entity.getSaldoInicial()
                            .add(entity.getTotalIngresos())
                            .subtract(entity.getTotalEgresos()));
                    r.setFechaApertura(entity.getFecha() != null ? entity.getFecha().toString() : null);
                    r.setEstado("ABIERTO");
                    return r;
                })
                .orElse(null);
    }
}
