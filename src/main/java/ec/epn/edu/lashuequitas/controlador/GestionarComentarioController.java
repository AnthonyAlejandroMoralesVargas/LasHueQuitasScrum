package ec.epn.edu.lashuequitas.controlador;

import ec.epn.edu.lashuequitas.modelo.entidades.Comentario;
import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import ec.epn.edu.lashuequitas.modelo.service.ComentarioService;
import ec.epn.edu.lashuequitas.modelo.service.ModeradorService;
import ec.epn.edu.lashuequitas.modelo.service.ResenaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/gestionarComentario")
public class GestionarComentarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    private void ruteador(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruta = (request.getParameter("ruta") == null) ? "listar" : request.getParameter("ruta");
        switch (ruta) {
            case "listar":
                this.listar(request, response);
                break;
            case "publicar":
                this.publicar(request, response);
                break;
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Obtener el parámetro idResena desde la URL
        String idResenaParam = request.getParameter("idResena");
        Long idResena;

        try {
            idResena = Long.parseLong(idResenaParam);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Error: ID de reseña no válido o nulo.");
            request.setAttribute("message", "ID de reseña inválido o no especificado.");
            request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
            return;
        }

        // 2. Obtener la reseña desde la base de datos
        ResenaService resenaService = new ResenaService();
        Resena resena = resenaService.buscarResenaPorId(idResena);

        if (resena == null) {
            System.out.println("Error: No se encontró la reseña con ID: " + idResena);
            request.setAttribute("message", "No se encontró la reseña asociada.");
            request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
            return;
        }

        // 3. Pasar la reseña al JSP
        request.setAttribute("resena", resena);
        request.setAttribute("idResena", idResena);

        // 4. Redirigir al JSP para mostrar los comentarios
        request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
    }

    private void publicar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        Usuario usuario = (Usuario) session.getAttribute("user");
        String resenaIdParam = request.getParameter("resenaId");

        if (resenaIdParam == null || resenaIdParam.trim().isEmpty()) {
            System.out.println("Error: ID de reseña vacío o nulo.");
            request.setAttribute("message", "No se especificó la reseña para el comentario.");
            request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
            return;
        }

        Long resenaId;
        try {
            resenaId = Long.parseLong(resenaIdParam);
        } catch (NumberFormatException e) {
            System.out.println("Error: ID de reseña inválido.");
            request.setAttribute("message", "ID de reseña inválido.");
            request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
            return;
        }

        ResenaService resenaService = new ResenaService();
        Resena resena = resenaService.buscarResenaPorId(resenaId);


        String contenido = request.getParameter("contenido");
        if (contenido == null || contenido.trim().isEmpty()) {
            System.out.println("Error: Contenido del comentario vacío.");
            request.setAttribute("message", "El contenido del comentario no puede estar vacío.");
            request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
            return;
        }

        ModeradorService moderadorService = new ModeradorService();

        if (moderadorService.verificarOfensivo(contenido)) {
            request.setAttribute("messageLogin", "El comentario contiene contenido ofensivo.");
            request.setAttribute("resena", resena);
            request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
            return;
        }

        if (!moderadorService.verificarLongitud(contenido)) {
            request.setAttribute("messageLogin", "El contenido excede la longitud máxima permitida.");
            request.setAttribute("resena", resena);
            request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
            return;
        }

        Comentario comentario = new Comentario(contenido, usuario, resena);
        ComentarioService comentarioService = new ComentarioService();
        boolean creado = comentarioService.crear(comentario);

        if (creado) {
            System.out.println("Comentario creado con éxito.");
            request.setAttribute("message", "Comentario publicado con éxito.");
        } else {
            System.out.println("Error al crear el comentario.");
            request.setAttribute("message", "Error al publicar el comentario.");
        }

        // Redirigir de vuelta a la reseña con los comentarios actualizados
        request.setAttribute("resena", resena);

        request.getRequestDispatcher("vista/VerComentarios.jsp").forward(request, response);
    }



}
