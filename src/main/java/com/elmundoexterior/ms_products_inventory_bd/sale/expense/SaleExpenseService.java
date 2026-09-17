package com.elmundoexterior.ms_products_inventory_bd.sale.expense;

import com.elmundoexterior.ms_products_inventory_bd.catalog.expensetype.ExpenseTypeEntity;
import com.elmundoexterior.ms_products_inventory_bd.catalog.expensetype.ExpenseTypeRepository;
import com.elmundoexterior.ms_products_inventory_bd.sale.SaleEntity;
import com.elmundoexterior.ms_products_inventory_bd.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleExpenseService {

    private final SaleRepository saleRepository;

    private final SaleExpenseRepository saleExpenseRepository;

    private final ExpenseTypeRepository expenseTypeRepository;

    @Transactional
    public SaleExpenseResponse create(
            Long saleId,
            SaleExpenseRequest request) {

        SaleEntity sale =
                saleRepository.findById(saleId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Venta no encontrada"));

        ExpenseTypeEntity expenseType =
                expenseTypeRepository.findById(
                                request.expenseTypeId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Tipo de gasto no encontrado"));

        String description = request.description();
        if (expenseType.getCalculadoAutomaticamente()
                && (description == null
                || description.isBlank())) {

            description =
                    "Comisión por "
                            + request.baseAmount();
        }

        BigDecimal amount = getAmount(request, expenseType);

        SaleExpenseEntity expense =
                SaleExpenseEntity.builder()
                        .venta(sale)
                        .tipoGasto(expenseType)
                        .descripcion(description)
                        .monto(amount)
                        .build();

        expense =
                saleExpenseRepository.save(expense);

        return new SaleExpenseResponse(
                expense.getId(),
                expenseType.getNombre(),
                expense.getDescripcion(),
                expense.getMonto()
        );
    }

    private static BigDecimal getAmount(SaleExpenseRequest request, ExpenseTypeEntity expenseType) {
        BigDecimal amount;

        if (expenseType.getCalculadoAutomaticamente()) {

            if (request.baseAmount() == null) {

                throw new RuntimeException(
                        "Debe proporcionar baseAmount");
            }

            amount =
                    request.baseAmount()
                            .multiply(
                                    expenseType.getPorcentaje()
                                            .divide(
                                                    BigDecimal.valueOf(100),
                                                    6,
                                                    RoundingMode.HALF_UP))
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP);

        } else {

            if (request.amount() == null) {

                throw new RuntimeException(
                        "Debe proporcionar amount");
            }

            amount = request.amount();
        }
        return amount;
    }

    @Transactional(readOnly = true)
    public List<SaleExpenseResponse> getBySaleId(
            Long saleId) {

        return saleExpenseRepository
                .findByVentaId(saleId)
                .stream()
                .map(expense ->
                        new SaleExpenseResponse(
                                expense.getId(),
                                expense.getTipoGasto()
                                        .getNombre(),
                                expense.getDescripcion(),
                                expense.getMonto()
                        )
                )
                .toList();
    }

    public BigDecimal getTotalExpenses(
            Long saleId) {

        return saleExpenseRepository
                .findByVentaId(saleId)
                .stream()
                .map(SaleExpenseEntity::getMonto)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }

}