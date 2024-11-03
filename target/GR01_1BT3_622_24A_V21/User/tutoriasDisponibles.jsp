<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Tutorías Disponibles</title>
</head>
<body>
<h2>Tutorías Disponibles</h2>

<!-- Tabla para mostrar las tutorías -->
<table border="1">
  <tr>
    <th>ID</th>
    <th>Materia</th>
    <th>Fecha</th>
    <th>Hora Inicio</th>
    <th>Hora Fin</th>
    <th>Tutor</th> <!-- Nueva columna para mostrar el nombre del tutor -->
    <th>Acción</th>
  </tr>
  <c:forEach var="tutoria" items="${tutorias}">
  <tr>
    <td>${tutoria.id}</td>
    <td>${tutoria.materia.nombre}</td>
    <td>${tutoria.fecha}</td>
    <td>${tutoria.horaInicio}</td>
    <td>${tutoria.horaFin}</td>
    <td>${tutoria.tutor.nombre}</td>
    <td>
      <form action="${pageContext.request.contextPath}/AceptarTutoriaServlet" method="post">
        <input type="hidden" name="tutoriaId" value="${tutoria.id}">
        <button type="submit">Solicitar unirce</button>
      </form>
    </td>
  </tr>
</c:forEach>

</table>
</body>
</html>

