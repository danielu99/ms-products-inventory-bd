package com.elmundoexterior.ms_products_inventory_bd.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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