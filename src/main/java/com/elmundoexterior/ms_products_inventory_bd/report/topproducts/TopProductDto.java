package com.elmundoexterior.ms_products_inventory_bd.report.topproducts;

public record TopProductDto(
        Long productId,
        String nombre,
        Long unidadesVendidas
) {
}