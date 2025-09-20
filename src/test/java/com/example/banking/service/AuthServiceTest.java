package com.example.banking.service;

import com.example.banking.exception.*;
import com.example.banking.model.Customer;
import com.example.banking.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private AuthService authService;

    private Customer existingCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resetSingleton();
        authService = AuthService.getInstance(customerRepository);

        existingCustomer = new Customer("C1", "John Doe", "john@example.com", "secret123", "9876543210");

        when(customerRepository.findByEmail("john@example.com")).thenReturn(existingCustomer);
        when(customerRepository.findById("C1")).thenReturn(existingCustomer);
        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    private void resetSingleton() {
        try {
            var field = AuthService.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception ignored) {}
    }

    // ---------- register ----------
    @Test
    void register_Success() {
        when(customerRepository.findByEmail("new@example.com")).thenReturn(null);

        Customer newCust = authService.register("Alice", "new@example.com", "mypwd12", "1234567890");

        assertNotNull(newCust);
        assertEquals("new@example.com", newCust.getEmail());
        verify(customerRepository).save(newCust);
    }

    @Test
    void register_FailsIfEmailInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> authService.register("Alice", "bade mail", "mypwd12", "1234567890"));
    }

    @Test
    void register_FailsIfPasswordInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> authService.register("Alice", "alice@example.com", "123", "1234567890"));
    }

    @Test
    void register_FailsIfPhoneInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> authService.register("Alice", "alice@example.com", "mypwd12", "12345"));
    }

    @Test
    void register_FailsIfEmailAlreadyRegistered() {
        assertThrows(EmailAlreadyRegisteredException.class,
                () -> authService.register("Alice", "john@example.com", "mypwd12", "1234567890"));
    }

    // ---------- login ----------
    @Test
    void login_Success() {
        Customer c = authService.login("john@example.com", "secret123");
        assertEquals("john@example.com", c.getEmail());
    }

    @Test
    void login_FailsIfWrongPassword() {
        assertThrows(InvalidCredentialsException.class,
                () -> authService.login("john@example.com", "wrongness"));
    }

    @Test
    void login_FailsIfUserNotFound() {
        when(customerRepository.findByEmail("missing@example.com")).thenReturn(null);
        assertThrows(InvalidCredentialsException.class,
                () -> authService.login("missing@example.com", "any"));
    }

    // ---------- changePassword ----------
    @Test
    void changePassword_Success() {
        authService.changePassword("C1", "secret123", "newness12");
        assertEquals("newness12", existingCustomer.getPassword());
        verify(customerRepository).save(existingCustomer);
    }

    @Test
    void changePassword_FailsIfUserNotFound() {
        when(customerRepository.findById("C2")).thenReturn(null);
        assertThrows(InvalidCredentialsException.class,
                () -> authService.changePassword("C2", "any", "newness12"));
    }

    @Test
    void changePassword_FailsIfOldPasswordWrong() {
        assertThrows(InvalidCredentialsException.class,
                () -> authService.changePassword("C1", "wrongOld", "newness12"));
    }

    @Test
    void changePassword_FailsIfNewPasswordInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> authService.changePassword("C1", "secret123", "12"));
    }

    @Test
    void changePassword_FailsIfNewPasswordSameAsOld() {
        assertThrows(IllegalArgumentException.class,
                () -> authService.changePassword("C1", "secret123", "secret123"));
    }
}
