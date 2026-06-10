package com.elmundoexterior.ms_products_inventory_bd.catalog.saleschannel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "canal_venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesChannelEntity {

    @Id
    private Long id;

    private String nombre;
}