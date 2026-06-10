package com.elmundoexterior.ms_products_inventory_bd.catalog.paymentmethod;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "metodo_pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethodEntity {

    @Id
    private Long id;

    private String nombre;
}