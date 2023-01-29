<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<c:choose>
    <c:when test="${sessionScope.userEmail == null}">
        <a href="/cruises"><fmt:message key="label.lang.registration.home" /></a> |
        <a href="/cruises/cruisesCatalog"><fmt:message key="label.lang.registration.cruisesCatalog" /></a> |
        <a href="/cruises/register"><fmt:message key="label.lang.registration.registration" /></a> |
        <a href="/cruises/login"><fmt:message key="label.lang.registration.login" /></a> |
        <a href="/cruises/profile"><fmt:message key="label.lang.registration.profile" /></a> |
    </c:when>
    <c:otherwise>
        <a href="/cruises"><fmt:message key="label.lang.registration.home" /></a> |
        <c:if test="${role != 'admin'}">
            <a href="/cruises/cruisesCatalog"><fmt:message key="label.lang.registration.cruisesCatalog" /></a> |
        </c:if>
        <a href="/cruises/profile">${sessionScope.userEmail}</a> |
    </c:otherwise>
</c:choose>

<select name="language" onchange="location = this.value;">
    <option value=""><fmt:message key="label.lang.registration.changeLanguage" /></option>
    <option value="?language=en">English</option>
    <option value="?language=ua">Українська</option>
</select>
<hr/>