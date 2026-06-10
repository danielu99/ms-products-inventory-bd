package com.elmundoexterior.ms_products_inventory_bd.purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseResponse(

        Long id,

        Long productId,

        Integer cantidad,

        BigDecimal costoUnitario,

        Boolean facturada,

        BigDecimal costoReal,

        LocalDateTime fecha

) {
}