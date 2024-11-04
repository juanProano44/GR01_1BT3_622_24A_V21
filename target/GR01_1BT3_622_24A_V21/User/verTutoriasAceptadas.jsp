<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Solicitudes de Tutoría</title>
</head>
<body>
<h2>Solicitudes de Tutoría</h2>

<!-- Tabla para mostrar las solicitudes -->
<table border="1">
  <tr>
    <th>ID Solicitud</th>
    <th>Materia</th>
    <th>Fecha</th>
    <th>Hora Inicio</th>
    <th>Hora Fin</th>
    <th>Tutor</th> <!-- Mostrar el nombre del tutor -->
    <th>Estado</th> <!-- Mostrar el estado (Pendiente, Aceptada, etc.) -->
    <th>Acción</th> <!-- Nueva columna para el botón -->
  </tr>
  <c:forEach var="solicitud" items="${solicitudes}">
    <tr>
      <td>${solicitud.id}</td>
      <td>${solicitud.tutoria.materia.nombre}</td>
      <td>${solicitud.tutoria.fecha}</td>
      <td>${solicitud.tutoria.horaInicio}</td>
      <td>${solicitud.tutoria.horaFin}</td>
      <td>${solicitud.tutor.nombre}</td>
      <td>${solicitud.estado}</td> <!-- Mostrar el estado de la solicitud -->
      <td>
        <form action="${pageContext.request.contextPath}/formularioUserServlet" method="get">
          <input type="hidden" name="idSolicitud" value="${solicitud.id}">
          <button type="submit">Responder Encuesta</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
