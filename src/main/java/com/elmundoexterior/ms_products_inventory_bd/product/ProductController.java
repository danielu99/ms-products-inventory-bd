package com.elmundoexterior.ms_products_inventory_bd.product;

import com.elmundoexterior.ms_products_inventory_bd.product.dto.UpdateMarginRequest;
import com.elmundoexterior.ms_products_inventory_bd.product.dto.UpdatePriceRequest;
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

        return service.createProduct(request);
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

    @PatchMapping("/{id}/price")
    public ProductResponse updatePrice(
            @PathVariable Long id,
            @RequestBody UpdatePriceRequest request) {

        return service
                .updatePrice(id, request);
    }

    @PatchMapping("/{id}/margin")
    public ProductResponse updateMargin(
            @PathVariable Long id,
            @RequestBody UpdateMarginRequest request) {

        return service
                .updateMargin(id, request);
    }
}