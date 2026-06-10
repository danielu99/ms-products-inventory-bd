package com.elmundoexterior.ms_products_inventory_bd.report.pendinginvoice;

import java.math.BigDecimal;

public record PendingInvoiceSummaryDto(
        Long ventas,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total
) {
}