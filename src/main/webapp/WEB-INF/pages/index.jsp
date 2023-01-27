<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>

<c:choose>
    <c:when test="${role == null}">
      <h4>Ласкаво просимо на наш сайт. Тут Ви можете обрати круїз і відправитись у подорож!<h4><br>
      <a href="/cruises/cruisesCatalog">cruisesCatalog</a> <br>
    </c:when>
    <c:otherwise>
        Сторінка для користувачів.
    </c:otherwise>
</c:choose>





