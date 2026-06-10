package com.elmundoexterior.ms_products_inventory_bd.purchase;

import java.math.BigDecimal;

public record PurchaseCreateRequest(

        Long productId,

        Integer cantidad,

        BigDecimal costoUnitario,

        Boolean facturada

) {
}