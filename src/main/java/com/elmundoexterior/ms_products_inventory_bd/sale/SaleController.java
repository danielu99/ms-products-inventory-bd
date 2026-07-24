package com.elmundoexterior.ms_products_inventory_bd.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponse create(
            @RequestBody SaleCreateRequest request) {

        return saleService.create(request);
    }

    @GetMapping
    public List<SaleResponse> getAll() {
        return saleService.getAll();
    }

    @GetMapping("/{id}")
    public SaleResponse getById(
            @PathVariable Long id) {

        return saleService.getById(id);
    }

    @GetMapping("/{id}/details")
    public List<SaleDetailResponse> getDetails(
            @PathVariable Long id) {
        return saleService
                .getDetails(id);
    }

    @GetMapping("/history")
    public List<SaleResponse> getByDateRange(
            @RequestParam String from,
            @RequestParam String to) {

        LocalDateTime fromDate =
                LocalDate.parse(from)
                        .atStartOfDay();

        LocalDateTime toDate =
                LocalDate.parse(to)
                        .atTime(
                                23,
                                59,
                                59);

        return saleService.getByDateRange(
                fromDate,
                toDate);
    }
}