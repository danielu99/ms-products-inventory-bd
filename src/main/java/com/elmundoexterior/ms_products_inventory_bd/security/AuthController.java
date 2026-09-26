package com.elmundoexterior.ms_products_inventory_bd.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/me")
    public Map<String, String> me(
            Authentication authentication) {

        String role =
                authentication
                        .getAuthorities()
                        .stream()
                        .map(
                                GrantedAuthority::getAuthority
                        )
                        .filter(
                                authority ->
                                        authority.startsWith(
                                                "ROLE_"
                                        )
                        )
                        .findFirst()
                        .map(
                                authority ->
                                        authority.replace(
                                                "ROLE_",
                                                ""
                                        )
                        )
                        .orElse("");

        return Map.of(
                "username",
                authentication.getName(),
                "role",
                role
        );
    }
}