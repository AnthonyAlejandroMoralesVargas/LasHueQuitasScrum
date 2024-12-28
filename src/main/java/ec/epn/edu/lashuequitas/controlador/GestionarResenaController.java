package ec.epn.edu.lashuequitas.controlador;


import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.service.ModeradorService;
import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.service.ResenaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

@WebServlet("/gestionarResena")
public class GestionarResenaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GestionarResenaController() {

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

        String ruta = (request.getParameter("ruta") == null) ? "accederForm" : request.getParameter("ruta");
        switch (ruta) {
            case "accederForm":
                this.accederForm(request, response);
                break;
            case "listar":
                this.listar(request, response);
                break;
            case "publicar":
                this.publicar(request, response);
                break;
        }
    }

    private void accederForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        //2. Hablar con el modelo
        //3. Redirigir a la vista
        response.sendRedirect("vista/FormularioResena.jsp");
    }
    private void publicar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("user");

        String nombreRestaurante = request.getParameter("txtRestaurante");
        String tipoComida = request.getParameter("txtTipoComida");
        String descripcion = request.getParameter("txtDescripcion");

        // 2. Verificar si el título o la descripción contienen palabras ofensivas
        ModeradorService moderadorService = new ModeradorService();

        if (moderadorService.verificarOfensivo(nombreRestaurante)) {
            request.setAttribute("messageLogin", "La reseña contiene palabras ofensivas y no se ha publicado.");
            request.getRequestDispatcher("vista/FormularioResena.jsp").forward(request, response);
            return;
        }

        if (moderadorService.verificarOfensivo(descripcion)) {
            request.setAttribute("messageLogin", "La reseña contiene palabras ofensivas y no se ha publicado.");
            request.getRequestDispatcher("vista/FormularioResena.jsp").forward(request, response);
            return;
        }

        if (!moderadorService.verificarLongitud(nombreRestaurante)) {
            request.setAttribute("messageLogin", "El nombre del restaurante excede la longitud máxima permitida.");
            request.getRequestDispatcher("vista/FormularioResena.jsp").forward(request, response);
            return;
        }

        if (!moderadorService.verificarLongitud(descripcion)) {
            request.setAttribute("messageLogin", "La descripción excede la longitud máxima permitida.");
            request.getRequestDispatcher("vista/FormularioResena.jsp").forward(request, response);
            return;
        }

        Resena resena = new Resena(nombreRestaurante, tipoComida, descripcion, usuario);

        //3. Hablar con el modelo
        ResenaService resenaService = new ResenaService();
        boolean publicado = resenaService.crear(resena);

        //4. Redirigir a la vista
        if (publicado) {
            request.setAttribute("messageLogin", "Reseña publicada con éxito.");
            request.getRequestDispatcher("vista/Home.jsp").forward(request, response);
        } else {
            request.setAttribute("messageLogin", "Error al publicar la reseña");
            request.getRequestDispatcher("vista/Home.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        //2. Hablar con el modelo
        //3. Redirigir a la vista
        // para pruebas redirige a Home.jsp, pero debería redirigir a la vista de listar reseñas
        response.sendRedirect("vista/Home.jsp");
        // response.sendRedirect("vista/ListarResenas.jsp");
    }
}
