package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.jpa.UsuarioJPA;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {

    private final UsuarioJPA usuarioJPA;

    public UsuarioService() {
        this.usuarioJPA = new UsuarioJPA();
    }

    public UsuarioService(UsuarioJPA usuarioJPA) {
        this.usuarioJPA = usuarioJPA;
    }


    public boolean crear(Usuario usuario) {
        return usuarioJPA.create(usuario);
    }

    public Usuario autenticar(String email, String password) {
        Usuario usuarioEncontrado = usuarioJPA.findByEmail(email);
        if (usuarioEncontrado != null && BCrypt.checkpw(password, usuarioEncontrado.getPasswordHash())) {
            return usuarioEncontrado;
        }
        return null;
    }
}
