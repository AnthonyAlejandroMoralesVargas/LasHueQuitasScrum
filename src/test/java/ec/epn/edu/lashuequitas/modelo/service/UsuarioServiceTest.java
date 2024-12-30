package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.jpa.ResenaJPA;
import ec.epn.edu.lashuequitas.modelo.jpa.UsuarioJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    UsuarioJPA usuarioJPA;
    UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioJPA = Mockito.mock(UsuarioJPA.class);
        usuarioService = new UsuarioService(usuarioJPA);
    }

    @Test
    void given_ValidDetails_when_CreatingUsuario_then_UsuarioIsCreatedSuccessfully() {
        // 1. Arrange
        Usuario usuario = new Usuario("Pepe", "pepe@example.com", BCrypt.hashpw("hashedPassword", BCrypt.gensalt()));
        // Configurar el mock para que devuelva true
        when(usuarioJPA.create(usuario)).thenReturn(true);
        //2. Act
        boolean result = usuarioService.crear(usuario);
        //3. Assert
        System.out.println(result);
        assertTrue(result);
        verify(usuarioJPA, times(1)).create(usuario);
    }

    @Test
    void given_ValidDetails_when_AuthenticatingUsuario_then_UsuarioIsAuthenticatedSuccessfully() {
        // 1. Arrange
        String email = "pepe@example.com";
        String password = "hashedPassword";
        // Configurar el mock para que devuelva un usuario
        when(usuarioJPA.findByEmail(email)).thenReturn(new Usuario("Pepe", "pepe@example", BCrypt.hashpw("hashedPassword", BCrypt.gensalt())));
        // 2. Act
        Usuario result = usuarioService.autenticar(email, password);
        // 3. Assert
        assertNotNull(result);
        verify(usuarioJPA, times(1)).findByEmail(email);
    }

}