package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.Comentario;
import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.jpa.ComentarioJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComentarioServiceTest {

    private ComentarioJPA comentarioJPA;
    private ComentarioService comentarioService;

    @BeforeEach
    void setUp() {
        // Mock de ComentarioJPA
        comentarioJPA = Mockito.mock(ComentarioJPA.class);
        // Inyectar el mock en ComentarioService
        comentarioService = new ComentarioService(comentarioJPA);
    }

    @Test
    void given_ValidDetails_when_CreatingComentario_then_ComentarioIsCreatedSuccessfully() {
        // 1. Arrange
        Usuario usuario = new Usuario("Dave", "Dave@example.com", "123Dave");
        Resena resena = new Resena("Campero", "Postres", "Deliciosos postres", usuario);
        Comentario comentario = new Comentario("No me gusto", usuario, resena);


        when(comentarioJPA.create(comentario)).thenReturn(true);


        boolean result = comentarioService.crear(comentario);


        System.out.println(result);
        assertTrue(result);
        verify(comentarioJPA, times(1)).create(comentario);
    }

    @Test
    void given_InvalidComentario_when_CreatingComentario_then_ComentarioIsNotCreated() {
        // 1. Arrange
        Usuario usuario = new Usuario("Juan", "juan@example.com", "123Juan");
        Resena resena = new Resena("KFC", "Postres", "No me gustan sus helados", usuario);
        Comentario comentario = new Comentario("", usuario, resena); // Comentario con contenido vacío


        when(comentarioJPA.create(comentario)).thenReturn(false);


        boolean result = comentarioService.crear(comentario);


        System.out.println(result);
        assertFalse(result);
        verify(comentarioJPA, times(1)).create(comentario);
    }

    @Test
    void given_ValidIdResena_when_ListarComentariosPorResena_then_ReturnsComentariosList() {
        // 1. Arrange
        Long idResena = 1L;
        Usuario usuario = new Usuario("Sebas", "Sebas@example.com", "123Sebas");
        Resena resena = new Resena("Campero", "Postres", "Deliciosos postres", usuario);
        Comentario comentario1 = new Comentario("Me gusto", usuario, resena);
        Comentario comentario2 = new Comentario("No me gusto", usuario, resena);
        List<Comentario> comentarios = Arrays.asList(comentario1, comentario2);

        when(comentarioJPA.findAllComentariosByResena(idResena)).thenReturn(comentarios);

        // 2. Act
        List<Comentario> result = comentarioService.listarComentariosPorResena(idResena);

        // 3. Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(comentario1, result.get(0));
        assertEquals(comentario2, result.get(1));
        verify(comentarioJPA, times(1)).findAllComentariosByResena(idResena);
    }

    @Test
    void given_InvalidIdResena_when_ListarComentariosPorResena_then_ReturnsEmptyList() {
        // 1. Arrange
        Long idResena = 999L; // Un ID que no existe
        when(comentarioJPA.findAllComentariosByResena(idResena)).thenReturn(Arrays.asList());

        // 2. Act
        List<Comentario> result = comentarioService.listarComentariosPorResena(idResena);

        // 3. Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(comentarioJPA, times(1)).findAllComentariosByResena(idResena);
    }
}
