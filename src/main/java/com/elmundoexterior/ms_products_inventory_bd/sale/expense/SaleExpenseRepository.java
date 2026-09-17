package com.elmundoexterior.ms_products_inventory_bd.sale.expense;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleExpenseRepository
        extends JpaRepository<
        SaleExpenseEntity,
        Long> {

    List<SaleExpenseEntity>
    findByVentaId(Long ventaId);
}