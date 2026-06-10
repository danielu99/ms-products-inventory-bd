package com.elmundoexterior.ms_products_inventory_bd.report.pendinginvoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PendingInvoiceSaleDto(
        Long ventaId,
        LocalDateTime fecha,
        String canalVenta,
        String metodoPago,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total
) {
}