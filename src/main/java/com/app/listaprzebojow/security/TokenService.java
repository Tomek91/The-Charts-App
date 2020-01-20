package com.app.listaprzebojow.security;

import com.app.listaprzebojow.dto.Tokens;
import com.app.listaprzebojow.exception.MyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface TokenService {

    static Tokens generateTokens( Authentication authentication ) {

        if ( authentication == null ) {
            throw new MyException("generate tokens - authentication is null");
        }

        return Tokens.builder()
                .accessToken(generateToken(authentication, TokenType.ACCESS_TOKEN))
                .refreshToken(generateToken(authentication, TokenType.REFRESH_TOKEN))
                .build();
    }

    private static String generateToken(Authentication authentication, TokenType tokenType) {

        String username = authentication.getName();

        String roles = authentication
                .getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(Collectors.joining(","));

        long expirationTime = SecurityConfig.ACCESS_TOKEN_EXPIRATION_TIME;

        if ( tokenType.equals(TokenType.REFRESH_TOKEN) ) {

            roles = "ROLE_REFRESH," + roles;
            expirationTime = SecurityConfig.REFRESH_TOKEN_EXPIRATION_TIME;

        }

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, SecurityConfig.SECRET_KEY)
                .claim("roles", roles)
                .compact();

    }

    static Tokens generateToken( String refreshToken ) {
        if ( refreshToken == null ) {
            throw new MyException("generate token exception - refresh token is null");
        }

        if ( !isTokenValid(refreshToken) ) {
            throw new MyException("generate token exception - refresh token has been expired");
        }

        if ( !isRoleRefresh(refreshToken) ) {
            throw new MyException("generate token exception - refresh token do not have refresh role");
        }

        String username = getUsername(refreshToken);
        List<GrantedAuthority> roles = getRoles(refreshToken);

        var authentication = new UsernamePasswordAuthenticationToken(username, null, roles);

        return Tokens.builder()
                .accessToken(generateToken(authentication, TokenType.ACCESS_TOKEN))
                .refreshToken(refreshToken)
                .build();
    }

    static UsernamePasswordAuthenticationToken parseToken( String token ) {

        if ( token == null ) {
            throw new MyException("parse token exception - token is null");
        }

        if ( !token.startsWith(SecurityConfig.TOKEN_PREFIX) ) {
            throw new MyException("parse token exception - token doesn't start with prefix");
        }

        if ( !isTokenValid(token) ) {
            throw new MyException("parse token exception - token has been expired");
        }

        String username = getUsername(token);
        List<GrantedAuthority> roles = getRoles(token);

        return new UsernamePasswordAuthenticationToken(username, null, roles);

    }

    private static Claims getClaims(String token ) {
        return Jwts
                .parser()
                .setSigningKey(SecurityConfig.SECRET_KEY)
                .parseClaimsJws(token.replace("Bearer", ""))
                .getBody();
    }

    private static Claims getRefreshTokenClaims(String token ) {
        return Jwts
                .parser()
                .setSigningKey(SecurityConfig.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean isTokenValid ( String token ) {
        return getClaims(token ).getExpiration().after(new Date());
    }

    private static String getUsername ( String token ) {
        return getClaims(token ).getSubject();
    }

    private static List<GrantedAuthority> getRoles (String token ) {
        return Arrays
                .stream(getClaims(token ).get("roles").toString().split(","))
                .filter(role -> !role.equals("ROLE_REFRESH"))
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    private static boolean isRoleRefresh (String token ) {
        return Arrays
                .stream(getRefreshTokenClaims(token ).get("roles").toString().split(","))
                .anyMatch(role  -> role.equals("ROLE_REFRESH"));
    }


}
