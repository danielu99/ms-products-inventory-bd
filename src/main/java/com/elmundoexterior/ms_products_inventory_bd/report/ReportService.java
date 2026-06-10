package com.elmundoexterior.ms_products_inventory_bd.report;

import com.elmundoexterior.ms_products_inventory_bd.product.ProductRepository;
import com.elmundoexterior.ms_products_inventory_bd.report.margin.MarginReportDto;
import com.elmundoexterior.ms_products_inventory_bd.report.sales.SalesSummaryDto;
import com.elmundoexterior.ms_products_inventory_bd.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

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
}