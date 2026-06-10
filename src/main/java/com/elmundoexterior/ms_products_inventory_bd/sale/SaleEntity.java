package com.elmundoexterior.ms_products_inventory_bd.sale;

import com.elmundoexterior.ms_products_inventory_bd.catalog.paymentmethod.PaymentMethodEntity;
import com.elmundoexterior.ms_products_inventory_bd.catalog.saleschannel.SalesChannelEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canal_venta_id")
    private SalesChannelEntity canalVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metodo_pago_id")
    private PaymentMethodEntity metodoPago;

    private BigDecimal subtotal;

    private BigDecimal iva;

    private BigDecimal total;

    private Boolean facturada;
}