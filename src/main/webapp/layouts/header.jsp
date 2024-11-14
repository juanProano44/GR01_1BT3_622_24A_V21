<%@ page language="java" pageEncoding="UTF-8"%>
<header>
    <style>
        header {
            background-color: #f8f9fa;
            padding: 10px;
            text-align: right;
        }
        .username {
            margin-right: 20px;
            font-weight: bold;
        }
        .logout-btn {
            background-color: #dc3545;
            color: #fff;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }
    </style>

    <c:if test="${not empty sessionScope.nombre && not empty sessionScope.apellido && not empty sessionScope.rolUsuario}">
        <span class="username">
            Hola ${sessionScope.rolUsuario} ${sessionScope.nombre} ${sessionScope.apellido}, Bienbenid@
        </span>
        <form action="${pageContext.request.contextPath}/LogoutServlet" method="post" style="display: inline;">
            <button type="submit" class="logout-btn">Sign Out</button>
        </form>
    </c:if>
</header>
