package com.elmundoexterior.ms_products_inventory_bd.report;

import com.elmundoexterior.ms_products_inventory_bd.report.margin.MarginReportDto;
import com.elmundoexterior.ms_products_inventory_bd.report.sales.SalesSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/margins")
    public List<MarginReportDto> getMargins() {

        return reportService.getMargins();
    }

    @GetMapping("/sales-summary")
    public SalesSummaryDto getSalesSummary() {

        return reportService.getSalesSummary();
    }
}