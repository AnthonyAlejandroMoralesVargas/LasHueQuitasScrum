<%@ page import="java.util.Base64" %>
<%@ page import="ec.epn.edu.lashuequitas.modelo.entidades.ImagenResena" %>
<%@ page import="ec.epn.edu.lashuequitas.modelo.entidades.Resena" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Reseñas</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

    body {
      font-family: 'Roboto', sans-serif;
      background: linear-gradient(to right, #FF914D, #FF6D6D);
      color: #333;
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .container {
      width: 90%;
      max-width: 800px;
      margin: 20px auto;
      background-color: #ffffff;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      padding: 20px;
    }

    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }

    h2 {
      color: #FF6D6D;
      font-size: 3.0em;
      font-weight: 700;
      margin: 0;
      text-align: center;
    }

    .button {
      display: inline-flex;
      align-items: center;
      background-color: #FF6D6D;
      color: white;
      padding: 12px 20px;
      font-size: 1em;
      border-radius: 8px;
      text-decoration: none;
      font-weight: 500;
      transition: background-color 0.3s ease;
      border: none;
      cursor: pointer;
    }

    .button:hover {
      background-color: #FF4A4A;
    }

    .button-secondary {
      background-color: #34495e;
      margin-bottom: 10px;
    }

    .button-secondary:hover {
      background-color: #2c3e50;
    }

    .review {
      border: 1px solid #ddd;
      padding: 15px;
      border-radius: 8px;
      margin-bottom: 15px;
      text-align: left;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
    }

    .review h3 {
      color: #FF6D6D;
      font-size: 1.5em;
      margin: 0 0 5px 0;
    }

    .review p {
      font-size: 1.2em;
      margin: 5px 0;
      color: #555;
    }

    .review .text-content {
      flex: 1;
      margin-right: 15px;
    }

    .review .carousel {
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      width: 300px;
      height: 300px;
      position: relative;
      overflow: hidden;
    }

    .carousel img {
      width: 300px;
      height: 300px;
      object-fit: cover;
      border-radius: 8px;
      margin-bottom: 10px;
      cursor: pointer;
      transition: transform 0.3s ease;
    }

    .carousel img:hover {
      transform: scale(1.05);
    }

    .carousel-controls {
      position: absolute;
      top: 50%;
      width: 100%;
      display: flex;
      justify-content: space-between;
      transform: translateY(-50%);
    }

    .carousel-button {
      background-color: rgba(0, 0, 0, 0.5);
      color: white;
      border: none;
      padding: 5px 10px;
      border-radius: 50%;
      cursor: pointer;
      display: none; /* Ocultas por defecto */
    }

    .carousel-button:hover {
      background-color: rgba(0, 0, 0, 0.7);
    }

    .comment-button {
      background-color: #34495e;
      color: white;
      padding: 8px 15px;
      font-size: 0.9em;
      border-radius: 5px;
      border: none;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .comment-button:hover {
      background-color: #2c3e50;
    }
    /* Estilos del modal */
    .modal {
      display: none; /* Oculto por defecto */
      position: fixed;
      z-index: 1000;
      left: 0;
      top: 10px; /* Modal en la parte superior */
      width: 100%;
      justify-content: center;
      align-items: flex-start; /* Alinear en la parte superior */
    }

    .modal-content {
      background-color: #fff;
      padding: 20px;
      border-radius: 10px;
      text-align: center;
      width: 90%;
      max-width: 400px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
    }

    .modal-header {
      font-size: 1.5em;
      margin-bottom: 10px;
      color: #FF6D6D;
      font-weight: bold;
    }

    .modal-body {
      font-size: 1em;
      color: #333;
    }
  </style>
  <script>
    function showNextImage(carouselId) {
      const carousel = document.getElementById(carouselId);
      const images = carousel.querySelectorAll('img');
      const currentIndex = parseInt(carousel.getAttribute('data-current-index')) || 0;
      images[currentIndex].style.display = 'none';
      const nextIndex = (currentIndex + 1) % images.length;
      images[nextIndex].style.display = 'block';
      carousel.setAttribute('data-current-index', nextIndex);
    }

    function showPrevImage(carouselId) {
      const carousel = document.getElementById(carouselId);
      const images = carousel.querySelectorAll('img');
      const currentIndex = parseInt(carousel.getAttribute('data-current-index')) || 0;
      images[currentIndex].style.display = 'none';
      const prevIndex = (currentIndex - 1 + images.length) % images.length;
      images[prevIndex].style.display = 'block';
      carousel.setAttribute('data-current-index', prevIndex);
    }

    function initializeCarousels() {
      const carousels = document.querySelectorAll('.carousel');
      carousels.forEach(carousel => {
        const images = carousel.querySelectorAll('img');
        const controls = carousel.querySelectorAll('.carousel-button');
        if (images.length > 1) {
          controls.forEach(control => control.style.display = 'block');
        }
        if (images.length > 0) {
          images.forEach((img, index) => {
            img.style.display = index === 0 ? 'block' : 'none';
          });
          carousel.setAttribute('data-current-index', 0);
        }
      });
    }

    window.onload = initializeCarousels;
  </script>
</head>
<body>
<div class="container">
  <div class="header">
    <h2>Reseñas</h2>

    <!-- Botón para crear nueva reseña -->
    <form action="${pageContext.request.contextPath}/gestionarResena?ruta=accederForm" method="get" style="display: inline;">
      <button type="submit" class="button">
        <span class="icon-plus">+ </span> Nueva Reseña
      </button>
    </form>
  </div>

  <!-- Botón para regresar a Home -->
  <a href="${pageContext.request.contextPath}/vista/Home.jsp" class="button button-secondary">Regresar a Home</a>

  <!-- Mostrar lista de reseñas -->
  <c:choose>
    <c:when test="${not empty resenas}">
      <c:forEach var="resena" items="${resenas}">
        <div class="review">
          <div class="text-content">
            <h3>${resena.restaurante} - ${resena.tipoComida}</h3>
            <p>${resena.descripcion}</p>
            <p style="font-size: 0.9em; color: #666;">
              Creado por: ${resena.usuario.username} el ${resena.fechaCreacion}
            </p>
            <!-- Botón para ver comentarios -->
            <form action="${pageContext.request.contextPath}/gestionarComentario?ruta=listar" method="get">
              <input type="hidden" name="idResena" value="${resena.id}" />
              <button type="submit" class="comment-button">Ver Comentarios</button>
            </form>
          </div>
          <div class="carousel" id="carousel-${resena.id}">
            <div class="carousel-controls">
              <button class="carousel-button" onclick="showPrevImage('carousel-${resena.id}')">&#9664;</button>
              <button class="carousel-button" onclick="showNextImage('carousel-${resena.id}')">&#9654;</button>
            </div>
            <c:if test="${not empty resena.imagenes}">
              <c:forEach var="imagen" items="${resena.imagenes}">
                <img src="data:image/jpeg;base64,${imagen.base64DatosImagen}" alt="Imagen de la reseña">
              </c:forEach>
            </c:if>
            <c:if test="${empty resena.imagenes}">
              <div style="width: 300px; height: 300px; background-color: #f0f0f0; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center;">
                <p style="text-align: center; color: #999;">Sin imagen</p>
              </div>
            </c:if>
          </div>
        </div>
      </c:forEach>
    </c:when>

    <c:otherwise>
      <div class="success-message" style="color: #FF6D6D; font-weight: bold; margin-bottom: 15px;">
        No hay reseñas disponibles en este momento.
      </div>
    </c:otherwise>
  </c:choose>

</div>
<!-- Modal -->
<div id="messageModal" class="modal">
  <div class="modal-content">

    <div class="modal-body">${messagePublicacion}</div>
  </div>
</div>
<script>
  // Mostrar el modal si hay un mensaje
  window.onload = function () {
    const message = '${messagePublicacion}';
    if (message.trim()) {
      const modal = document.getElementById('messageModal');
      modal.style.display = 'flex';
      // Ocultar el modal automáticamente después de 5 segundos
      setTimeout(function () {
        modal.style.display = 'none';
      }, 5000);
    }
  };
</script>
</body>
</html>
