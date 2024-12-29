package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.Comentario;
import ec.epn.edu.lashuequitas.modelo.jpa.ComentarioJPA;

import java.util.List;

public class ComentarioService {

    private final ComentarioJPA comentarioJPA;

    // Constructor por defecto
    public ComentarioService() {
        this.comentarioJPA = new ComentarioJPA();
    }

    // Constructor con inyección de dependencia
    public ComentarioService(ComentarioJPA comentarioJPA) {
        this.comentarioJPA = comentarioJPA;
    }

    // Método para crear un comentario
    public boolean crear(Comentario comentario) {
        return comentarioJPA.create(comentario);
    }


}
