<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Responde la Encuesta</title>
</head>
<body>
<h2>Responde la Encuesta</h2>
<form action="${pageContext.request.contextPath}/guardarRespuesta" method="post">
  <c:forEach var="pregunta" items="${preguntas}">
    <div>
      <p>${pregunta.pregunta}</p>
      <input type="hidden" name="idPregunta" value="${pregunta.id}">
      <label><input type="radio" name="calificacion_${pregunta.id}" value="1"> 1</label>
      <label><input type="radio" name="calificacion_${pregunta.id}" value="2"> 2</label>
      <label><input type="radio" name="calificacion_${pregunta.id}" value="3"> 3</label>
      <label><input type="radio" name="calificacion_${pregunta.id}" value="4"> 4</label>
      <label><input type="radio" name="calificacion_${pregunta.id}" value="5"> 5</label>
    </div>
    <hr>
  </c:forEach>

  <!-- Campos ocultos con los nuevos parámetros requeridos -->
  <input type="hidden" name="idSolicitud" value="${idSolicitud}">
  <input type="hidden" name="idAlumno" value="${idAlumno}">
  <input type="hidden" name="idTutor" value="${idTutor}">
  <input type="hidden" name="codigomateria" value="${codigomateria}">

  <button type="submit">Enviar Respuestas</button>
</form>
</body>
</html>
