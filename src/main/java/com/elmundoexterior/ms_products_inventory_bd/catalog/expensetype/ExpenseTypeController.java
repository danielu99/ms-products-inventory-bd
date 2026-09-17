package com.elmundoexterior.ms_products_inventory_bd.catalog.expensetype;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expense-types")
@RequiredArgsConstructor
public class ExpenseTypeController {

    private final ExpenseTypeService service;

    @GetMapping
    public List<ExpenseTypeResponse> getAll() {

        return service.getAll();
    }
}