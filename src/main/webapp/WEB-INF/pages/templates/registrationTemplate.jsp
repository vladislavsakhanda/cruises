<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<div id="homeElements">
<c:choose>
    <c:when test="${sessionScope.userEmail == null}">
        <a href="/cruises?command=Home" id="home_a"><fmt:message key="label.lang.registration.home" /></a>
        <a href="/cruises?command=CruisesCatalog" id="home_a"><fmt:message key="label.lang.registration.cruisesCatalog" /></a>
        <a href="/cruises?command=Register" id="home_a"><fmt:message key="label.lang.registration.registration" /></a>
        <a href="/cruises?command=Login" id="home_a"><fmt:message key="label.lang.registration.login" /></a>
        <a href="/cruises?command=Profile" id="home_a"><fmt:message key="label.lang.registration.profile" /></a>
    </c:when>
    <c:otherwise>
        <a href="/cruises?command=Home" id="home_a"><fmt:message key="label.lang.registration.home" /></a>
        <c:if test="${role != 'admin'}">
            <a href="/cruises?command=CruisesCatalog" id="home_a"><fmt:message key="label.lang.registration.cruisesCatalog" /></a>
        </c:if>
        <a href="/cruises?command=Profile" id="home_a">${sessionScope.userEmail}</a>
    </c:otherwise>
</c:choose>

<c:set var="parametersPath" value=""/>
<c:forEach var="entry" items="${paramValues}">
        <c:if test="${entry.key != 'language'}">
            <c:set var="parametersPath" value="${parametersPath}${entry.key}"/>

            <c:forEach var="value" items="${entry.value}">
                <c:set var="parametersPath" value="${parametersPath}=${value}&"/>
            </c:forEach>
        </c:if>

</c:forEach>


<select name="language" onchange="location = this.value;" class="select-css">
    <option value=""><fmt:message key="label.lang.registration.changeLanguage" /></option>
    <option value="?${parametersPath}language=en">English</option>
    <option value="?${parametersPath}language=ua">Українська</option>
</select>
</div>