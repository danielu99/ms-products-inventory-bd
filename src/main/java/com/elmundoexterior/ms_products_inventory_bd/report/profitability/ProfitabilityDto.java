package com.elmundoexterior.ms_products_inventory_bd.report.profitability;

import java.math.BigDecimal;

public record ProfitabilityDto(
        Long productId,
        String nombre,
        Long unidadesVendidas,
        BigDecimal ingresos,
        BigDecimal costoEstimado,
        BigDecimal utilidadEstimada,
        BigDecimal margenReal
) {
}