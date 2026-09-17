package com.elmundoexterior.ms_products_inventory_bd.sale.expense;

import java.math.BigDecimal;

public record SaleExpenseResponse(

        Long id,

        String expenseType,

        String description,

        BigDecimal amount
) {
}