package com.elmundoexterior.ms_products_inventory_bd.catalog.saleschannel;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales-channels")
@RequiredArgsConstructor
public class SalesChannelController {

    private final SalesChannelService salesChannelService;

    @GetMapping
    public List<SalesChannelResponse> getAll() {

        return salesChannelService.getAll();
    }
}