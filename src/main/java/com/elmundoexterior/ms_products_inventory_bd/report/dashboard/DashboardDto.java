package com.elmundoexterior.ms_products_inventory_bd.report.dashboard;

import java.math.BigDecimal;

public record DashboardDto(
        Long productos,
        Integer stockTotal,
        Long ventas,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total
) {
}