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


        when(resenaJPA.create(resena)).thenReturn(true);


        boolean result = resenaService.crear(resena);


        System.out.println(result);
        assertTrue(result);
        verify(resenaJPA, times(1)).create(resena);
    }

    @Test
    void given_ValidId_when_BuscarResenaPorId_then_ReturnsCorrectResena() {

        Long id = 1L;
        Resena expectedResena = new Resena("La Casa del Sabor", "Platos principales",
                "Excelente comida", new Usuario("Pepe", "pepe@example.com", "123pepe"));

        when(resenaJPA.findById(id)).thenReturn(expectedResena);

        Resena actualResena = resenaService.buscarResenaPorId(id);

        assertNotNull(actualResena);
        assertEquals(expectedResena, actualResena);
        verify(resenaJPA, times(1)).findById(id);
    }

    @Test
    void given_InvalidId_when_BuscarResenaPorId_then_ReturnsNull() {

        Long id = 99L;

        when(resenaJPA.findById(id)).thenReturn(null);

        Resena actualResena = resenaService.buscarResenaPorId(id);
        assertNull(actualResena);
        verify(resenaJPA, times(1)).findById(id);
    }

}