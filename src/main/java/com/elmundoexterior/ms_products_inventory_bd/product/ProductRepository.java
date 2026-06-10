package com.elmundoexterior.ms_products_inventory_bd.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<ProductEntity, Long> {
    boolean existsBySku(String sku);

    @Query(value = """
        SELECT
            p.id,
            p.nombre,
            p.stock_actual,
            p.costo_promedio,
            p.precio_final,
            ROUND(
                (
                    (
                        (p.precio_final / 1.16)
                        - p.costo_promedio
                    )
                    /
                    (p.precio_final / 1.16)
                ) * 100,
                2
            ) AS margen_real
        FROM producto p
        ORDER BY margen_real ASC
        """,
            nativeQuery = true)
    List<Object[]> getMarginReport();
}