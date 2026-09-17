package com.elmundoexterior.ms_products_inventory_bd.catalog.expensetype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTypeRepository
        extends JpaRepository<
                ExpenseTypeEntity,
                Long> {
}