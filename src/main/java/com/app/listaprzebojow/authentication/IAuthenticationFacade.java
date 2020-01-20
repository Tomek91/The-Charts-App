package com.app.listaprzebojow.authentication;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    boolean isUserLoggedIn();
}
