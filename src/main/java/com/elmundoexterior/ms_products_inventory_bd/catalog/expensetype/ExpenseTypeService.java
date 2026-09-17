package com.elmundoexterior.ms_products_inventory_bd.catalog.expensetype;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseTypeService {

    private final ExpenseTypeRepository repository;

    public List<ExpenseTypeResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(type ->
                        new ExpenseTypeResponse(
                                type.getId(),
                                type.getNombre(),
                                type.getPorcentaje(),
                                type.getCalculadoAutomaticamente(),
                                type.getAplicaIva()
                        ))
                .toList();
    }
}