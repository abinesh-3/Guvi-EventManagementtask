package com.eventsphere.service;

import com.eventsphere.model.AppUser;
import com.eventsphere.model.Role;
import com.eventsphere.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DbUserDetailsServiceTest {
    @Mock
    private AppUserRepository users;
    @InjectMocks
    private DbUserDetailsService dbUserDetailsService;

    private AppUser user;

    @BeforeEach
    void setUp() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().id(1L).name("USER").build());
        user = AppUser.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .email("test@example.com")
                .enabled(true)
                .roles(roles)
                .build();
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(users.findByUsername("testuser")).thenReturn(Optional.of(user));
        UserDetails details = dbUserDetailsService.loadUserByUsername("testuser");
        assertEquals("testuser", details.getUsername());
        assertEquals("password", details.getPassword());
        assertTrue(details.isEnabled());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(users).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        when(users.findByUsername("nouser")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> dbUserDetailsService.loadUserByUsername("nouser"));
        verify(users).findByUsername("nouser");
    }
}
