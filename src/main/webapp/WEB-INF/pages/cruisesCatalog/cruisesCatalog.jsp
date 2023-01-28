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
</head>
<body>
    <form action="cruisesCatalog" method="get">
        <input type="hidden" name="action" value="change" />

        <fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.cruisesCatalog" />:
        <select name="recordsPerPage">
            <option>3</option>
            <option>5</option>
            <option>10</option>
        </select>

        <fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.duration" />:
        <select name="choseDuration">
            <option value="all">*</option>
            <c:forEach var="duration" items="${allDuration}">
                <option value="${duration}">${duration} <fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.days" /></option>
            </c:forEach>
        </select>

        З <input type="date" min='${minDate}' max='${maxDate}' value='${minDate}' name="date_start">
        По <input type="date" min='${minDate}' max='${maxDate}' value='${maxDate}' name="date_end">

        <input type="submit" value="<fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.confirm" />" />
    </form>

    <form action="cruisesCatalog" method="post">
        <input type="hidden" name="action" value="reset" />

        <input type="submit" value="<fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.reset" />" />
    </form>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.cruise" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.date" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.price" /></th>
            <th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.capacity" /></th>
        </tr>

        <c:forEach var="liner" items="${linerList}">
            <tr>
                <td><a href="cruisesCatalog/liner?id=${liner.id}">${liner.name}</a></td>
                <td>${liner.date_start} по ${liner.date_end}</td>
                <td>${cruisesTLD:countPriceLiner(liner)}$</td>
                <td>${liner.capacity}</td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${currentPage != 1}">
        <td><a href="cruisesCatalog?page=${currentPage - 1}"><th><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.capacity" /></a></td>
    </c:if>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${numberPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="cruisesCatalog?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <c:if test="${currentPage lt numberPages}">
        <td><a href="cruisesCatalog?page=${currentPage + 1}"><fmt:message key="label.lang.cruisesCatalog.cruisesCatalog.next" /></a></td>
    </c:if>

</body>
</html>