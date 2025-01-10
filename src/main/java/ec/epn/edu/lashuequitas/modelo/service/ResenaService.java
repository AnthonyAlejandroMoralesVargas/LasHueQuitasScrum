package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.ImagenResena;
import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.jpa.ResenaJPA;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResenaService {

    private final ResenaJPA resenaJPA;
    private final ModeradorService moderadorService = new ModeradorService();

    // Constructor por defecto
    public ResenaService() {
        this.resenaJPA = new ResenaJPA();
    }

    // Constructor con ResenaJPA inyectado
    public ResenaService(ResenaJPA resenaJPA) {
        this.resenaJPA = resenaJPA;
    }

    // Método para crear una nueva reseña
    public boolean crear(Resena resena) {
        return resenaJPA.create(resena);
    }

    // Listar todas las reseñas (sin imágenes cargadas)
    public List<Resena> listarResenas() {
        return resenaJPA.findAllResenas();
    }

    // Listar todas las reseñas con imágenes cargadas (usando JOIN FETCH)
    public List<Resena> listarResenasConImagenes() {

        return resenaJPA.findAllResenasConImagenes();
    }

    // Buscar reseña por ID
    public Resena buscarResenaPorId(Long id) {
        return resenaJPA.findById(id);
    }

    // Validar contenido de la reseña
    public String validarContenidoResena(String nombreRestaurante, String descripcion) {
        if (moderadorService.verificarOfensivo(nombreRestaurante) || moderadorService.verificarOfensivo(descripcion)) {
            return "La reseña contiene palabras ofensivas y no se ha publicado";
        }
        return null;
    }

    // Validar longitud de la reseña
    public String validarLongitudResena(String nombreRestaurante, String descripcion) {
        if (moderadorService.verificarLongitud(nombreRestaurante) || moderadorService.verificarLongitud(descripcion)) {
            return "La reseña excede los 200 caracteres y no se ha publicado.";
        }
        return null;
    }

    // Agregar una imagen a una reseña
    /*
    public void agregarImagenAResena(Resena resena, InputStream imagenStream) throws IOException {
        byte[] datosImagen = imagenStream.readAllBytes();
        ImagenResena imagenResena = new ImagenResena();
        imagenResena.setDatosImagen(datosImagen);
        imagenResena.setResena(resena);
        resena.getImagenes().add(imagenResena);
    }

     */
}
