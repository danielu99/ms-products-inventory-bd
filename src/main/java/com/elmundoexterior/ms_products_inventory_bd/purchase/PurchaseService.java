package com.elmundoexterior.ms_products_inventory_bd.purchase;

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
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final ProductRepository productRepository;

    @Transactional
    public PurchaseResponse create(
            PurchaseCreateRequest request) {

        ProductEntity product =
                productRepository.findById(request.productId())
                        .orElseThrow(() ->
                                new RuntimeException("Producto no encontrado"));

        BigDecimal costoReal =
                request.facturada()
                        ? request.costoUnitario()
                        .divide(
                                BigDecimal.valueOf(1.16),
                                2,
                                RoundingMode.HALF_UP)
                        : request.costoUnitario();

        PurchaseEntity purchase =
                PurchaseEntity.builder()
                        .producto(product)
                        .fecha(LocalDateTime.now())
                        .cantidad(request.cantidad())
                        .costoUnitario(request.costoUnitario())
                        .facturada(request.facturada())
                        .costoReal(costoReal)
                        .build();

        PurchaseEntity saved =
                purchaseRepository.save(purchase);

        actualizarInventario(
                product,
                request.cantidad(),
                costoReal);

        productRepository.save(product);

        return new PurchaseResponse(
                saved.getId(),
                product.getId(),
                saved.getCantidad(),
                saved.getCostoUnitario(),
                saved.getFacturada(),
                saved.getCostoReal(),
                saved.getFecha()
        );
    }

    private void actualizarInventario(
            ProductEntity product,
            Integer cantidadNueva,
            BigDecimal costoRealNuevo) {

        Integer stockAnterior =
                product.getStockActual();

        BigDecimal costoPromedioAnterior =
                product.getCostoPromedio();

        /*
         * Primera compra del producto
         */
        if (stockAnterior == null || stockAnterior == 0) {

            product.setStockActual(cantidadNueva);

            product.setCostoPromedio(costoRealNuevo);

            return;
        }

        Integer nuevoStock =
                stockAnterior + cantidadNueva;

        BigDecimal valorInventarioAnterior =
                costoPromedioAnterior.multiply(
                        BigDecimal.valueOf(stockAnterior));

        BigDecimal valorCompraNueva =
                costoRealNuevo.multiply(
                        BigDecimal.valueOf(cantidadNueva));

        BigDecimal nuevoCostoPromedio =
                valorInventarioAnterior
                        .add(valorCompraNueva)
                        .divide(
                                BigDecimal.valueOf(nuevoStock),
                                2,
                                RoundingMode.HALF_UP);

        product.setStockActual(nuevoStock);

        product.setCostoPromedio(nuevoCostoPromedio);
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponse> getAll() {

        return purchaseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PurchaseResponse getById(
            Long id) {

        PurchaseEntity purchase =
                purchaseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Compra no encontrada"));

        return toResponse(purchase);
    }

    private PurchaseResponse toResponse(
            PurchaseEntity purchase) {

        return new PurchaseResponse(
                purchase.getId(),
                purchase.getProducto().getId(),
                purchase.getCantidad(),
                purchase.getCostoUnitario(),
                purchase.getFacturada(),
                purchase.getCostoReal(),
                purchase.getFecha()
        );
    }
}