package com.elmundoexterior.ms_products_inventory_bd.product;

import java.math.BigDecimal;

public record ProductCreateRequest(
        String nombre,
        String codigoBarras,
        Integer cantidadInicial,
        BigDecimal costoUnitario,
        BigDecimal margenDeseado,
        BigDecimal precioFinal,
        Boolean compraFacturada
) {
}