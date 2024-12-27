package ec.epn.edu.lashuequitas.controlador;


import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
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


        Resena resena = new Resena(nombreRestaurante, tipoComida, descripcion, usuario);
        //2. Hablar con el modelo
        ResenaService resenaService = new ResenaService();
        boolean publicado = resenaService.crear(resena);
        //3. Redirigir a la vista
        if (publicado) {
            request.setAttribute("messageLogin", "Reseña publicada con éxito.");
            request.getRequestDispatcher("vista/Home.jsp").forward(request, response);
        }else {
            request.setAttribute("messageLogin", "Error al publicar la reseña");
            request.getRequestDispatcher("vista/Home.jsp").forward(request, response);
            //response.sendRedirect("vista/Home.jsp");
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
