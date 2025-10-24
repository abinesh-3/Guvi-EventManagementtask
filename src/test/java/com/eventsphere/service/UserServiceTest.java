package com.eventsphere.service;

import com.eventsphere.model.AppUser;
import com.eventsphere.model.Role;
import com.eventsphere.repository.AppUserRepository;
import com.eventsphere.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private AppUserRepository users;
    @Mock
    private RoleRepository roles;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserService userService;

    private AppUser user;

    @BeforeEach
    void setUp() {
        user = AppUser.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .email("test@example.com")
                .roles(new HashSet<>())
                .build();
    }

    @Test
    void testRegister_UserRole() {
        Role userRole = Role.builder().id(1L).name("USER").build();
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(roles.findByName("USER")).thenReturn(Optional.of(userRole));
        when(users.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser registered = userService.register(user, false);

        assertEquals("encodedPassword", registered.getPassword());
        assertTrue(registered.getRoles().contains(userRole));
        verify(users).save(any(AppUser.class));
    }

    @Test
    void testRegister_AdminRole() {
        Role adminRole = Role.builder().id(2L).name("ADMIN").build();
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(roles.findByName("ADMIN")).thenReturn(Optional.of(adminRole));
        when(users.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser registered = userService.register(user, true);

        assertEquals("encodedPassword", registered.getPassword());
        assertTrue(registered.getRoles().contains(adminRole));
        verify(users).save(any(AppUser.class));
    }

    @Test
    void testRegister_RoleNotFound() {
        Role newRole = Role.builder().id(3L).name("USER").build();
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(roles.findByName("USER")).thenReturn(Optional.empty());
        when(roles.save(any(Role.class))).thenReturn(newRole);
        when(users.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser registered = userService.register(user, false);

        assertEquals("encodedPassword", registered.getPassword());
        assertTrue(registered.getRoles().contains(newRole));
        verify(roles).save(any(Role.class));
        verify(users).save(any(AppUser.class));
    }

    @Test
    void testExistsUsername() {
        when(users.existsByUsername("testuser")).thenReturn(true);
        assertTrue(userService.existsUsername("testuser"));
        verify(users).existsByUsername("testuser");
    }

    @Test
    void testExistsEmail() {
        when(users.existsByEmail("test@example.com")).thenReturn(true);
        assertTrue(userService.existsEmail("test@example.com"));
        verify(users).existsByEmail("test@example.com");
    }
}
