<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
  <title>Registro Usuario</title>
  <script type="text/javascript">
    function toggleTutorFields() {
      var rol = document.getElementById("rol").value;
      var tutorFields = document.getElementById("tutorFields");
      var archivoCertificacion = document.getElementById("archivoCertificacion");

      if (rol === "2") { // Rol de Tutor
        tutorFields.style.display = "block";
        archivoCertificacion.required = true; // Requerir archivo para Tutor
      } else {
        tutorFields.style.display = "none";
        archivoCertificacion.required = false; // No requerir archivo para Estudiante
      }
    }

    function validarFormulario() {
      var nombre = document.getElementById("nombre").value;
      var apellido = document.getElementById("apellido").value;
      var correo = document.getElementById("correo").value;

      // Validación de nombre y apellido (solo letras y espacios)
      var nombreApellidoRegex = /^[a-zA-Z\s]+$/;
      if (!nombreApellidoRegex.test(nombre)) {
        alert("El nombre solo debe contener letras y espacios.");
        return false;
      }
      if (!nombreApellidoRegex.test(apellido)) {
        alert("El apellido solo debe contener letras y espacios.");
        return false;
      }

      // Validación del correo institucional (debe terminar en @epn.edu.ec)
      var correoRegex = /^[a-zA-Z0-9._%+-]+@epn\.edu\.ec$/;
      if (!correoRegex.test(correo)) {
        alert("El correo debe terminar en @epn.edu.ec.");
        return false;
      }

      return true;
    }
  </script>
</head>
<body>
<h2>Registro Usuario</h2>
<!-- Mostrar el mensaje de error si existe -->
<c:if test="${not empty errorMessage}">
  <p style="color: red;">${errorMessage}</p>
</c:if>
<form action="${pageContext.request.contextPath}/RegistroUsuarioServlet" method="post" enctype="multipart/form-data" onsubmit="return validarFormulario()">
  <label for="nombre">Nombre:</label>
  <input type="text" id="nombre" name="nombre" required><br>

  <label for="apellido">Apellido:</label>
  <input type="text" id="apellido" name="apellido" required><br>

  <label for="correo">Correo Institucional:</label>
  <input type="email" id="correo" name="correo" required><br>

  <label for="rol">Rol:</label>
  <select id="rol" name="rol" onchange="toggleTutorFields()" required>
    <option value="">Seleccionar Rol</option>
    <option value="1">Estudiante</option>
    <option value="2">Tutor</option>
  </select><br>

  <label for="nombreUsuario">Nombre de Usuario:</label>
  <input type="text" id="nombreUsuario" name="nombreUsuario" required><br>

  <label for="contrasena">Contraseña:</label>
  <input type="password" id="contrasena" name="contrasena" required><br>

  <!-- Sección para Tutor, se muestra si selecciona "Tutor" -->
  <div id="tutorFields" style="display:none;">
    <h3>Registro para Tutor</h3>
    <label for="archivoCertificacion">Subir Certificación (PDF):</label>
    <input type="file" id="archivoCertificacion" name="archivoCertificacion" accept="application/pdf"><br><br>
  </div>

  <input type="submit" value="Registrar">
</form>
</body>
</html>
