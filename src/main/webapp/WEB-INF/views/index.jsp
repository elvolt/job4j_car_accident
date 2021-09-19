<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/0a16ec4483.js" crossorigin="anonymous"></script>
    <title>Инциденты</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-10">
            <h1 class="h2 text-center">Инциденты</h1>
            <a class="btn btn-primary my-2" role="button" href="<c:url value='/create'/>"><i
                    class="fas fa-car-crash"></i> Добавить инцидент</a>
            <a class="btn btn-primary my-2 float-end" role="button" href="<c:url value='/logout'/>">
                <i class="fas fa-user"></i> ${user.username} |
                <i class="fas fa-door-open"></i> Выйти</a>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Название</th>
                    <th scope="col">Адрес</th>
                    <th scope="col">Тип</th>
                    <th scope="col">Статьи</th>
                    <th scope="col">Описание</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="accident" items="${accidents}">
                    <tr>
                        <td><c:out value="${accident.name}"/></td>
                        <td><c:out value="${accident.address}"/></td>
                        <td><c:out value="${accident.type.name}"/></td>
                        <td>
                            <c:forEach var="rule" items="${accident.rules}">
                                <c:out value="${rule.name}"/><br>
                            </c:forEach>
                        </td>
                        <td><c:out value="${accident.text}"/></td>
                        <td><a class="btn btn-light" role="button" href="<c:url value='/update?id=${accident.id}'/>"><i
                                class="far fa-edit"></i></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
        crossorigin="anonymous"></script>
</body>
</html>