package com.eventsphere.web;

import com.eventsphere.model.AppUser;
import com.eventsphere.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private AuthController authController;

    private AppUser user;

    @BeforeEach
    void setUp() {
        user = AppUser.builder().username("testuser").email("test@example.com").build();
    }

    @Test
    void testLogin() {
        String view = authController.login();
        assertEquals("auth/login", view);
    }

    @Test
    void testRegisterForm() {
        String view = authController.registerForm(model);
        verify(model).addAttribute(eq("user"), any(AppUser.class));
        assertEquals("auth/register", view);
    }

    @Test
    void testRegister_UsernameExists() {
        when(userService.existsUsername(user.getUsername())).thenReturn(true);
        when(userService.existsEmail(user.getEmail())).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(true);
        String view = authController.register(user, bindingResult, model);
        verify(bindingResult).rejectValue(eq("username"), eq("exists"), anyString());
        assertEquals("auth/register", view);
        verify(userService, never()).register(any(AppUser.class), anyBoolean());
    }

    @Test
    void testRegister_EmailExists() {
        when(userService.existsUsername(user.getUsername())).thenReturn(false);
        when(userService.existsEmail(user.getEmail())).thenReturn(true);
        when(bindingResult.hasErrors()).thenReturn(true);
        String view = authController.register(user, bindingResult, model);
        verify(bindingResult).rejectValue(eq("email"), eq("exists"), anyString());
        assertEquals("auth/register", view);
        verify(userService, never()).register(any(AppUser.class), anyBoolean());
    }

    @Test
    void testRegister_HasErrors() {
        when(userService.existsUsername(user.getUsername())).thenReturn(false);
        when(userService.existsEmail(user.getEmail())).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(true);
        String view = authController.register(user, bindingResult, model);
        assertEquals("auth/register", view);
        verify(userService, never()).register(any(AppUser.class), anyBoolean());
    }

    @Test
    void testRegister_Success() {
        when(userService.existsUsername(user.getUsername())).thenReturn(false);
        when(userService.existsEmail(user.getEmail())).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(false);
        String view = authController.register(user, bindingResult, model);
        verify(userService).register(user, false);
        verify(model).addAttribute(eq("msg"), anyString());
        assertEquals("auth/login", view);
    }
}
