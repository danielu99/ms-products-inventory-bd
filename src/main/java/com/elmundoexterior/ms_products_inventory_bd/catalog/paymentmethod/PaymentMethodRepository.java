package com.elmundoexterior.ms_products_inventory_bd.catalog.paymentmethod;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository
        extends JpaRepository<PaymentMethodEntity, Long> {
}