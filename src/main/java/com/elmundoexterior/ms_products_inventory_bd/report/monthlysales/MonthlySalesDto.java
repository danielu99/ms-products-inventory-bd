package com.elmundoexterior.ms_products_inventory_bd.report.monthlysales;

import java.math.BigDecimal;

public record MonthlySalesDto(
        Integer year,
        Integer month,
        Long ventas,
        BigDecimal subtotal,
        BigDecimal iva,
        BigDecimal total,
        Long facturadas,
        Long pendientesFacturar
) {
}