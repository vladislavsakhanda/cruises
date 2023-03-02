<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

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