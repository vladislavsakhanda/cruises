<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<head>
    <title><fmt:message key="label.lang.index.title" /></title>
</head>

<div id="indent">
<c:choose>
    <c:when test="${role == null}">
      <h4><fmt:message key="label.lang.index.welcome.user"/><h4>
    </c:when>
    <c:otherwise>
        <h4><fmt:message key="label.lang.index.welcome.admin"/><h4>
    </c:otherwise>
</c:choose>
</div>




