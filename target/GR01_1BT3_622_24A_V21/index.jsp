<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Sistema Asistencia Virtual FIS</title>
</head>
<body>
<h2>Bienvenido al Sistema de Asistencia Virtual FIS</h2>
<form action="seleccionRol" method="post">
    <button type="submit" name="rol" value="administrator">Ingresar como Admin</button>
    <button type="submit" name="rol" value="usuario">Ingresar como Usuario</button>
    <button type="submit" name="rol" value="tutor">Ingresar como Tutor</button>
</form>
</body>
</html>
