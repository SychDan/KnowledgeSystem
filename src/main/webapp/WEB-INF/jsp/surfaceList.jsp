<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="partOne.jsp"/>
    <h1>Мои пространства</h1>
    <table class="table">
        <th>Название</th>
        <th></th>
        <th></th>

        <c:forEach var="surface" items="${surfaceList}">
            <tr>
                <td>${surface.name}</td>
                <td><a class="btn btn-info" href="/surfaces/show/${surface.id}" role="button">Перейти</a></td>
            </tr>
        </c:forEach>

    </table>

    <p><a class="btn btn-primary" href="/surfaces/new">Создать новое пространство</a></p>
<jsp:include page="partTwo.jsp"/>