package com.elmundoexterior.ms_products_inventory_bd.catalog.paymentmethod;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public List<PaymentMethodResponse> getAll() {

        return paymentMethodService.getAll();
    }
}