package ec.epn.edu.lashuequitas.controlador;

import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginController() {

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

        String ruta = (request.getParameter("ruta") == null) ? "ingresar" : request.getParameter("ruta");
            switch (ruta) {
                case "login":
                    this.login(request, response);
                    break;
                case "logout":
                    this.logout(request, response);
                    break;
                case "ingresar":
                    this.ingresar(request, response);
                    break;
                case "accederHome":
                    this.accederHome(request, response);
                    break;
            }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        //2. Hablar con el
        UsuarioService usuarioService = new UsuarioService();
        Usuario resultado = usuarioService.autenticar(email, password);
        //3. Redirigir a la vista
        if (resultado == null){
            request.setAttribute("messageReg", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("vista/Login.jsp").forward(request, response);
        }else {
            HttpSession session = request.getSession();
            session.setAttribute("user", resultado);
            request.setAttribute("messageLogin", "Bienvenido " + resultado.getUsername());
            request.getRequestDispatcher("vista/Home.jsp").forward(request, response);
            //response.sendRedirect("vista/Home.jsp");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        //2. Hablar con el modelo
        //3. Redirigir a la vista
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.setAttribute("messageReg", "Sesión cerrada con éxito");
        request.getRequestDispatcher("vista/Login.jsp").forward(request, response);
    }

    private void ingresar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        String messageRegister = request.getParameter("messageReg");
        if (messageRegister != null) {
            request.getRequestDispatcher("vista/Login.jsp").forward(request, response);
        }else {
            response.sendRedirect("vista/Login.jsp");
        }
        //2. Hablar con el modelo
        //3. Redirigir a la vista

    }

    private void accederHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        //2. Hablar con el modelo
        //3. Redirigir a la vista
        response.sendRedirect("vista/Home.jsp");
    }
}
