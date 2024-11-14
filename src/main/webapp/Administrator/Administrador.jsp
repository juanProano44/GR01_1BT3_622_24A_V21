<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/layouts/header.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Bienvenido, Administrador</h2>
    <p>Desde aquí puedes registrar Usuarios Tutores y Administradores.</p>
    <a href="${pageContext.request.contextPath}/ReguistroSistemaServlet">Registrar nuevo usuario</a>
    <a href="${pageContext.request.contextPath}/VerRolesServlet">Ver Roles reguistro</a>
    <a href="${pageContext.request.contextPath}/SolicitudeTutoreServlet">Solicitudes de tutores</a>
</body>
</html>
