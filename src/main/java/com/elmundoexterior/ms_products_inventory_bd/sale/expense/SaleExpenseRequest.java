package com.elmundoexterior.ms_products_inventory_bd.sale.expense;

import java.math.BigDecimal;

public record SaleExpenseRequest(

        Long expenseTypeId,

        String description,

        BigDecimal amount,

        BigDecimal baseAmount
) {
}