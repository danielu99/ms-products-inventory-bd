package com.elmundoexterior.ms_products_inventory_bd.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleDetailRepository
        extends JpaRepository<SaleDetailEntity, Long> {
    @Query(value = """
        SELECT
            p.id,
            p.nombre,
            SUM(dv.cantidad) unidades_vendidas
        FROM detalle_venta dv
        INNER JOIN producto p
            ON p.id = dv.producto_id
        GROUP BY p.id, p.nombre
        ORDER BY unidades_vendidas DESC
        """,
            nativeQuery = true)
    List<Object[]> getTopProducts();

    @Query(value = """
        SELECT
            p.id,
            p.nombre,
            SUM(dv.cantidad) unidades_vendidas,
            SUM(dv.subtotal) importe_vendido
        FROM detalle_venta dv
        INNER JOIN producto p
            ON p.id = dv.producto_id
        GROUP BY p.id, p.nombre
        ORDER BY importe_vendido DESC
        """,
            nativeQuery = true)
    List<Object[]> getTopRevenueProducts();

    @Query(value = """

        SELECT
            p.id,
            p.nombre,
            SUM(dv.cantidad) unidades_vendidas,
            ROUND(
                SUM(dv.subtotal) / 1.16,
                2
            ) ingresos_sin_iva,
            ROUND(
                SUM(dv.cantidad * p.costo_promedio),
                2
            ) costo_estimado,
            ROUND(
                (SUM(dv.subtotal) / 1.16)
                -
                SUM(dv.cantidad * p.costo_promedio),
                2
            ) utilidad_estimada,
            ROUND(
                (
                    (
                        (SUM(dv.subtotal) / 1.16)
                        -
                        SUM(dv.cantidad * p.costo_promedio)
                    )
                    /
                    NULLIF(
                        (SUM(dv.subtotal) / 1.16),
                        0
                    )
                ) * 100,
                2
            ) margen_real
        FROM detalle_venta dv
        INNER JOIN producto p
            ON p.id = dv.producto_id
        GROUP BY
            p.id,
            p.nombre
        ORDER BY utilidad_estimada DESC;
        """,
            nativeQuery = true)
    List<Object[]> getProfitabilityReport();

    @Query("""
    SELECT sd
    FROM SaleDetailEntity sd
    JOIN FETCH sd.producto
    WHERE sd.venta.id = :saleId
    """)
    List<SaleDetailEntity> findBySaleId(
            Long saleId);
}