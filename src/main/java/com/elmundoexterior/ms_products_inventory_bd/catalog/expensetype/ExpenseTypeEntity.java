package com.elmundoexterior.ms_products_inventory_bd.catalog.expensetype;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tipo_gasto_venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private BigDecimal porcentaje;

    @Column(name = "calculado_automaticamente")
    private Boolean calculadoAutomaticamente;

    @Column(name = "aplica_iva")
    private Boolean aplicaIva;
}