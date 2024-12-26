package ec.epn.edu.lashuequitas.controlador;

import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/RegistroController")
public class RegistroController extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public RegistroController() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    private void ruteador(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ruta = (request.getParameter("ruta") == null) ? "registrar" : request.getParameter("ruta");
        switch (ruta) {
            case "registrar":
                this.registrar(request, response);
                break;
            case "guardar":
                this.guardar(request, response);
                break;
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        //2. Hablar con el modelo
        //3. Redirigir a la vista
        response.sendRedirect("vista/Registro.jsp");
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        String username = request.getParameter("txtUsername");
        String email = request.getParameter("txtEmail");
        String HashPassword = BCrypt.hashpw(request.getParameter("txtPassword"), BCrypt.gensalt());

        Usuario usuario = new Usuario(username, email, HashPassword);
        //2. Hablar con el modelo
        UsuarioService usuarioService = new UsuarioService();
        boolean registrado = usuarioService.crear(usuario);
        System.out.println("registrado: " + registrado);
        if (registrado) {
            request.setAttribute("messageReg", "Usuario registrado con éxito.");
            //3. Redirigir a la vista Login
            //request.getRequestDispatcher("vista/Login.jsp").forward(request, response);
            request.getRequestDispatcher("vista/Login.jsp").forward(request, response);
        } else {
            request.setAttribute("messageReg", "Error al registrar el usuario.");
            //3. Redirigir a la vista Registro
            request.getRequestDispatcher("vista/Registro.jsp").forward(request, response);
        }

    }
}
