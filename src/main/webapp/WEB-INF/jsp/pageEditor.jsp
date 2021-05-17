<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="partOne.jsp"/>
    <form:form action="/surfaces/${surfaceId}/pages/save" method="post" modelAttribute="page">

        <div class="form-group">
            <form:hidden path="id"/>
            <form:hidden path="level"/>
            <form:hidden path="ancestorId"/>
            <form:hidden path="creatorName"/>

            <p>Название:</p>
            <form:input path="name"/><br>
            <p>Содержимое:</p>

            <form:textarea path="content" id="editor" username="editor"></form:textarea>
            <script>
                CKEDITOR.replace('editor');
            </script>
        </div>
        <button type="submit" class="btn btn-primary" value="Save">Сохранить</button>
        <br>

    </form:form>

    <p><button class="btn btn-light" onclick="history.back()">Закрыть</button></p>
<jsp:include page="partTwo.jsp"/>