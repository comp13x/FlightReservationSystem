package com.gerardrama.controller;

import com.gerardrama.entity.CurrentUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserControllerAdvice {
    private static final Logger LOGGER = LogManager.getLogger(CurrentUserControllerAdvice.class);

    @ModelAttribute("currentUser")
    public CurrentUser getCurrentUser(Authentication authentication) {
        LOGGER.info("Authenticating...{}", authentication);
        return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
    }
}
