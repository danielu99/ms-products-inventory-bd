package com.elmundoexterior.ms_products_inventory_bd.sale.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales/{saleId}/expenses")
@RequiredArgsConstructor
public class SaleExpenseController {

    private final SaleExpenseService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleExpenseResponse create(
            @PathVariable Long saleId,
            @RequestBody SaleExpenseRequest request) {

        return service.create(
                saleId,
                request);
    }

    @GetMapping
    public List<SaleExpenseResponse> getBySaleId(
            @PathVariable Long saleId) {

        return service.getBySaleId(
                saleId);
    }
}