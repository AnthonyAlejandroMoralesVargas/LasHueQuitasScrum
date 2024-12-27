package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.jpa.ResenaJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ResenaServiceTest {
    private ResenaJPA resenaJPA;
    private ResenaService resenaService;

    @BeforeEach
    void setUp() {
        // Mock de ResenaJPA
        resenaJPA = Mockito.mock(ResenaJPA.class);
        // Inyectar el mock en ResenaService
        resenaService = new ResenaService(resenaJPA);
    }


    @Test
    void given_ValidDetails_when_CreatingResena_then_ResenaIsCreatedSuccessfully() {
        // 1. Arrange
        Usuario usuario = new Usuario("Pepe", "pepe@example.com", "hashedPassword");
        Resena resena = new Resena("La Casa del Sabor", "Platos principales",
                "Excelente comida", usuario);

        // Configurar el mock para que devuelva true
        when(resenaJPA.create(resena)).thenReturn(true);

        // 2. Act
        boolean result = resenaService.crear(resena);

        // 3. Assert
        System.out.println(result);
        assertTrue(result);
        verify(resenaJPA, times(1)).create(resena);
    }
}