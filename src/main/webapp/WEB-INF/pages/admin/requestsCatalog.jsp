<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<head>
    <title><fmt:message key="label.lang.admin.title" /></title>
</head>

<table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <th><fmt:message key="label.lang.admin.client" /></th>
            <th><fmt:message key="label.lang.admin.passport" /></th>
            <th><fmt:message key="label.lang.admin.cruise" /></th>
            <th><fmt:message key="label.lang.admin.date" /></th>
            <th><fmt:message key="label.lang.admin.payment" /></th>
            <th><fmt:message key="label.lang.admin.status" /></th>
            <th><fmt:message key="label.lang.admin.changeStatus" /></th>
        </tr>

        <c:forEach var="trip" items="${cruisesTLD:getAllTrip()}">
            <c:set var="user" value="${cruisesTLD:getUserByUserId(trip.user_id)}"/>
            <c:set var="liner" value="${cruisesTLD:getLinerById(trip.liner_id)}"/>

            <tr>
                <td>${user.surname} ${user.name}</td>
                <td>
                ${cruisesTLD:getAndWriteTempImageOfPassport(trip, pathProjectDirectory)}
                <img src="./images/${trip.id}_temp.jpg" width="250" height="250"/>
                </td>
                <td>${liner.name}</td>
                <td>${trip.date_start} <fmt:message key="label.lang.admin.to" /> ${trip.date_end}</td>
                <c:choose>
                    <c:when test="${trip.is_paid == false}">
                        <td><fmt:message key="label.lang.admin.notPaid" /></td>
                    </c:when>
                    <c:otherwise>
                        <td><fmt:message key="label.lang.admin.paid" /></td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${trip.status == 0}">
                        <td><fmt:message key="label.lang.admin.pending" /></td>
                    </c:when>
                    <c:when test="${trip.status == 1}">
                        <td><fmt:message key="label.lang.admin.requiresPayment" /></td>
                    </c:when>
                    <c:when test="${trip.status == 2}">
                        <td><fmt:message key="label.lang.admin.rejected" /></td>
                    </c:when>
                    <c:when test="${trip.status == 3}">
                        <td><fmt:message key="label.lang.admin.confirmed" /></td>
                    </c:when>
                </c:choose>

                <td>
                <c:choose>
                <c:when test ="${trip.status != 3}">
                    <form action="requestsCatalog" method="POST">
                        <select name="status">
                            <c:if test="${trip.status != 0}">
                                <option value="0"><fmt:message key="label.lang.admin.pending" /></option>
                            </c:if>
                            <c:if test="${trip.status != 1 && trip.is_paid == false}">
                                <option value="1"><fmt:message key="label.lang.admin.requiresPayment" /></option>
                            </c:if>
                            <c:if test="${trip.status != 2 && trip.is_paid == false}">
                                <option value="2"><fmt:message key="label.lang.admin.rejected" /></option>
                            </c:if>
                            <c:if test="${trip.status != 3 && trip.is_paid == true}">
                                <option value="3"><fmt:message key="label.lang.admin.confirmed" /></option>
                            </c:if>
                        </select>
                        <br><br>
                        <input type="hidden" name="trip_id" value="${trip.id}" />
                        <input type="submit" value="<fmt:message key="label.lang.admin.change" />" />
                    </form>
                </c:when>

                <c:otherwise>
                    <fmt:message key="label.lang.admin.changesMissing" />
                </c:otherwise>
                </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>