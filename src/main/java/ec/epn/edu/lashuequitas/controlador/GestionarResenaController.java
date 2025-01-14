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
import java.util.List;

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

        // 2. Hablar con el modelo
        ResenaService resenaService = new ResenaService();

        String isOffensive = resenaService.validarContenidoResena(nombreRestaurante, descripcion);
        String isTooLong = resenaService.validarLongitudResena(nombreRestaurante, descripcion);

        if (isOffensive != null || isTooLong != null) {
            request.setAttribute("messageLogin", isOffensive != null ? isOffensive : isTooLong);
            request.getRequestDispatcher("vista/FormularioResena.jsp").forward(request, response);
            return;
        }

        Resena resena = new Resena(nombreRestaurante, tipoComida, descripcion, usuario);

        boolean publicado = resenaService.crear(resena);

        //3. Redirigir a la vista
        if (publicado) {
            // Establecer el mensaje en el atributo de la solicitud
            request.setAttribute("messagePublicacion", "Reseña publicada");
            request.getRequestDispatcher("/gestionarResena?ruta=listar").forward(request, response);

        } else {
            // Mostrar error y redirigir al formulario
            request.setAttribute("messageLogin", "Error al publicar la reseña");
            request.getRequestDispatcher("vista/FormularioResena.jsp").forward(request, response);
        }

    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Crear instancia del servicio
        ResenaService resenaService = new ResenaService();
        String messagePublicacion = request.getParameter("messagePublicacion");

        // Obtener la lista de reseñas
        List<Resena> resenas = resenaService.listarResenas();

        // Verificar si la lista de reseñas está vacía
        if (resenas.isEmpty()) {
            request.setAttribute("messageEmpty", "No hay reseñas disponibles en este momento");
        }

        if (messagePublicacion != null) {
            request.setAttribute("messagePublicacion", messagePublicacion);
        }


        // Pasar la lista como atributo al JSP
        request.setAttribute("resenas", resenas);


        // Redirigir al JSP VerResenas.jsp
        request.getRequestDispatcher("vista/VerResenas.jsp").forward(request, response);
    }



}
