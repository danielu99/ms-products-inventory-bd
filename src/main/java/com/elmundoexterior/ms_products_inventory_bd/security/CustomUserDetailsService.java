package com.elmundoexterior.ms_products_inventory_bd.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        UsuarioEntity usuario =
                usuarioRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Usuario no encontrado"
                                ));

        return User.builder()
                .username(
                        usuario.getUsername()
                )
                .password(
                        usuario.getPassword()
                )
                .disabled(
                        !usuario.getEnabled()
                )
                .authorities("USER")
                .build();
    }
}