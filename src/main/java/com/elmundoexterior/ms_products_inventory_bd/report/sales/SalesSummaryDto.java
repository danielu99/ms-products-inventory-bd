package com.elmundoexterior.ms_products_inventory_bd.report.sales;

import java.math.BigDecimal;

public record SalesSummaryDto(
        Long totalVentas,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total
) {
}