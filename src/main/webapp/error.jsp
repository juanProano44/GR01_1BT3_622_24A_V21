<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .error-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .error-container h2 {
            color: #d9534f;
        }
        .error-container p {
            font-size: 16px;
        }
        .error-container button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .error-container button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="error-container">
    <h2>Error</h2>
    <!-- Mostrar el mensaje de error pasado como parámetro -->
    <p>${param.mensaje}</p>
    <!-- Botón para regresar a la página anterior -->
    <button onclick="history.back()">Regresar</button>
</div>

</body>
</html>
