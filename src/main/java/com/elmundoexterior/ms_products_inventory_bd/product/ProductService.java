package com.elmundoexterior.ms_products_inventory_bd.product;

import com.elmundoexterior.ms_products_inventory_bd.product.dto.UpdateMarginRequest;
import com.elmundoexterior.ms_products_inventory_bd.product.dto.UpdatePriceRequest;
import com.elmundoexterior.ms_products_inventory_bd.purchase.PurchaseEntity;
import com.elmundoexterior.ms_products_inventory_bd.purchase.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public ProductResponse createProduct(
            ProductCreateRequest request) {

        BigDecimal costoReal =
                request.compraFacturada()
                        ? request.costoUnitario().divide(
                        BigDecimal.valueOf(1.16),
                        2,
                        RoundingMode.HALF_UP)
                        : request.costoUnitario();

        ProductEntity product =
                ProductEntity.builder()
                        .sku(request.sku())
                        .nombre(request.nombre())
                        .costoPromedio(costoReal)
                        .margenDeseado(request.margenDeseado().divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP))
                        .precioFinal(request.precioFinal())
                        .stockActual(request.cantidadInicial())
                        .fechaCreacion(LocalDateTime.now())
                        .build();

        ProductEntity savedProduct =
                repository.save(product);

        PurchaseEntity purchase =
                PurchaseEntity.builder()
                        .producto(savedProduct)
                        .cantidad(request.cantidadInicial())
                        .costoUnitario(request.costoUnitario())
                        .fecha(LocalDateTime.now())
                        .facturada(request.compraFacturada())
                        .costoReal(costoReal)
                        .build();

        purchaseRepository.save(purchase);

        return map(savedProduct);
    }

    public List<ProductResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public ProductResponse findById(Long id) {

        ProductEntity entity =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Producto no encontrado"));

        return map(entity);
    }

    public ProductResponse updatePrice(
            Long productId,
            UpdatePriceRequest request) {

        ProductEntity product =
                repository.findById(productId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Producto no encontrado"));

        product.setPrecioFinal(
                request.precioFinal());

        product.setMargenDeseado(
                request.margenDeseado());

        ProductEntity saved =
                repository.save(product);

        return map(saved);
    }

    public ProductResponse updateMargin(
            Long productId,
            UpdateMarginRequest request) {

        ProductEntity product =
                repository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Producto no encontrado"));

        BigDecimal priceWithoutVat =
                product.getCostoPromedio()
                        .divide(
                                BigDecimal.ONE.subtract(request.margenDeseado()),
                                2,
                                RoundingMode.HALF_UP);

        BigDecimal finalPrice =
                priceWithoutVat.multiply(
                                BigDecimal.valueOf(1.16))
                        .setScale(
                                2,
                                RoundingMode.HALF_UP);

        product.setMargenDeseado(
                request.margenDeseado());

        product.setPrecioFinal(
                finalPrice);

        ProductEntity saved =
                repository.save(product);

        return map(saved);
    }

    private ProductResponse map(ProductEntity entity) {

        return new ProductResponse(
                entity.getId(),
                entity.getSku(),
                entity.getNombre(),
                entity.getMargenDeseado(),
                entity.getPrecioFinal(),
                entity.getCostoPromedio(),
                entity.getStockActual()
        );
    }
}