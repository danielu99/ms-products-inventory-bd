package com.elmundoexterior.ms_products_inventory_bd.sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailRepository
        extends JpaRepository<SaleDetailEntity, Long> {
}