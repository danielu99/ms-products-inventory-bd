package com.elmundoexterior.ms_products_inventory_bd.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponse create(
            @RequestBody PurchaseCreateRequest request) {

        return service.create(request);
    }

    @GetMapping
    public List<PurchaseResponse> getAll() {

        return service.getAll();
    }

    @GetMapping("/{id}")
    public PurchaseResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }
}