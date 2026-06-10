package com.elmundoexterior.ms_products_inventory_bd.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
    @Query(value = """
        SELECT
            COUNT(*),
            COALESCE(SUM(subtotal), 0),
            COALESCE(SUM(iva), 0),
            COALESCE(SUM(total), 0)
        FROM venta
        """,
            nativeQuery = true)
    List<Object[]> getSalesSummary();
}