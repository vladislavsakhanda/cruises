<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<c:choose>
    <c:when test="${sessionScope.userEmail == null}">
        <a href="/cruises">Home</a> |
        <a href="/cruises/register">register</a> |
        <a href="/cruises/login">login</a> |
        <a href="/cruises/profile">profile</a>
    </c:when>
    <c:otherwise>
        <a href="/cruises">Home</a> |
        <a href="/cruises/profile">${sessionScope.userEmail}</a>
    </c:otherwise>
</c:choose>
<hr/>