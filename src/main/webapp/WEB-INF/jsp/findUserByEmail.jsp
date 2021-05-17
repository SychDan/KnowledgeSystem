<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="partOne.jsp"/>
<div id="content">
<p>Введите email пользователя</p>

<form method="get" action="/surfaces/${surfaceId}/findUser">
    <p><input name="email" type="text" placeholder="Введите email"/></p>

    <p><button type="submit" class="btn btn-primary" value="Find">Поиск</button></p>
</form>

    <form method="get" id="form1" action="/surfaces/${surfaceId}/sendInvitationOn">
        <input type="hidden" name="email" value="${email}">
    </form>
<c:if test="${email.length() > 0}">
<c:choose>
<c:when test="${foundUsers.size() > 0}">
    <p>Найденные пользователи</p>
    <c:forEach var="foundUser" items="${foundUsers}">
    <p><a href="/surfaces/${surfaceId}/addUser/${foundUser.id}">${foundUser.surname}: ${foundUser.email}</a></p>
    </c:forEach>
</c:when>
    <c:when test="${email != null && foundUsers.size() == 0}">
        <p>
            Пользователь с почтой ${email} не найден, но вы можете <a href="#" class="btn btn-primary btn-lg active" onclick="document.getElementById('form1').submit(); return false;">отправить</a> приглашение по почте
        </p>
    </c:when>
</c:choose>
</c:if>
</div>
<jsp:include page="partTwo.jsp"/>