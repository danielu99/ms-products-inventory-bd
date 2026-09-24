package com.elmundoexterior.ms_products_inventory_bd.sale.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SaleExpenseRepository
        extends JpaRepository<
        SaleExpenseEntity,
        Long> {

    List<SaleExpenseEntity>
    findByVentaId(Long ventaId);

    @Query(value = """
    SELECT
        COALESCE(
            SUM(
                CASE
                    WHEN tgv.aplica_iva = true
                        THEN gv.monto / 1.16
                    ELSE gv.monto
                END
            ),
            0
        )
    FROM gasto_venta gv
    INNER JOIN tipo_gasto_venta tgv
        ON tgv.id = gv.tipo_gasto_venta_id
    """,
            nativeQuery = true)
    BigDecimal getTotalExpenses();
}