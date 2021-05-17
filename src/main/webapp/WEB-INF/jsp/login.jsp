<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/x-icon" href="/resources/css/che_guevara.ico">
    <link rel = "stylesheet" href = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Авторизация</title>
    <meta charset="UTF-8">
    <link href="/resources/css/auth.css" rel="stylesheet">
</head>

<body>

<div class="container">

    <form method="POST" action="/login" class="form-signin">
        <div class="text-center mb-4">
            <img class="mb-4" src="/resources/css/anonymous-logo.png" alt="" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal">Knowledge system</h1>
            <p>Введите ваш логин и пароль</p>
        </div>
        <span>${message}</span>
        <div class="form-label-group ${error != null ? 'has-error' : ''}">

            <input id="log" name="username" type="text" class="form-control" placeholder="Логин"
                   required autofocus/>
            <label for="log">Логин</label>
        </div>
        <div class="form-label-group ${error != null ? 'has-error' : ''}">
            <input id="pas" name="password" type="password" class="form-control" placeholder="Пароль" required/>
            <span>${error}</span>
            <label for="pas">Пароль</label>
        </div>
        <div class="form-label-group ${error != null ? 'has-error' : ''}">
        </div>

            <input type="checkbox" name="remember-me" hidden checked/>

        <div class="form-label-group ${error != null ? 'has-error' : ''}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
            <h4 class="text-center"><a href="/registration">Создать аккаунт</a></h4>
        </div>

    </form>

</div>
</body>
</html>