package com.elmundoexterior.ms_products_inventory_bd.product.dto;

import java.math.BigDecimal;

public record UpdateMarginRequest(
        BigDecimal margenDeseado
) {
}