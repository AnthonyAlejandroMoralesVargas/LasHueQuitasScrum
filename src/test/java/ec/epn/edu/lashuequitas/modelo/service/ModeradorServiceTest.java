package ec.epn.edu.lashuequitas.modelo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModeradorServiceTest {

    private ModeradorService moderadorService;

    @BeforeEach
    void setUp() {
        moderadorService = new ModeradorService();
    }

    @Test
    void given_ContentWithOffensiveWord_when_Verifying_then_ShouldDetectOffensiveWord() {
        String contenido = "Esa comida fe una completa basura a mi parecer.";
        assertTrue(moderadorService.verificarOfensivo(contenido));
    }

    @Test
    void given_ContentWithoutOffensiveWord_when_Verifying_then_ShouldNotDetectOffensiveWord() {
        String contenido = "La salichipapa que me comi estuvo muy buena la verdad";
        assertFalse(moderadorService.verificarOfensivo(contenido));
    }

    @Test
    void given_ContentExceedingMaxLength_when_VerifyingLength_then_ShouldFailValidation() {
        String contenido = "El hornado de este lugar es genial, no se lo pueden perder".repeat(20);
        assertFalse(moderadorService.verificarLongitud(contenido));
    }

    @Test
    void given_ContentWithinMaxLength_when_VerifyingLength_then_ShouldPassValidation() {
        String contenido = "La salchipapa que me comí estuvo muy buena.";
        assertTrue(moderadorService.verificarLongitud(contenido));
    }

}