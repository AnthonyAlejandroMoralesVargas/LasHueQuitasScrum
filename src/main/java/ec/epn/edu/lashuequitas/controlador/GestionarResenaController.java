package ec.epn.edu.lashuequitas.controlador;

import ec.epn.edu.lashuequitas.modelo.entidades.ImagenResena;
import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.service.ResenaService;
import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/gestionarResena")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class GestionarResenaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ResenaService resenaService;

    public GestionarResenaController() {
        this.resenaService = new ResenaService(); // Inicializa el servicio de reseñas
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
        response.sendRedirect("vista/FormularioResena.jsp");
    }

    private void publicar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Obtener parametros de la solicitud
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("user");

        String nombreRestaurante = request.getParameter("txtRestaurante");
        String tipoComida = request.getParameter("txtTipoComida");
        String descripcion = request.getParameter("txtDescripcion");
        List<byte[]> imagenesBytes = new ArrayList<>();

        // Procesar las partes de la solicitud
        for (Part part : request.getParts()) {
            if (part.getName().equals("imagenes") && part.getSize() > 0) {
                // Leer la imagen como byte[]
                try (InputStream inputStream = part.getInputStream()) {
                    imagenesBytes.add(inputStream.readAllBytes());
                }
            }
        }
        // 2. Hablar con el modelo
        ResenaService resenaService = new ResenaService();

        String isOffensive = resenaService.validarContenidoResena(nombreRestaurante, descripcion);
        String isTooLong = resenaService.validarLongitudResena(nombreRestaurante, descripcion);

        if (isOffensive != null || isTooLong != null) {
            request.setAttribute("messageLogin", isOffensive != null ? isOffensive : isTooLong);
            request.getRequestDispatcher("vista/FormularioResena.jsp").forward(request, response);
            return;
        }

        // Crear la reseña
        Resena resena = new Resena(nombreRestaurante, tipoComida, descripcion, usuario);

        // Agregar imágenes a la reseña
        for (byte[] imagen : imagenesBytes) {
            ImagenResena imagenResena = new ImagenResena();
            imagenResena.setDatosImagen(imagen);
            imagenResena.setResena(resena);
            resena.getImagenes().add(imagenResena);
        }

        // Guardar la reseña
        boolean publicado = resenaService.crear(resena);

        if (publicado) {
            request.setAttribute("messagePublicacion", "Reseña publicada");
            request.getRequestDispatcher("/gestionarResena?ruta=listar").forward(request, response);
        } else {
            request.setAttribute("messageLogin", "Error al publicar la reseña");
            request.getRequestDispatcher("vista/FormularioResena.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messagePublicacion = request.getParameter("messagePublicacion");
        List<Resena> resenas = resenaService.listarResenasConImagenes();

        for (Resena resena : resenas) {
            if (resena.getImagenes() == null) {
                resena.setImagenes(new ArrayList<>());
            }
        }

        if (resenas.isEmpty()) {
            request.setAttribute("messageEmpty", "No hay reseñas disponibles en este momento");
        }

        if (messagePublicacion != null) {
            request.setAttribute("messagePublicacion", messagePublicacion);
        }

        request.setAttribute("resenas", resenas);
        request.getRequestDispatcher("vista/VerResenas.jsp").forward(request, response);
    }
}
