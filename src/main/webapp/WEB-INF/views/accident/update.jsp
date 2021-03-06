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
        <div class="col-7">
            <h1 class="h2 text-center">Редактирование инцидента</h1>
            <form action="<c:url value='/save?id=${accident.id}'/>" method="post">
                <div class="mb-3">
                    <label for="name" class="form-label">Название</label>
                    <input type="text" value="${accident.name}" class="form-control" id="name" name="name" required>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Адрес</label>
                    <input type="text" value="${accident.address}" class="form-control" id="address" name="address"
                           required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="type">Тип</label>
                    <select class="form-select" id="type" name="typeId" required>
                        <c:forEach var="type" items="${types}">
                            <c:if test="${type.id == accident.type.id}">
                                <option value="${type.id}" selected>${type.name}</option>
                            </c:if>
                            <c:if test="${type.id != accident.type.id}">
                                <option value="${type.id}">${type.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="rule">Статьи</label>
                    <select class="form-select" id="rule" name="ruleIds" multiple required>
                        <c:forEach var="rule" items="${rules}">
                            <c:set var = "selected" value="false"/>
                            <c:forEach var="accidentRule" items="${accident.rules}">
                                <c:if test="${rule.id == accidentRule.id}">
                                    <c:set var = "selected" value="true"/>
                                </c:if>
                            </c:forEach>
                            <c:if test="${selected == true}">
                                <option value="${rule.id}" selected>${rule.name}</option>
                            </c:if>
                            <c:if test="${selected != true}">
                                <option value="${rule.id}">${rule.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Описание</label>
                    <textarea rows="3" class="form-control" id="description" name="text" required>${accident.text}</textarea>
                </div>
                <button type="submit" class="btn btn-primary">Отправить</button>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
        crossorigin="anonymous"></script>
</body>
</html>
