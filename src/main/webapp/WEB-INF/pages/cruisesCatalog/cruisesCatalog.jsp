<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<html>
<head>
    <title><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.title" /></title>
</head>

<body>

    <div id="filter">
    <form action="?command=CruisesCatalog" method="POST" style="margin: 0px;">
        <div style="margin-top: 15px;">
        <span><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.cruisesCatalog" />:</span>
        <span><select name="recordsPerPage" value="5">
            <option value="3">3</option>
            <option value="5">5</option>
            <option value="10">10</option>
        </select></span>
        </div>

        <div style="margin-top: 15px;">
        <span><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.duration" />:</span>
        <span><select name="choseDuration">
            <option value="0">*</option>
            <c:forEach var="duration" items="${allDurations}">
                <option value="${duration}">${duration} <fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.days" /></option>
            </c:forEach>
        </select></span>
        </div>

        <div style="margin-top: 15px;">
        <span><input type="date" min='${minDateStart}' max='${maxDateStart}' value='${currentDateStart}' name="dateStart" required></span>
        <span><fmt:message key="label.lang.admin.to" /></span>
        <span><input type="date" min='${minDateEnd}' max='${maxDateEnd}' value='${currentDateEnd}' name="dateEnd" required></span>
        </div>

        <div style="margin-top: 15px;">
        <span"><input type="submit" value="<fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.confirm" />" style="margin: 0px;"/></span>


    </form>
    <form action="?command=CruisesCatalog" method="POST" style="margin: 0px;">
                <span><input type="hidden" name="action" value="reset" /></span>
                <span><input type="submit" value="<fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.reset" />" /></span>
    </form>
            </div>
    </div>

    <table class="styled-table">
        <thead>
        <tr>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.cruise" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.date" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.price" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.capacity" /></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="liner" items="${linerList}">
            <tr class="active-row">
                <td><a href="/cruises?command=CruisesCatalogLiner&id=${liner.id}">${liner.name}</a></td>
                <td>${liner.dateStart} <fmt:message key="label.lang.admin.to" /> ${liner.dateEnd}</td>
                <td>${cruisesTLD:countPriceLiner(liner)}$</td>
                <td>${liner.capacity}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<tf:pagination/>

</body>
</html>