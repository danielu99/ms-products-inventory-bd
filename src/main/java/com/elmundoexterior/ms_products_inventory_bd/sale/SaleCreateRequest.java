package com.elmundoexterior.ms_products_inventory_bd.sale;

import java.util.List;

public record SaleCreateRequest(
        Long idCanalVenta,
        Long idMetodoPago,
        Boolean facturada,
        List<SaleItemRequest> items
) {
}