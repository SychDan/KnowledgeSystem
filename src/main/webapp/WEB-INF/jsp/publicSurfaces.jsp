<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="partOne.jsp"/>

<h1>Популярные пространства</h1>
<c:forEach var="publicSurface" items="${publicSurfacesByPopularity}">
    <p><a href="/surfaces/show/${publicSurface.id}">${publicSurface.name}</a> (${publicSurface.users.size()} участников)</p>
</c:forEach>

<h1>
    Поиск открытых пространств
</h1>

<form method="get" action="/surfaces/public">
    <p><input name="word" type="text" placeholder="Введите название"/></p>
    <p><button type="submit" class="btn btn-primary" value="Find">Искать</button></p>
</form>


<c:if test="${word.length() > 0}">
    <c:choose>
        <c:when test="${foundPublicSurfacesByName.size() > 0}">
            <h1>Найденные пространства</h1>
            <c:forEach var="surface" items="${foundPublicSurfacesByName}">
                <p><a href="/surfaces/show/${surface.id}">${surface.name}</a></p>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Пространства не найдены</p>
        </c:otherwise>
    </c:choose>
</c:if>
<jsp:include page="partTwo.jsp"/>