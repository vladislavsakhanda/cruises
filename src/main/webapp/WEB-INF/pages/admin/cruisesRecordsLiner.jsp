<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<head>
    <title><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.title" /></title>
</head>

<body>

<c:set var="liner" value="${cruisesTLD:getLinerById(id)}"/>
<c:set var="freePlaces" value="${cruisesTLD:getLinerFreePlaces(liner)}"/>


<form action="?command=CruisesRecordsLiner&id=${liner.id}" method="POST">
<b>Актуальна назва:</b>
${liner.name}</br>
<b>Нова назва:</b><input name="name" id="myInput" />
<fmt:message key="${requestScope.messageName}" />

<br><br><b>Актуальний опис:</b>
${liner.description}<br>
<b>Новий опис:</b> <textarea name="description" cols="50" rows="10"></textarea>
<fmt:message key="${requestScope.messageDescription}" />

<br><br><b>Актуальна місткість:</b>
${liner.capacity}<br>
<b>Новий місткість:</b> <input type="number" name="capacity" id="myInput" />
<fmt:message key="${requestScope.messageCapacity}" />

<br><br><b>Актуальний ціновий коефіцієнт:</b>
${liner.priceCoefficient}<br>
<b>Новий ціновий коефіцієнт:</b> <input type="number" step="0.01" name="priceCoefficient" id="myInput" />
<fmt:message key="${requestScope.messagePriceCoefficient}" />

<br><br><b>Актуальний початок круїзу:</b>
${liner.dateStart}<br>
<b>Новий початок круїзу:</b> <input type="date" min='${cruisesTLD:getCurrentDate()}' max='${cruisesTLD:getCurrentDatePlusYear()}' name="dateStart">
<fmt:message key="${requestScope.messageDateStart}" />


<br><br><b>Актуальний кінець круїзу:</b>
${liner.dateEnd}<br>
<b>Новий кінець круїзу:</b> <input type="date" min='${cruisesTLD:getCurrentDate()}' max='${cruisesTLD:getCurrentDatePlusYear()}' name="dateEnd">
<fmt:message key="${requestScope.messageDateEnd}" />

<table class="styled-table">
        <thead>
        <tr>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.day" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.port" /></th>
            <th>Новий порт</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="entry" items="${cruisesTLD:getLinerRouteInMap(liner)}">
            <tr class="active-row">
                <td>${entry.key}</a></td>
                <td>${entry.value}</td>
                <td><input name="name" id="myInput" /></td>
            </tr>
        </c:forEach>
        </tbody>
</table>

</h5>

<input type="hidden" name="linerId" value="${liner.id}" />

<div><input type="submit" value="Змінити" /></div>
</form>

<br><br><a href="/cruises?command=CruisesRecords"><fmt:message key="label.lang.index.welcome.admin.cruisesRecordsLiner" /></a><br>
</body>




