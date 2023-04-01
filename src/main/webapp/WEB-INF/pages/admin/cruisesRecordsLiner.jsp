<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<head>
    <title><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.title" /></title>
</head>

<body>

<c:set var="liner" value="${cruisesTLD:getLinerById(id)}"/>
<c:set var="freePlaces" value="${cruisesTLD:getLinerFreePlaces(liner)}"/>


<form action="?command=CruisesRecordsLiner&id=${liner.id}" method="POST">
<b><fmt:message key="label.lang.admin.newCruise.ActualName" />:</b>
${liner.name}</br>
<b><fmt:message key="label.lang.admin.newCruise.NewName" />:</b><input name="name" id="myInput" />
<fmt:message key="${requestScope.messageName}" />

<br><br><b><fmt:message key="label.lang.admin.newCruise.ActualDescription" />:</b>
${liner.description}<br>
<b><fmt:message key="label.lang.admin.newCruise.NewDescription" />:</b> <textarea name="description" cols="50" rows="10"></textarea>
<fmt:message key="${requestScope.messageDescription}" />

<br><br><b><fmt:message key="label.lang.admin.newCruise.ActualCapacity" />:</b>
${liner.capacity}<br>
<b><fmt:message key="label.lang.admin.newCruise.NewCapacity" />:</b> <input type="number" name="capacity" id="myInput" />
<fmt:message key="${requestScope.messageCapacity}" />

<br><br><b><fmt:message key="label.lang.admin.newCruise.ActualPriceCoefficient" />:</b>
${liner.priceCoefficient}<br>
<b><fmt:message key="label.lang.admin.newCruise.NewPriceCoefficient" />:</b> <input type="number" step="0.01" name="priceCoefficient" id="myInput" />
<fmt:message key="${requestScope.messagePriceCoefficient}" />

<br><br><b><fmt:message key="label.lang.admin.newCruise.ActualStartCruise" />:</b>
${liner.dateStart}<br>
<b><fmt:message key="label.lang.admin.newCruise.NewStartCruise" />:</b> <input type="date" min='${cruisesTLD:getCurrentDate()}' max='${cruisesTLD:getCurrentDatePlusYear()}' name="dateStart">
<fmt:message key="${requestScope.messageDateStart}" />


<br><br><b><fmt:message key="label.lang.admin.newCruise.ActualEndCruise" />:</b>
${liner.dateEnd}<br>
<b><fmt:message key="label.lang.admin.newCruise.NewEndCruise" />:</b> <input type="date" min='${cruisesTLD:getCurrentDate()}' max='${cruisesTLD:getCurrentDatePlusYear()}' name="dateEnd">
<fmt:message key="${requestScope.messageDateEnd}" />


<table class="styled-table">
        <thead>
        <tr>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.day" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.port" /></th>
            <th><fmt:message key="label.lang.admin.newCruise.NewPort" /></th>
        </tr>
        </thead>

        <tbody>
        <c:set var="routeNumber" value="${1}"/>
        <c:forEach var="entry" items="${cruisesTLD:getLinerRouteInMap(liner)}">
            <tr class="active-row">
                <td>${entry.key}</a></td>
                <td>${entry.value}</td>
                <td><input name="route${routeNumber}" id="myInput" /></td>
                <c:set var="routeNumber" value="${routeNumber+1}"/>
            </tr>
        </c:forEach>
        </tbody>
</table>

</h5>

<input type="hidden" name="linerId" value="${liner.id}" />

<div><input type="submit" value="<fmt:message key="label.lang.admin.change" />" /></div>
</form>

<form action="?command=CruisesRecordsLiner" method="POST" style="margin: 0px;">
    <input type="hidden" name="linerId" value="${liner.id}" />
    <span><input type="hidden" name="action" value="delete" /></span>
    <span><input type="submit" value="<fmt:message key="label.lang.admin.delete" />" /></span>
</form>

<br><br><a href="/cruises?command=CruisesRecords"><fmt:message key="label.lang.index.welcome.admin.cruisesRecordsLiner" /></a><br>
</body>




