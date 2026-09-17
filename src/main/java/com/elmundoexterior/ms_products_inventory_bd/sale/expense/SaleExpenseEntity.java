package com.elmundoexterior.ms_products_inventory_bd.sale.expense;

import com.elmundoexterior.ms_products_inventory_bd.catalog.expensetype.ExpenseTypeEntity;
import com.elmundoexterior.ms_products_inventory_bd.sale.SaleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "gasto_venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private SaleEntity venta;

    @ManyToOne
    @JoinColumn(name = "tipo_gasto_venta_id")
    private ExpenseTypeEntity tipoGasto;

    private String descripcion;

    private BigDecimal monto;
}