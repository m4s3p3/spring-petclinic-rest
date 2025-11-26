package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldThrowExceptionWhenRolesAreNull() {
        // 1. GIVEN
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRoles(null); // Forzamos el error

        // 2. WHEN & THEN
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals("User must have at least a role set!", exception.getMessage());
        // Verificamos que NUNCA se llamó al repositorio porque falló antes
        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldAddRolePrefixIfNotPresent() {
        // 1. GIVEN
        User user = new User();
        user.setUsername("adminUser");
        user.setPassword("password");

        // Añadimos un rol SIN el prefijo "ROLE_" para probar la lógica del IF
        user.addRole("ADMIN");

        // 2. WHEN
        userService.saveUser(user);

        // 3. THEN
        // Verificamos que el rol se ha transformado correctamente
        Role processedRole = user.getRoles().iterator().next();
        assertEquals("ROLE_ADMIN", processedRole.getName(), "El servicio debería añadir 'ROLE_' automáticamente");

        // Verificamos que se llamó a guardar
        verify(userRepository).save(user);
    }
}
