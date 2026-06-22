package com.lunorion.labs.core.ordencompra.domain.ports.in;

import com.lunorion.labs.core.ordencompra.application.dto.in.CreateOrdenCompraRequest;
import com.lunorion.labs.core.ordencompra.application.dto.in.RecibirOrdenRequest;
import com.lunorion.labs.core.ordencompra.application.dto.out.OrdenCompraResponse;

public interface IOrdenCompraCommandPort {
    OrdenCompraResponse create(CreateOrdenCompraRequest request);
    void aprobar(String id);
    void completar(String id);
    void anular(String id);
    OrdenCompraResponse recibir(String id, RecibirOrdenRequest request);
}
