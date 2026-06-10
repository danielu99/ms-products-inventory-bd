package com.elmundoexterior.ms_products_inventory_bd.report.revenue;

import java.math.BigDecimal;

public record TopRevenueProductDto(
        Long productId,
        String nombre,
        Long unidadesVendidas,
        BigDecimal importeVendido
) {
}