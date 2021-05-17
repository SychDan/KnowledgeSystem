<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Выбор пространства</title>
</head>
<body>
<h1>Выберите простраство</h1>

<form action="/surfaces/participatePageToSurface" method="get" name="participate_form">
    <input type="hidden" name="pageId" id="form_pageId">
    <input type="hidden" name="surfaceId" id="form_surfaceId">
</form>

<script>
    function someFunction(pageId, surfaceId) {
        document.getElementById("form_pageId").value = pageId;
        document.getElementById("form_surfaceId").value = surfaceId;
        document.forms["participate_form"].submit();
    }
</script>

<c:forEach var="surface" items="${surfaceList}">
    <p><a class="btn btn-light" href="#" onclick="someFunction(${pageId}, ${surface.id})">${surface.name}</a></p>
</c:forEach>

<p><button class="btn btn-primary" onclick="history.back()">Отмена</button></p>
</body>
</html>