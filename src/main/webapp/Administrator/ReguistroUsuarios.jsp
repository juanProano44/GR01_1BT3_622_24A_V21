<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>Title</title>
    <script type="text/javascript">
      function toggleMateria() {
        var rol = document.getElementById("rol").value;
        var materiaField = document.getElementById("materiaField");
        if (rol === "2") { // Asumiendo que el rol con id 2 es 'Tutor'
          materiaField.style.display = "block";
        } else {
          materiaField.style.display = "none";
        }
      }
    </script>
  </head>
  <body>
    <h2>Reguistro Estudiante</h2>
    <!-- Mostrar el mensaje de error si existe -->
    <c:if test="${not empty errorMessage}">
      <p style="color: red;">${errorMessage}</p>
    </c:if>
    <form action="${pageContext.request.contextPath}/ReguistroSistemaServlet" method="post">
      <label for="nombre">Nombre:</label>
      <input type="text" id="nombre" name="nombre" required><br>

      <label for="apellido">Apellido:</label>
      <input type="text" id="apellido" name="apellido" required><br>

      <label for="correo">Correo istitucional:</label>
      <input type="text" id="correo" name="correo" required><br>

      <label for="rol">Rol:</label>
      <select id="rol" name="rol" onchange="toggleMateria()" required>
        <option value="">Seleccionar Rol</option>
        <c:forEach var="rol" items="${rols}">
          <option value="${rol.id}">${rol.tipo}</option>
        </c:forEach>
      </select><br>

      <!-- Sección de materias, inicialmente oculta -->
      <div id="materiaField" style="display:none;">
        <h3>Selecciona Materias</h3>
        <table border="1">
          <thead>
          <tr>
            <th>Seleccionar</th>
            <th>Código de Materia</th>
            <th>Nombre de Materia</th>
            <th>Descripción</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="materia" items="${materias}">
            <tr>
              <td>
                <input type="checkbox" name="materiasSeleccionadas" value="${materia.codigomateria}">
              </td>
              <td>${materia.codigomateria}</td>
              <td>${materia.nombre}</td>
              <td>${materia.descripcion}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table><br>
      </div>

      <input type="submit" value="Registrar">
    </form>
  </body>
</html>
