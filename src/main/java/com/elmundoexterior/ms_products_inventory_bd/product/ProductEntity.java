package com.elmundoexterior.ms_products_inventory_bd.product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costoPromedio;

    @Column(nullable = false)
    private Boolean facturable;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal margenDeseado;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioFinal;

    @Column(nullable = false)
    private Integer stockActual;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
}