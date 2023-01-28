<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<head>
    <title><fmt:message key="label.lang.index.title" /></title>
</head>

<c:choose>
    <c:when test="${role == null}">
      <h4><fmt:message key="label.lang.index.welcome.user"/><h4><br>
    </c:when>
    <c:otherwise>
        <fmt:message key="label.lang.index.welcome.admin"/>
    </c:otherwise>
</c:choose>





