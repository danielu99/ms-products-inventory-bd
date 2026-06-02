package com.elmundoexterior.ms_products_inventory_bd.product;

import java.math.BigDecimal;

public record ProductResponse(

        Long id,

        String sku,

        String nombre,

        Boolean facturable,

        BigDecimal margenDeseado,

        BigDecimal precioFinal,

        BigDecimal costoPromedio,

        Integer stockActual

) {
}