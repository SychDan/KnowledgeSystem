<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="partOne.jsp"/>
<div id="content">
    <p>Введите слово</p>

    <form method="get" action="/surfaces/${surface.id}/findPages">

        <p><input name="word" type="text" placeholder="Введите слово"/></p>

        <p><button type="submit" class="btn btn-primary" value="Find">Искать</button></p>

    </form>


    <c:if test="${word.length() > 0}">
        <h3>По имени</h3>
    <c:choose>
        <c:when test="${foundPagesByName.size() > 0}">
           <c:forEach var="page" items="${foundPagesByName}">
              <p><a href="/surfaces/${surface.id}/pages/show/${page.id}">${page.name}</a></p>
           </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Страницы не найдены</p>

        </c:otherwise>
    </c:choose>


        <h3>По содержанию</h3>
    <c:choose>
        <c:when test="${foundPagesByContent.size() > 0}">
            <c:forEach var="page" items="${foundPagesByContent}">
                <p><a href="/surfaces/${surface.id}/pages/show/${page.id}">${page.name}</a></p>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Страницы не найдены</p>
        </c:otherwise>
    </c:choose>
    </c:if>
</div>
<jsp:include page="partTwo.jsp"/>