<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="partOne.jsp"/>

<h2>
    Добро пожаловать, ${user.surname}!
</h2>

<jsp:include page="partTwo.jsp"/>