<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Bienvenido a Las HueQuitas</title>
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
      justify-content: center;
      align-items: center;
      min-height: 100vh;
    }

    .container {
      background-color: #ffffff;
      padding: 40px;
      border-radius: 10px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
      width: 100%;
      max-width: 500px;
      text-align: center;
    }

    h2 {
      color: #FF6D6D;
      font-size: 2.5em;
      font-weight: 700;
      margin-bottom: 10px;
    }

    p {
      font-size: 1.1em;
      color: #555;
      margin-bottom: 20px;
    }

    .nav-links {
      display: flex;
      flex-direction: column;
      gap: 15px;
      margin-top: 20px;
    }

    .nav-links a {
      background-color: #FF6D6D;
      color: white;
      padding: 12px 20px;
      border: none;
      border-radius: 5px;
      font-size: 1em;
      font-weight: bold;
      text-decoration: none;
      transition: background-color 0.3s ease;
    }

    .nav-links a:hover {
      background-color: #FF4A4A;
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
</head>
<body>
<div class="container">
  <h2>Bienvenido a Las HueQuitas</h2>
  <p>¡Explora, comenta y comparte reseñas gastronómicas!</p>

  <div class="nav-links">
    <a href="${pageContext.request.contextPath}/ResenaSv">Ir a Reseñas</a>
    <a href="${pageContext.request.contextPath}/AnuncioSv">Ir a Anuncios</a>
    <a href="${pageContext.request.contextPath}/PromocionSv">Ir a Promociones</a>
    <a href="${pageContext.request.contextPath}/LogoutSv">Cerrar sesión</a>
  </div>
</div>

<!-- Modal -->
<div id="messageModal" class="modal">
  <div class="modal-content">

    <div class="modal-body">${messageLogin}</div>
  </div>
</div>
<script>
  // Mostrar el modal si hay un mensaje
  window.onload = function () {
    const message = '${messageLogin}';
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
