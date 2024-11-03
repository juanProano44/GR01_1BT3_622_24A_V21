<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Visualización de Roles</title>
    <style>
        /* Ocultar las tablas por defecto */
        .role-table {
            display: none;
        }

        /* Ocultar los detalles por defecto */
        .details {
            display: none;
        }
    </style>
    <script type="text/javascript">
        window.onload = function() {
            // Función para mostrar/ocultar tablas de roles
            function showTable(role) {
                // Ocultar todas las tablas primero
                document.getElementById("alumnosTable").style.display = "none";
                document.getElementById("tutoresTable").style.display = "none";
                document.getElementById("adminsTable").style.display = "none";

                // Mostrar solo la tabla seleccionada
                if (role === 'alumnos') {
                    document.getElementById("alumnosTable").style.display = "block";
                } else if (role === 'tutores') {
                    document.getElementById("tutoresTable").style.display = "block";
                } else if (role === 'admins') {
                    document.getElementById("adminsTable").style.display = "block";
                }
            }

            // Mostrar/ocultar los detalles del tutor
            function toggleDetails(tutorId) {
                var detailsDiv = document.getElementById('details-' + tutorId);
                if (detailsDiv.style.display === "none") {
                    detailsDiv.style.display = "block";
                } else {
                    detailsDiv.style.display = "none";
                }
            }

            // Asignar las funciones al objeto window para que puedan ser usadas desde los eventos
            window.showTable = showTable;
            window.toggleDetails = toggleDetails;
        };
    </script>
</head>
<body>
<h2>Visualización de Roles</h2>

<!-- Botones para visualizar las tablas de roles -->
<button onclick="showTable('alumnos')">Ver Alumnos</button>
<button onclick="showTable('tutores')">Ver Tutores</button>
<button onclick="showTable('admins')">Ver Administradores</button>

<!-- Tabla de Alumnos -->
<div id="alumnosTable" class="role-table">
    <h3>Lista de Alumnos</h3>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
        </tr>
        <c:forEach var="alumno" items="${alumnos}">
            <tr>
                <td>${alumno.id}</td>
                <td>${alumno.nombre}</td>
                <td>${alumno.apellido}</td>
                <td>${alumno.email}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/CambiarEstadoCuentaServlet" method="post">
                        <input type="hidden" name="userId" value="${alumno.id}">
                        <input type="hidden" name="accion" value="banear">
                        <input type="hidden" name="typeUser" value="alumno">
                        <button type="submit">Banear Correo</button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/CambiarEstadoCuentaServlet" method="post">
                        <input type="hidden" name="userId" value="${alumno.id}">
                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="typeUser" value="alumno">
                        <button type="submit">Eliminar Cuenta</button>
                    </form>
                </td>

            </tr>
        </c:forEach>


    </table>
</div>

<!-- Tabla de Tutores -->
<div id="tutoresTable" class="role-table">
    <h3>Lista de Tutores</h3>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
            <th>Acciones</th>
        </tr>
        <c:forEach var="tutor" items="${tutores}">
            <tr>
                <td>${tutor.id}</td>
                <td>${tutor.nombre}</td>
                <td>${tutor.apellido}</td>
                <td>${tutor.email}</td>
                <td>
                    <!-- Botón para mostrar/ocultar detalles del tutor -->
                    <button onclick="toggleDetails(${tutor.id})">Ver detalles</button>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/CambiarEstadoCuentaServlet" method="post">
                        <input type="hidden" name="userId" value="${tutor.id}">
                        <input type="hidden" name="accion" value="banear">
                        <input type="hidden" name="typeUser" value="tutor">
                        <button type="submit">Banear Correo</button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/CambiarEstadoCuentaServlet" method="post">
                        <input type="hidden" name="userId" value="${tutor.id}">
                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="typeUser" value="tutor">
                        <button type="submit">Eliminar Cuenta</button>
                    </form>
                </td>

            </tr>

            <!-- Detalles del tutor (oculto por defecto) -->
            <tr id="details-${tutor.id}" class="details">
                <td colspan="5">
                    <h4>Tutorías Creadas</h4>
                    <table border="1">
                        <tr>
                            <th>ID</th>
                            <th>Materia</th>
                            <th>Fecha</th>
                        </tr>
                        <c:forEach var="tutoria" items="${tutor.tutorias}">
                            <tr>
                                <td>${tutoria.id}</td>
                                <td>${tutoria.materia.nombre}</td>
                                <td>${tutoria.fecha}</td>
                            </tr>
                        </c:forEach>
                    </table>

                    <h4>Solicitudes de Alumnos</h4>
                    <!-- Recorrer las tutorías del tutor -->
                    <c:forEach var="tutoria" items="${tutor.tutorias}">
                        <!-- Recorrer las solicitudes de cada tutoría -->
                        <h4>Tutoría: ${tutoria.materia.nombre} - ${tutoria.fecha}</h4>
                        <table border="1">
                            <tr>
                                <th>ID Solicitud</th>
                                <th>Alumno</th>
                                <th>Estado</th>
                            </tr>
                            <c:forEach var="solicitud" items="${tutoria.solicitudes}">
                                <tr>
                                    <td>${solicitud.id}</td>
                                    <td>${solicitud.alumno.nombre} ${solicitud.alumno.apellido}</td>
                                    <td>${solicitud.estado}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:forEach>

                </td>

            </tr>
        </c:forEach>
    </table>
</div>

<!-- Tabla de Administradores -->
<div id="adminsTable" class="role-table">
    <h3>Lista de Administradores</h3>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Email</th>
        </tr>
        <c:forEach var="admin" items="${administradores}">
            <tr>
                <td>${admin.id}</td>
                <td>${admin.nombre}</td>
                <td>${admin.apellido}</td>
                <td>${admin.email}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/CambiarEstadoCuentaServlet" method="post">
                        <input type="hidden" name="userId" value="${admin.id}">
                        <input type="hidden" name="accion" value="banear">
                        <input type="hidden" name="typeUser" value="admin">
                        <button type="submit">Banear Correo</button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/CambiarEstadoCuentaServlet" method="post">
                        <input type="hidden" name="userId" value="${admin.id}">
                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="typeUser" value="admin">
                        <button type="submit">Eliminar Cuenta</button>
                    </form>
                </td>

            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
