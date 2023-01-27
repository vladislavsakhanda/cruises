<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>

<html>
<head>
<title>Liners</title>
</head>
<body>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <th>Круїз</th>
            <th>Дата</th>
            <th>Ціна</th>
            <th>Місткість</th>
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
        <td><a href="cruisesCatalog?page=${currentPage - 1}">Previous</a></td>
    </c:if>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
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

    <c:if test="${currentPage lt noOfPages}">
        <td><a href="cruisesCatalog?page=${currentPage + 1}">Next</a></td>
    </c:if>

</body>
</html>