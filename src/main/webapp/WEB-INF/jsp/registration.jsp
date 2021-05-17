<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/x-icon" href="/resources/css/che_guevara.ico">
    <link rel = "stylesheet" href = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="/resources/css/auth.css" rel="stylesheet">
    <title>Регистрация</title>

</head>

<body>
<div class="container">

    <form:form method="POST" modelAttribute="userForm" class="form-signin" id="form1">
        <div class="text-center mb-4">
            <a href="/login"><img class="mb-4" src="/resources/css/anonymous-logo.png" alt="" width="72" height="72"></a>
            <h1 class="h3 mb-3 font-weight-normal">Knowledge system</h1>
            <p>Регистрация</p>
        </div>
        <h2 class="form-signin-heading"></h2>

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" class="form-control" placeholder="Логин"
                            autofocus="true"/>
                <form:errors path="username"/>
            </div>

        <c:if test="${userForm.surfaces.size() == 0}">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="email" path="email" class="form-control" placeholder="Email"/>
                <form:errors path="email"/>
            </div>
        </c:if>

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="surname" class="form-control" placeholder="Имя"/>
                <form:errors path="surname"/>
            </div>

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="Пароль"/>
                <form:errors path="password"/>
            </div>

            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="passwordConfirm" class="form-control"
                            placeholder="Подтвердите введенный Вами пароль"/>
                <form:errors path="passwordConfirm"/>
            </div>

            <form:hidden path="surfaces"/>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Готово</button>
    </form:form>

</div>
</body>
</html>