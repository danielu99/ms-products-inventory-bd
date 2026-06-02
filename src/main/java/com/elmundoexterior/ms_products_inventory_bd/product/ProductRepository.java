package com.elmundoexterior.ms_products_inventory_bd.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<ProductEntity, Long> {
    boolean existsBySku(String sku);
}