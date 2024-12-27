package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.jpa.UsuarioJPA;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {

    public boolean crear(Usuario usuario) {
        UsuarioJPA usuarioJPA = new UsuarioJPA();
        return usuarioJPA.create(usuario);
    }

    public Usuario autenticar(String email, String password) {
        UsuarioJPA usuarioJPA = new UsuarioJPA();
        Usuario usuarioEncontrado = usuarioJPA.findByEmail(email);
        if (usuarioEncontrado != null && BCrypt.checkpw(password, usuarioEncontrado.getPasswordHash())) {
            return usuarioEncontrado;
        }
        return null;
    }
}
