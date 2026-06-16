package com.elmundoexterior.ms_products_inventory_bd.catalog.saleschannel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesChannelService {

    private final SalesChannelRepository salesChannelRepository;

    public List<SalesChannelResponse> getAll() {

        return salesChannelRepository.findAll()
                .stream()
                .map(channel ->
                        new SalesChannelResponse(
                                channel.getId(),
                                channel.getNombre()
                        ))
                .toList();
    }
}