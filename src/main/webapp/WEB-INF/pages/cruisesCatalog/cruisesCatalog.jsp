<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<html>
<head>
    <title><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.title" /></title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
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

    <table>
        <tr>
            <c:choose>
            <c:when test="${currentPage != 1}">
                <td style="padding: 12px 15px;"><a href="/cruises?command=CruisesCatalog&currentPage=${currentPage - 1}">
                <fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.previous" /></a></td>
            </c:when>
            <c:otherwise>
                <td style="padding: 12px 15px;">
                <fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.previous" /></td>
            </c:otherwise>
            </c:choose>

            <c:forEach begin="1" end="${numberPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td style="padding: 12px 15px;">${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td style="padding: 12px 15px;"><a href="/cruises?command=CruisesCatalog&currentPage=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
            <c:when test="${currentPage lt numberPages}">
                <td style="padding: 12px 15px;"><a href="/cruises?command=CruisesCatalog&currentPage=${currentPage + 1}">
                <fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.next" /></a></td>
            </c:when>
            <c:otherwise>
                <td style="padding: 12px 15px;">
                <fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.next" /></td>
            </c:otherwise>
            </c:choose>
        </tr>
    </table>

</body>
</html>