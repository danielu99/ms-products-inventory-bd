package com.elmundoexterior.ms_products_inventory_bd.report;

import com.elmundoexterior.ms_products_inventory_bd.report.dashboard.DashboardDto;
import com.elmundoexterior.ms_products_inventory_bd.report.margin.MarginReportDto;
import com.elmundoexterior.ms_products_inventory_bd.report.monthlysales.MonthlySalesDto;
import com.elmundoexterior.ms_products_inventory_bd.report.pendinginvoice.PendingInvoiceSaleDto;
import com.elmundoexterior.ms_products_inventory_bd.report.pendinginvoice.PendingInvoiceSummaryDto;
import com.elmundoexterior.ms_products_inventory_bd.report.profitability.ProfitabilityDto;
import com.elmundoexterior.ms_products_inventory_bd.report.revenue.TopRevenueProductDto;
import com.elmundoexterior.ms_products_inventory_bd.report.sales.SalesSummaryDto;
import com.elmundoexterior.ms_products_inventory_bd.report.topproducts.TopProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/top-products")
    public List<TopProductDto> getTopProducts() {

        return reportService.getTopProducts();
    }

    @GetMapping("/top-revenue-products")
    public List<TopRevenueProductDto> getTopRevenueProducts() {

        return reportService.getTopRevenueProducts();
    }

    @GetMapping("/profitability")
    public List<ProfitabilityDto> getProfitabilityReport() {

        return reportService.getProfitabilityReport();
    }

    @GetMapping("/dashboard")
    public DashboardDto getDashboard() {

        return reportService.getDashboard();
    }

    @GetMapping("/monthly-sales")
    public MonthlySalesDto getMonthlySales(
            @RequestParam Integer year,
            @RequestParam Integer month) {

        return reportService
                .getMonthlySales(year, month);
    }

    @GetMapping("/pending-invoice-sales")
    public List<PendingInvoiceSaleDto> getPendingInvoiceSales(
            @RequestParam Integer year,
            @RequestParam Integer month) {

        return reportService
                .getPendingInvoiceSales(year, month);
    }

    @GetMapping("/pending-invoice-summary")
    public PendingInvoiceSummaryDto getPendingInvoiceSummary(
            @RequestParam Integer year,
            @RequestParam Integer month) {

        return reportService
                .getPendingInvoiceSummary(year, month);
    }

    @GetMapping("/pending-invoice-sales/csv")
    public ResponseEntity<byte[]> exportPendingInvoiceSalesCsv(
            @RequestParam Integer year,
            @RequestParam Integer month) {

        return reportService
                .exportPendingInvoiceSalesCsv(
                        year,
                        month);
    }

    @PostMapping("/mark-invoiced")
    public Integer markAsInvoiced(
            @RequestParam Integer year,
            @RequestParam Integer month) {

        return reportService
                .markAsInvoiced(
                        year,
                        month
                );
    }
}