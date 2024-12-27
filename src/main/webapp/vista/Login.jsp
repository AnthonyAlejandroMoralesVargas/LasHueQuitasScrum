<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión</title>
    <style>
        /* Importar Google Fonts */
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

        /* Estilos básicos */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to right, #FF914D, #FF6D6D);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        .login-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            text-align: center;
            width: 100%;
            max-width: 400px;
        }

        .title {
            font-size: 2.5em;
            color: #FF6D6D;
            font-weight: bold;
            margin-bottom: 5px;
        }

        h2 {
            color: #555;
            font-size: 1.8em;
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            color: #555;
            margin-top: 10px;
            text-align: left;
        }

        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1em;
            box-sizing: border-box;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
            height: 40px;
        }

        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #FF6D6D;
            outline: none;
        }

        .button {
            background-color: #FF6D6D;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            font-size: 1em;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
            width: 100%;
            margin-top: 10px;
        }

        .button:hover {
            background-color: #FF4A4A;
        }

        .register-link {
            margin-top: 20px;
            font-size: 0.9em;
            color: #555;
        }

        .register-link a {
            color: #FF6D6D;
            text-decoration: none;
            font-weight: bold;
        }

        .register-link a:hover {
            text-decoration: underline;
        }

        .error-message {
            color: #FF4A4A;
            margin-top: 10px;
            font-size: 0.9em;
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
<div class="login-container">
    <div class="title">Las Huequitas</div>
    <h2>Iniciar Sesión</h2>
    <form action="${pageContext.request.contextPath}/login?ruta=login" method="POST" autocomplete="off">
        <label for="txtEmail">Correo electrónico:</label>
        <input type="email" id="txtEmail" name="txtEmail" required>

        <label for="txtPassword">Contraseña:</label>
        <input type="password" id="txtPassword" name="txtPassword" required>

        <button type="submit" class="button">Iniciar sesión</button>
    </form>
    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>
    <p class="register-link">¿No tienes una cuenta? <a href="${pageContext.request.contextPath}/RegistroController?ruta=registrar">Registrarse</a></p>
</div
        <!-- Modal -->
<div id="messageModal" class="modal">
    <div class="modal-content">
        <div class="modal-body">${messageReg}</div>
    </div>
</div>
<script>
    // Mostrar el modal si hay un mensaje
    window.onload = function () {
        const message = '${messageReg}';
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
