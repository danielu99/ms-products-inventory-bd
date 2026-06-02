package com.elmundoexterior.ms_products_inventory_bd.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid @RequestBody ProductCreateRequest request) {

        return service.create(request);
    }

    @GetMapping
    public List<ProductResponse> findAll() {

        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(
            @PathVariable Long id) {

        return service.findById(id);
    }
}