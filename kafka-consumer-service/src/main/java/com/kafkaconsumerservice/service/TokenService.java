package com.kafkaconsumerservice.service;

import org.springframework.security.core.*;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
public class TokenService {
    private final JwtEncoder encoder;
    private final CustomUserDetailsService customUserDetailsService;


    public TokenService(JwtEncoder encoder, CustomUserDetailsService customUserDetailsService) {
        this.encoder = encoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        String identityKey = customUserDetailsService.getIdentityKey(authentication.getName());
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("identity_key", identityKey)   // Add the user's unique identity key
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
