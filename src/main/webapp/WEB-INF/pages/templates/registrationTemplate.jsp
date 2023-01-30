<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<div id="homeElements">
<c:choose>
    <c:when test="${sessionScope.userEmail == null}">
        <a href="/cruises" id="home_a"><fmt:message key="label.lang.registration.home" /></a>
        <a href="/cruises/cruisesCatalog" id="home_a"><fmt:message key="label.lang.registration.cruisesCatalog" /></a>
        <a href="/cruises/register" id="home_a"><fmt:message key="label.lang.registration.registration" /></a>
        <a href="/cruises/login" id="home_a"><fmt:message key="label.lang.registration.login" /></a>
        <a href="/cruises/profile" id="home_a"><fmt:message key="label.lang.registration.profile" /></a>
    </c:when>
    <c:otherwise>
        <a href="/cruises" id="home_a"><fmt:message key="label.lang.registration.home" /></a>
        <c:if test="${role != 'admin'}">
            <a href="/cruises/cruisesCatalog" id="home_a"><fmt:message key="label.lang.registration.cruisesCatalog" /></a>
        </c:if>
        <a href="/cruises/profile" id="home_a">${sessionScope.userEmail}</a>
    </c:otherwise>
</c:choose>

<select name="language" onchange="location = this.value;" class="select-css">
    <option value=""><fmt:message key="label.lang.registration.changeLanguage" /></option>
    <option value="?language=en">English</option>
    <option value="?language=ua">Українська</option>
</select>

</div>