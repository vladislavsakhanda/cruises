<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<head>
    <title><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.title" /></title>
</head>

<body>
<div id="indent">
<c:set var="liner" value="${cruisesTLD:getLinerById(id)}"/>
<c:set var="linerId" value="${liner.getId()}"/>
<c:set var="price" value="${cruisesTLD:countPriceLiner(liner)}"/>
<c:set var="dateStart" value="${liner.getDateStart()}"/>
<c:set var="dateEnd" value="${liner.getDateEnd()}"/>
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
                <td>${dateStart} <fmt:message key="label.lang.admin.to" /> ${dateEnd}</td>
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
<c:set var="trip" value="${cruisesTLD:getTripByUserIdAndLinerId(sessionScope.userId, linerId)}"/>
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
                    <input type="hidden" name="linerId" value="${linerId}" />
                    <input type="hidden" name="price" value="${price}" />
                    <input type="hidden" name="dateStart" value="${dateStart}" />
                    <input type="hidden" name="dateEnd" value="${dateEnd}" />
                    <input type="hidden" name="actionBook" value="bookView" />
                    <input type='submit' name="Submit" value="<fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.makeRequest" />" />
        </form>
        <p>
    </c:otherwise>
</c:choose>
<a href="/cruises?command=CruisesCatalog"><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.chooseOtherCruise" /></a><br>
</h5>
</body>


