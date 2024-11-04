<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ejemplo de JSP</title>
    <script>
        function filtrarPreguntas() {
            document.getElementById("materiaForm").submit();
        }
    </script>
</head>
<body>
<div style="margin-bottom: 20px;">
    <form id="materiaForm" action="${pageContext.request.contextPath}/materiasFormularioServlet" method="get">
        <label for="dropdown">Selecciona una Materia:</label>
        <select id="dropdown" name="codigomateria" onchange="filtrarPreguntas()">
            <c:forEach var="materia" items="${materias}">
                <option value="${materia.codigomateria}" ${materia.codigomateria == codigomateria ? 'selected' : ''}>${materia.nombre}</option>
            </c:forEach>
        </select>
    </form>

    <form action="${pageContext.request.contextPath}/guardarPregunta" method="post">
        <label for="textarea">Pregunta para escala de Likert:</label><br>
        <textarea id="textarea" name="pregunta" rows="4" cols="50"></textarea>
        <input type="hidden" name="codigomateria" value="${codigomateria}"> <!-- Materia seleccionada -->
        <br><br>
        <button type="submit">Guardar Pregunta</button>
    </form>
</div>

<!-- Tabla para mostrar preguntas de la materia seleccionada -->
<div>
    <table border="1">
        <thead>
        <tr>
            <th>Pregunta</th>
            <th>Materia</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="pregunta" items="${preguntas}">
            <tr>
                <td>${pregunta.pregunta}</td>
                <td>${pregunta.materia.nombre}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
