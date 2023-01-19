<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<c:choose>
    <c:when test="${sessionScope.userEmail == null}">
        <a href="/cruises">Home</a> |
        <a href="register">register</a> |
        <a href="login">login</a> |
        <a href="profile">profile</a>
    </c:when>
    <c:otherwise>
        <a href="/cruises">Home</a> |
        <a href="profile">${sessionScope.userEmail}</a>
    </c:otherwise>
</c:choose>
<hr/>