<%--
  Created by IntelliJ IDEA.
  User: joel.piuri
  Date: 18/10/2024
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Bienvenido, Administrador</h2>
    <p>Desde aquí puedes registrar Usuarios Tutores y Administradores.</p>
    <a href="${pageContext.request.contextPath}/ReguistroSistemaServlet">Registrar nuevo usuario</a>
    <a href="${pageContext.request.contextPath}/VerRolesServlet">Ver Roles reguistro</a>
</body>
</html>
