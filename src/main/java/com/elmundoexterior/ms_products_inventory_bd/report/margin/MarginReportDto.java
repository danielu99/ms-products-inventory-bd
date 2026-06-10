package com.elmundoexterior.ms_products_inventory_bd.report.margin;

import java.math.BigDecimal;

public record MarginReportDto(
        Long productId,
        String nombre,
        Integer stockActual,
        BigDecimal costoPromedio,
        BigDecimal precioFinal,
        BigDecimal margenReal
) {
}