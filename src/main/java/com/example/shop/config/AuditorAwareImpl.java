package com.example.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Provides the current authenticated user's ID for JPA auditing.
 * Used to populate @CreatedBy and @LastModifiedBy fields automatically.
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";
        if (authentication != null) {
            userId = authentication.getName(); // gets name of logged-in user.
        }
        return Optional.of(userId);
    }
}
