package com.elmundoexterior.ms_products_inventory_bd.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = """
        SELECT
            COUNT(*),
            COALESCE(SUM(subtotal),0),
            COALESCE(SUM(iva),0),
            COALESCE(SUM(total),0)
        FROM venta
        """,
            nativeQuery = true)
    List<Object[]> getDashboardSalesSummary();

    @Query(value = """
        SELECT
            COUNT(*) ventas,
            COALESCE(SUM(subtotal),0) subtotal,
            COALESCE(SUM(iva),0) iva,
            COALESCE(SUM(total),0) total,

            SUM(
                CASE
                    WHEN facturada = true THEN 1
                    ELSE 0
                END
            ) facturadas,

            SUM(
                CASE
                    WHEN facturada = false THEN 1
                    ELSE 0
                END
            ) pendientes

        FROM venta
        WHERE EXTRACT(YEAR FROM fecha) = :year
        AND EXTRACT(MONTH FROM fecha) = :month
        """,
            nativeQuery = true)
    List<Object[]> getMonthlySales(
            @Param("year") Integer year,
            @Param("month") Integer month);

    @Query(value = """
        SELECT
            v.id,
            v.fecha,
            cv.nombre,
            mp.nombre,
            v.subtotal,
            v.iva,
            v.total
        FROM venta v
        INNER JOIN canal_venta cv
            ON cv.id = v.canal_venta_id
        INNER JOIN metodo_pago mp
            ON mp.id = v.metodo_pago_id
        WHERE v.facturada = false
          AND EXTRACT(YEAR FROM v.fecha) = :year
          AND EXTRACT(MONTH FROM v.fecha) = :month
        ORDER BY v.fecha
        """,
            nativeQuery = true)
    List<Object[]> getPendingInvoiceSales(
            @Param("year") Integer year,
            @Param("month") Integer month);

    @Query(value = """
        SELECT
            COUNT(*),
            COALESCE(SUM(subtotal),0),
            COALESCE(SUM(iva),0),
            COALESCE(SUM(total),0)
        FROM venta
        WHERE facturada = false
          AND EXTRACT(YEAR FROM fecha) = :year
          AND EXTRACT(MONTH FROM fecha) = :month
        """,
            nativeQuery = true)
    List<Object[]> getPendingInvoiceSummary(
            @Param("year") Integer year,
            @Param("month") Integer month);
}