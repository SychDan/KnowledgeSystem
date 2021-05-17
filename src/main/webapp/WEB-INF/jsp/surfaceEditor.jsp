<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="partOne.jsp"/>

    <form:form action="/surfaces/save" method="post" modelAttribute="newSurface">

        <form:hidden path="id"/>
        <form:hidden path="adminId"/>
        <form:hidden path="users"/>
        <div class="form-group">
            <label for="example">Название:</label>
            <form:input path="name" type="text" class="form-control" id="example" placeholder="Введите название" />
            <p>Тип</p>
            <form:radiobutton path="accessType" value="private"/>Закрытое
            <form:radiobutton path="accessType" value="public"/>Открытое
            <p>Описание:</p>
            <form:textarea path="description" id="editor" username="editor"></form:textarea>
            <script>
                CKEDITOR.replace('editor');
            </script>
        </div>
        <button type="submit" class="btn btn-primary"  value="Save">Сохранить</button>

    </form:form>

    <p><a class="btn btn-light" href="/surfaces" role="button">Закрыть</a></p>
<jsp:include page="partTwo.jsp"/>