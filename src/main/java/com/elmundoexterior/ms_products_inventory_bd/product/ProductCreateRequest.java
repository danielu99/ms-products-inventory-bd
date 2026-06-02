package com.elmundoexterior.ms_products_inventory_bd.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductCreateRequest(

        String sku,

        @NotBlank
        String nombre,

        @NotNull
        Boolean facturable,

        @NotNull
        @PositiveOrZero
        BigDecimal margenDeseado,

        @NotNull
        @PositiveOrZero
        BigDecimal precioFinal

) {
}