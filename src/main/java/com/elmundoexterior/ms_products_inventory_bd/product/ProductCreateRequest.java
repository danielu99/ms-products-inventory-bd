package com.elmundoexterior.ms_products_inventory_bd.product;

import java.math.BigDecimal;

public record ProductCreateRequest(
        String sku,
        String nombre,
        Integer cantidadInicial,
        BigDecimal costoUnitario,
        BigDecimal margenDeseado,
        BigDecimal precioFinal,
        Boolean compraFacturada
) {
}