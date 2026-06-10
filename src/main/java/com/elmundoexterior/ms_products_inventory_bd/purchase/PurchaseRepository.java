package com.elmundoexterior.ms_products_inventory_bd.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository
        extends JpaRepository<PurchaseEntity, Long> {
}