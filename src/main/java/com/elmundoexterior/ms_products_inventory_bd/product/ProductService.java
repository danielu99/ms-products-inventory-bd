package com.elmundoexterior.ms_products_inventory_bd.product;

import com.elmundoexterior.ms_products_inventory_bd.product.dto.UpdateMarginRequest;
import com.elmundoexterior.ms_products_inventory_bd.product.dto.UpdatePriceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductResponse create(ProductCreateRequest request) {

        ProductEntity entity =
                ProductEntity.builder()
                        .sku(request.sku())
                        .nombre(request.nombre())
                        .margenDeseado(request.margenDeseado())
                        .precioFinal(request.precioFinal())
                        .costoPromedio(java.math.BigDecimal.ZERO)
                        .stockActual(0)
                        .fechaCreacion(java.time.LocalDateTime.now())
                        .build();

        ProductEntity saved =
                repository.save(entity);

        return map(saved);
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