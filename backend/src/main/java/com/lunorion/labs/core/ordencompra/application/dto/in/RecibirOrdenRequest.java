package com.lunorion.labs.core.ordencompra.application.dto.in;

import java.math.BigDecimal;
import java.util.List;

public class RecibirOrdenRequest {
    private List<RecibirItem> items;

    public List<RecibirItem> getItems() { return items; }
    public void setItems(List<RecibirItem> items) { this.items = items; }

    public static class RecibirItem {
        private String itemId;
        private BigDecimal cantidadRecibida;

        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }
        public BigDecimal getCantidadRecibida() { return cantidadRecibida; }
        public void setCantidadRecibida(BigDecimal cantidadRecibida) { this.cantidadRecibida = cantidadRecibida; }
    }
}
