<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="partOne.jsp"/>
<c:forEach var="i" items="${pageList}">
        <h2>${i.name}</h2>
        <p>${i.content}</p>
        <div class="line"></div>
</c:forEach>

<jsp:include page="partTwo.jsp"/>