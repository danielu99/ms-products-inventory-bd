package com.elmundoexterior.ms_products_inventory_bd.report;

import com.elmundoexterior.ms_products_inventory_bd.product.ProductRepository;
import com.elmundoexterior.ms_products_inventory_bd.report.dashboard.DashboardDto;
import com.elmundoexterior.ms_products_inventory_bd.report.margin.MarginReportDto;
import com.elmundoexterior.ms_products_inventory_bd.report.monthlysales.MonthlySalesDto;
import com.elmundoexterior.ms_products_inventory_bd.report.pendinginvoice.PendingInvoiceSaleDto;
import com.elmundoexterior.ms_products_inventory_bd.report.pendinginvoice.PendingInvoiceSummaryDto;
import com.elmundoexterior.ms_products_inventory_bd.report.profitability.ProfitabilityDto;
import com.elmundoexterior.ms_products_inventory_bd.report.revenue.TopRevenueProductDto;
import com.elmundoexterior.ms_products_inventory_bd.report.sales.SalesSummaryDto;
import com.elmundoexterior.ms_products_inventory_bd.report.topproducts.TopProductDto;
import com.elmundoexterior.ms_products_inventory_bd.sale.SaleDetailRepository;
import com.elmundoexterior.ms_products_inventory_bd.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    public List<MarginReportDto> getMargins() {

        return productRepository.getMarginReport()
                .stream()
                .map(this::mapMargin)
                .toList();
    }

    public SalesSummaryDto getSalesSummary() {

        Object[] row =  saleRepository.getSalesSummary().get(0);

        return new SalesSummaryDto(
                ((Number) row[0]).longValue(),
                (BigDecimal) row[1],
                (BigDecimal) row[2],
                (BigDecimal) row[3]
        );
    }

    public List<TopProductDto> getTopProducts() {

        return saleDetailRepository
                .getTopProducts()
                .stream()
                .map(this::mapTopProduct)
                .toList();
    }

    public List<TopRevenueProductDto> getTopRevenueProducts() {

        return saleDetailRepository
                .getTopRevenueProducts()
                .stream()
                .map(this::mapTopRevenueProduct)
                .toList();
    }

    public List<ProfitabilityDto> getProfitabilityReport() {

        return saleDetailRepository
                .getProfitabilityReport()
                .stream()
                .map(this::mapProfitability)
                .toList();
    }

    public DashboardDto getDashboard() {
        Object[] inventory = productRepository
                        .getInventorySummary().get(0);
        Object[] sales = saleRepository
                        .getDashboardSalesSummary().get(0);

        return new DashboardDto(
                ((Number) inventory[0]).longValue(),
                ((Number) inventory[1]).intValue(),

                ((Number) sales[0]).longValue(),
                (BigDecimal) sales[1],
                (BigDecimal) sales[2],
                (BigDecimal) sales[3]
        );
    }

    public MonthlySalesDto getMonthlySales(
            Integer year,
            Integer month) {

        Object[] row = saleRepository
                        .getMonthlySales(year, month).get(0);

        Long facturadas =
                row[4] == null
                        ? 0L
                        : ((Number) row[4]).longValue();

        Long pendientes =
                row[5] == null
                        ? 0L
                        : ((Number) row[5]).longValue();

        return new MonthlySalesDto(
                year,
                month,
                ((Number) row[0]).longValue(),
                (BigDecimal) row[1],
                (BigDecimal) row[2],
                (BigDecimal) row[3],
                facturadas,
                pendientes
        );
    }

    public List<PendingInvoiceSaleDto> getPendingInvoiceSales(
            Integer year,
            Integer month) {

        return saleRepository
                .getPendingInvoiceSales(year, month)
                .stream()
                .map(this::mapPendingInvoiceSale)
                .toList();
    }

    public ResponseEntity<byte[]> exportPendingInvoiceSalesCsv(
            Integer year,
            Integer month) {

        List<PendingInvoiceSaleDto> sales =
                getPendingInvoiceSales(
                        year,
                        month);

        StringBuilder csv =
                new StringBuilder();

        csv.append(
                "Venta ID,Fecha,Canal,Metodo Pago,Subtotal,IVA,Total\n");

        for (PendingInvoiceSaleDto sale : sales) {

            csv.append(sale.ventaId())
                    .append(",")
                    .append(sale.fecha())
                    .append(",")
                    .append(sale.canalVenta())
                    .append(",")
                    .append(sale.metodoPago())
                    .append(",")
                    .append(sale.subtotal())
                    .append(",")
                    .append(sale.iva())
                    .append(",")
                    .append(sale.total())
                    .append("\n");
        }

        byte[] bytes =
                csv.toString().getBytes();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=ventas_pendientes.csv")
                .contentType(
                        MediaType.parseMediaType(
                                "text/csv"))
                .body(bytes);
    }

    public PendingInvoiceSummaryDto getPendingInvoiceSummary(
            Integer year,
            Integer month) {

        Object[] row = saleRepository
                        .getPendingInvoiceSummary(year, month).get(0);

        return new PendingInvoiceSummaryDto(
                ((Number) row[0]).longValue(),
                (BigDecimal) row[1],
                (BigDecimal) row[2],
                (BigDecimal) row[3]
        );
    }

    private MarginReportDto mapMargin(
            Object[] row) {

        return new MarginReportDto(
                ((Number) row[0]).longValue(),
                (String) row[1],
                ((Number) row[2]).intValue(),
                (BigDecimal) row[3],
                (BigDecimal) row[4],
                (BigDecimal) row[5]
        );
    }

    private TopProductDto mapTopProduct(
            Object[] row) {

        return new TopProductDto(
                ((Number) row[0]).longValue(),
                (String) row[1],
                ((Number) row[2]).longValue()
        );
    }

    private TopRevenueProductDto mapTopRevenueProduct(
            Object[] row) {

        return new TopRevenueProductDto(
                ((Number) row[0]).longValue(),
                (String) row[1],
                ((Number) row[2]).longValue(),
                (BigDecimal) row[3]
        );
    }

    private ProfitabilityDto mapProfitability(
            Object[] row) {

        return new ProfitabilityDto(
                ((Number) row[0]).longValue(),
                (String) row[1],
                ((Number) row[2]).longValue(),
                (BigDecimal) row[3],
                (BigDecimal) row[4],
                (BigDecimal) row[5],
                (BigDecimal) row[6]
        );
    }

    private PendingInvoiceSaleDto mapPendingInvoiceSale(
            Object[] row) {

        return new PendingInvoiceSaleDto(
                ((Number) row[0]).longValue(),
                ((java.sql.Timestamp) row[1]).toLocalDateTime(),
                (String) row[2],
                (String) row[3],
                (BigDecimal) row[4],
                (BigDecimal) row[5],
                (BigDecimal) row[6]
        );
    }
}