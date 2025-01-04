<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Reseñas</title>
  <style>
    /* Importar Google Fonts */
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

    body {
      font-family: 'Roboto', sans-serif;
      background: linear-gradient(to right, #FF914D, #FF6D6D); /* Fondo degradado */
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
      color: #FF6D6D; /* Color de título */
      font-size: 3.0em;
      font-weight: 700;
      margin: 0;
      text-align: center; /* Centrar el texto */
    }

    .button {
      display: inline-flex;
      align-items: center;
      background-color: #FF6D6D; /* Color de fondo del botón principal */
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
      background-color: #FF4A4A; /* Color al hacer hover */
    }

    .button-secondary {
      background-color: #34495e; /* Color del botón secundario */
      margin-bottom: 10px;
    }

    .button-secondary:hover {
      background-color: #2c3e50; /* Hover para botón secundario */
    }

    .review {
      border: 1px solid #ddd;
      padding: 15px;
      border-radius: 8px;
      margin-bottom: 15px;
      text-align: left;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .review h3 {
      color: #FF6D6D; /* Color del título de la reseña */
      font-size: 1.5em;
      margin: 0 0 5px 0;
    }

    .review p {
      font-size: 1.2em;
      margin: 5px 0;
      color: #555;
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

    .modal-body {
      font-size: 1em;
      color: #333;
    }
  </style>
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
          <h1 style="display: none;">${resena.id}</h1>
          <h3>${resena.restaurante} - ${resena.tipoComida}</h3>
          <p>${resena.descripcion}</p>
          <p style="font-size: 0.9em; color: #666;">
            Creado por: ${resena.usuario.username} el ${resena.fechaCreacion}
          </p>
          <button class="button button-secondary" onclick="window.location.href='${pageContext.request.contextPath}/gestionarComentario?ruta=listar&idResena=${resena.id}'">
            Ver comentarios
          </button>
        </div>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <div class="success-message" style="color: #FF6D6D; font-weight: bold; margin-bottom: 15px;">
          ${messageEmpty}
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
