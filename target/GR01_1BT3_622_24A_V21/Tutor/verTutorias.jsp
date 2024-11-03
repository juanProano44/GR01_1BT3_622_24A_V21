<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
  <title>Tutorías Creadas y Solicitudes</title>
</head>
<body>
<h2>Tutorías Creadas</h2>

<!-- Tabla para mostrar las tutorías creadas -->
<table border="1">
  <tr>
    <th>ID</th>
    <th>Materia</th>
    <th>Fecha</th>
  </tr>
  <c:forEach var="tutoria" items="${tutorias}">
    <tr>
      <td>${tutoria.id}</td>
      <td>${tutoria.materia.nombre}</td> <!-- Mostrar el nombre de la materia -->
      <td>
        <!-- Usar fmt:formatDate para formatear la fecha y mostrar solo YYYY-MM-DD -->
        <fmt:formatDate value="${tutoria.fecha}" pattern="dd-MM-yyyy" />
      </td>
    </tr>
  </c:forEach>
</table>

<h2>Solicitudes Recibidas</h2>

<!-- Tabla para mostrar las solicitudes de estudiantes -->
<table border="1">
  <tr>
    <th>ID Solicitud</th>
    <th>Materia</th> <!-- Mostrar el nombre de la materia en lugar del ID de la tutoría -->
    <th>Alumno</th>
    <th>Estado</th>
    <th>Acciones</th> <!-- Nueva columna para los botones de acción -->
  </tr>
  <c:forEach var="solicitud" items="${solicitudes}">
    <tr>
      <td>${solicitud.id}</td>
      <td>${solicitud.tutoria.materia.nombre}</td> <!-- Mostrar el nombre de la materia -->
      <td>${solicitud.alumno.nombre}</td> <!-- Mostrar nombre y apellido del alumno -->
      <td>${solicitud.estado}</td>
      <td>
        <!-- Botón para aceptar la solicitud -->
        <form action="${pageContext.request.contextPath}/CambiarEstadoSolicitudServlet" method="post" style="display:inline;">
          <input type="hidden" name="solicitudId" value="${solicitud.id}">
          <input type="hidden" name="nuevoEstado" value="Aceptada">
          <button type="submit">Aceptar</button>
        </form>
        <!-- Botón para rechazar la solicitud -->
        <form action="${pageContext.request.contextPath}/CambiarEstadoSolicitudServlet" method="post" style="display:inline;">
          <input type="hidden" name="solicitudId" value="${solicitud.id}">
          <input type="hidden" name="nuevoEstado" value="Rechazada">
          <button type="submit">Rechazar</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
