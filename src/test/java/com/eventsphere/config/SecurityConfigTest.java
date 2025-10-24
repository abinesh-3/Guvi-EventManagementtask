package com.eventsphere.config;

import com.eventsphere.service.DbUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {
    @Mock
    private DbUserDetailsService userDetailsService;
    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testPasswordEncoderBean() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("test", encoder.encode("test")));
    }

    @Test
    void testAuthenticationProviderBean() {
        DaoAuthenticationProvider provider = securityConfig.authenticationProvider();
        assertNotNull(provider);
        assertTrue(provider instanceof DaoAuthenticationProvider);
    }

    @Test
    void testFilterChainBean() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
        // Just check that the bean method does not throw and returns non-null
        SecurityFilterChain chain = securityConfig.filterChain(http);
        assertNotNull(chain);
    }
}
