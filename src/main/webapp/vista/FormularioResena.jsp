<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Nueva Reseña</title>
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

        .resena-container {
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

        input[type="text"],
        select,
        textarea {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1em;
            box-sizing: border-box;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        textarea {
            resize: none;
            height: 80px;
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

        .modal-body {
            font-size: 1em;
            color: #333;
        }
        /*Contador de caracteres*/
        .char-count {
            font-size: 0.9em;
            color: #666;
            margin-bottom: 15px;
        }
    </style>
</head>
<body> <!-- Cambiar el Controller -->
<div class="resena-container">
    <div class="title">Nueva Reseña</div>
    <h2>Crear una Reseña</h2>
    <c:if test="${not empty messageLogin}">
        <div class="error-message">
                ${messageLogin}
        </div>
    </c:if>
    <form action="${pageContext.request.contextPath}/gestionarResena?ruta=publicar"
          method="POST"
          enctype="multipart/form-data"
          autocomplete="off">
        <label for="restaurante">Nombre del restaurante:</label>
        <input type="text" id="restaurante" name="txtRestaurante" required oninput="updateNombreCount(this)" >
        <div class="char-count" id="nombreCount">0 caracteres</div>
        <label for="tipoComida">Tipo de comida:</label>
        <select id="tipoComida" name="txtTipoComida" required>
            <option value="Platos principales">Platos principales</option>
            <option value="Sopas/Caldos">Sopas/Caldos</option>
            <option value="Postres">Postres</option>
            <option value="Bebidas">Bebidas</option>
        </select>

        <label for="descripcion">Descripción:</label>
        <textarea id="descripcion" name="txtDescripcion" required oninput="updateCharCount(this)"></textarea>
        <div class="char-count" id="charCount">0 caracteres</div>
        <label for="imagen">Subir imágenes:</label>
        <input type="file" id="imagen" name="imagenes" accept="image/*" multiple>

        <button type="submit" class="button">Publicar Reseña</button>
    </form>
    <p class="register-link">
        <a href="${pageContext.request.contextPath}/gestionarResena?ruta=listar">Volver a lista de reseñas</a>
    </p>
</div>
<!-- Modal -->
<div id="messageModal" class="modal">
    <div class="modal-content">
        <div class="modal-body">${messageReg}</div>
    </div>
</div>
<script>
    function showMessageModal(mensaje) {
        const modal = document.getElementById('messageModal');
        const modalBody = modal.querySelector('.modal-body');

        // Inyectar el mensaje en la parte del modal
        modalBody.textContent = mensaje;

        // Mostrar el modal
        modal.style.display = 'flex';

        // (Opcional) Cerrar el modal automáticamente después de 5 segundos:
        setTimeout(() => {
            modal.style.display = 'none';
        }, 5000);
    }

    window.onload = function() {
        const serverMessage = '${messageReg}';
        if (serverMessage.trim()) {
            showMessageModal(serverMessage);
        }
    };

    (function() {
        const MAX_FILE_SIZE_MB = 1;
        const inputImagen = document.getElementById('imagen');

        inputImagen.addEventListener('change', function() {
            const maxSizeInBytes = MAX_FILE_SIZE_MB * 1024 * 1024;
            const files = inputImagen.files;

            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                if (file.size > maxSizeInBytes) {
                    // Observa cómo concatenamos:
                    showMessageModal(
                        "El archivo \"" + file.name + "\" supera el máximo de " + MAX_FILE_SIZE_MB + "MB."
                    );

                    // Limpiamos el input
                    inputImagen.value = "";
                    break;
                }
            }
        });
    })();

    function updateCharCount(textarea) {
        const count = textarea.value.length;
        document.getElementById('charCount').innerText = count + ' / 200 caracteres (máximo)';
    }
    function updateNombreCount(input){
        const count = input.value.length;
        document.getElementById('nombreCount').innerText = count + ' / 200 caracteres (máximo)';
    }
</script>
</body>
</html>