package ec.epn.edu.lashuequitas.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/gestionarComentario")
public class GestionarComentarioController extends HttpServlet {

    public GestionarComentarioController() {
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
            case "listar":
                this.listar(request, response);
                break;
            case "publicar":
                this.publicar(request, response);
                break;
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros
        // Incluir la logica para obtener el IdResena enviado en el request
        //2. Hablar con el modelo
        // Incluir la logica para obtener los comentarios de la resena por el IdResena
        //3. Redirigir a la vista
        // Modificar la redireccion para que se envie la lista de resenas obtenidas
        response.sendRedirect("vista/VerComentarios.jsp");
    }

    private void publicar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. Obtener parámetros

        //2. Hablar con el modelo

        //3. Redirigir a la vista

    }
}
