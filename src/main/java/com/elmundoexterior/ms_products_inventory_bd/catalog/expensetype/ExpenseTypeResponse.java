package com.elmundoexterior.ms_products_inventory_bd.catalog.expensetype;

import java.math.BigDecimal;

public record ExpenseTypeResponse(

        Long id,

        String nombre,

        BigDecimal porcentaje,

        Boolean calculadoAutomaticamente,

        Boolean aplicaIva
) {
}