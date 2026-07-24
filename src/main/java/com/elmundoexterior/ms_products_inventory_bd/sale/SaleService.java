package com.elmundoexterior.ms_products_inventory_bd.sale;

import com.elmundoexterior.ms_products_inventory_bd.catalog.paymentmethod.PaymentMethodEntity;
import com.elmundoexterior.ms_products_inventory_bd.catalog.paymentmethod.PaymentMethodRepository;
import com.elmundoexterior.ms_products_inventory_bd.catalog.saleschannel.SalesChannelEntity;
import com.elmundoexterior.ms_products_inventory_bd.catalog.saleschannel.SalesChannelRepository;
import com.elmundoexterior.ms_products_inventory_bd.product.ProductEntity;
import com.elmundoexterior.ms_products_inventory_bd.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;

    private final SaleDetailRepository saleDetailRepository;

    private final ProductRepository productRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final SalesChannelRepository salesChannelRepository;

    @Transactional
    public SaleResponse create(
            SaleCreateRequest request) {

        SalesChannelEntity salesChannel =
                salesChannelRepository.findById(
                                request.idCanalVenta())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Canal de venta no encontrado"));

        PaymentMethodEntity paymentMethod =
                paymentMethodRepository.findById(
                                request.idMetodoPago())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Método de pago no encontrado"));

        SaleEntity sale =
                SaleEntity.builder()
                        .fecha(LocalDateTime.now())
                        .canalVenta(salesChannel)
                        .metodoPago(paymentMethod)
                        .facturada(request.facturada())
                        .subtotal(BigDecimal.ZERO)
                        .iva(BigDecimal.ZERO)
                        .total(BigDecimal.ZERO)
                        .build();

        sale = saleRepository.save(sale);

        BigDecimal totalVenta = BigDecimal.ZERO;

        for (SaleItemRequest item : request.items()) {

            ProductEntity product =
                    productRepository.findById(
                                    item.productId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Producto no encontrado"));

            if (product.getStockActual() < item.cantidad()) {

                throw new RuntimeException(
                        "Stock insuficiente para "
                                + product.getNombre());
            }

            BigDecimal subtotalLinea =
                    item.precioUnitario()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.cantidad()));

            SaleDetailEntity detail =
                    SaleDetailEntity.builder()
                            .venta(sale)
                            .producto(product)
                            .cantidad(item.cantidad())
                            .precioUnitario(
                                    item.precioUnitario())
                            .subtotal(subtotalLinea)
                            .build();

            saleDetailRepository.save(detail);

            product.setStockActual(
                    product.getStockActual()
                            - item.cantidad());

            productRepository.save(product);

            totalVenta =
                    totalVenta.add(subtotalLinea);
        }

        BigDecimal total = totalVenta;

        BigDecimal subtotal =
                total.divide(
                        BigDecimal.valueOf(1.16),
                        2,
                        RoundingMode.HALF_UP);

        BigDecimal iva =
                total.subtract(subtotal);

        sale.setSubtotal(subtotal);
        sale.setIva(iva);
        sale.setTotal(total);

        saleRepository.save(sale);

        return toResponse(sale);
    }

    @Transactional(readOnly = true)
    public List<SaleResponse> getAll() {

        return saleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SaleResponse> getByDateRange(
            LocalDateTime fromDate,
            LocalDateTime toDate) {

        return saleRepository
                .findByDateRange(
                        fromDate,
                        toDate)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SaleResponse getById(
            Long id) {

        SaleEntity sale =
                saleRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Venta no encontrada"));

        return toResponse(sale);
    }

    @Transactional(readOnly = true)
    public List<SaleDetailResponse> getDetails(
            Long saleId) {

        return saleDetailRepository
                .findBySaleId(saleId)
                .stream()
                .map(detail ->
                        new SaleDetailResponse(
                                detail.getProducto()
                                        .getNombre(),
                                detail.getCantidad(),
                                detail.getPrecioUnitario(),
                                detail.getSubtotal()
                        )
                )
                .toList();
    }

    private SaleResponse toResponse(
            SaleEntity sale) {

        return new SaleResponse(
                sale.getId(),
                sale.getFecha(),
                sale.getSubtotal(),
                sale.getIva(),
                sale.getTotal(),
                sale.getFacturada()
        );
    }
}

