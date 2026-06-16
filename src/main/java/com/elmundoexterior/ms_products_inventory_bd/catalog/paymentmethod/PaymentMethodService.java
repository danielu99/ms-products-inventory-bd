package com.elmundoexterior.ms_products_inventory_bd.catalog.paymentmethod;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethodResponse> getAll() {

        return paymentMethodRepository.findAll()
                .stream()
                .map(paymentMethod ->
                        new PaymentMethodResponse(
                                paymentMethod.getId(),
                                paymentMethod.getNombre()
                        ))
                .toList();
    }
}