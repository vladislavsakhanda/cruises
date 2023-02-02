<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<head>
    <title><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.title" /></title>
        <style><%@include file="/WEB-INF/css/style.css"%></style>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<body>
<div id="indent">
<c:set var="liner" value="${cruisesTLD:getLinerById(id)}"/>
<c:set var="liner_id" value="${liner.getId()}"/>
<c:set var="price" value="${cruisesTLD:countPriceLiner(liner)}"/>
<c:set var="date_start" value="${liner.getDate_end()}"/>
<c:set var="date_end" value="${liner.getDate_start()}"/>
<c:set var="freePlaces" value="${cruisesTLD:getLinerFreePlaces(liner)}"/>

<table class="styled-table" style="margin-left: 5px; position: absolute; list-style: none; padding-left: 0;">
        <thead>
        <tr style="">
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.name" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.price" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.freePlaces" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.date" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.details" /></th>
        </tr>
        </thead>

        <tbody>
            <tr class="active-row">
                <td>${liner.name}</td>
                <td>${price}$</td>
                <td>${freePlaces}</td>
                <td>${date_start} <fmt:message key="label.lang.admin.to" /> ${date_end}</td>
                <td style="font-size: 25px; max-width:700px; word-wrap:break-word;">${liner.description}</td>
            </tr>
        </tbody>
</table>

<table class="styled-table" style="margin-left: 75%;">
        <thead>
        <tr>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.day" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.port" /></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="entry" items="${cruisesTLD:getLinerRouteInMap(liner)}">
            <tr class="active-row">
                <td>${entry.key}</a></td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
        </tbody>
</table>

<h5 style="position: relative;position:absolute;bottom: 0px;left: 0;right:0;">
<c:set var="trip" value="${cruisesTLD:getTripByUserIdAndLinerId(sessionScope.userId, liner_id)}"/>
<c:choose>
    <c:when test="${trip != null}">
        <p><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.requestExist" />.</p>
    </c:when>
    <c:when test="${freePlaces == 0}">
        <p><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.capacitiesBusy" />.<p>
    </c:when>
    <c:when test="${sessionScope.userEmail == null}">
        <p><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.onlySigned" />.<p>
    </c:when>
    <c:otherwise>
        <p>
        <form method="POST" action="?command=BookTour">
                    <input type="hidden" name="liner_id" value="${liner_id}" />
                    <input type="hidden" name="price" value="${price}" />
                    <input type="hidden" name="date_start" value="${date_start}" />
                    <input type="hidden" name="date_end" value="${date_end}" />
                    <input type="hidden" name="action" value="book" />
                    <input type='submit' name="Submit" value="<fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.makeRequest" />" />
        </form>
        <p>
    </c:otherwise>
</c:choose>
<a href="/cruises/cruisesCatalog"><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.chooseOtherCruise" /></a><br>
</h5>
</body>


