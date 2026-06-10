package com.elmundoexterior.ms_products_inventory_bd.sale;

import java.math.BigDecimal;

public record SaleItemRequest(
        Long productId,
        Integer cantidad,
        BigDecimal precioUnitario
) {
}