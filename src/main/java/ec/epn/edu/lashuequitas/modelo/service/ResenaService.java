package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.jpa.ResenaJPA;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;

import java.util.List;

public class ResenaService {

    private final ResenaJPA resenaJPA;
    private final ModeradorService moderadorService = new ModeradorService();

    public ResenaService() {
        this.resenaJPA = new ResenaJPA();
    }

    public ResenaService(ResenaJPA resenaJPA) {
        this.resenaJPA = resenaJPA;
    }

    public boolean crear(Resena resena) {
        return resenaJPA.create(resena);
    }

    public List<Resena> listarResenas() {
        return resenaJPA.findAllResenas();
    }

    public Resena buscarResenaPorId(Long id) {
        return resenaJPA.findById(id);
    }

    public String validarContenidoResena(String nombreRestaurante, String descripcion) {
        if (moderadorService.verificarOfensivo(nombreRestaurante) || moderadorService.verificarOfensivo(descripcion)) {
            return "La reseña contiene palabras ofensivas y no se ha publicado";
        }
        return null;
    }

    public String validarLongitudResena(String nombreRestaurante, String descripcion) {
        if (moderadorService.verificarLongitud(nombreRestaurante) || moderadorService.verificarLongitud(descripcion)) {
            return "La reseña excede los 200 caracteres y no se ha publicado.";
        }
        return null;
    }
}
