package com.app.listaprzebojow.security;

import com.app.listaprzebojow.dto.Info;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager ) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        try {

            String token = request.getHeader(SecurityConfig.TOKEN_HEADER);
            if (token != null) {
                var user = TokenService.parseToken(token);
                SecurityContextHolder.getContext().setAuthentication(user);
            }

            chain.doFilter(request, response);
        } catch (Exception e) {

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            Info.<String>builder().error(e.getMessage()).httpStatusCode(HttpStatus.BAD_REQUEST).build()
                    )
            );
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
