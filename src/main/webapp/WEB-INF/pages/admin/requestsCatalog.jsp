<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<head>
    <title><fmt:message key="label.lang.admin.title" /></title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<table class="styled-table">
        <thead>
        <tr>
            <th><fmt:message key="label.lang.admin.client" /></th>
            <th><fmt:message key="label.lang.admin.passport" /></th>
            <th><fmt:message key="label.lang.admin.cruise" /></th>
            <th><fmt:message key="label.lang.admin.date" /></th>
            <th><fmt:message key="label.lang.admin.payment" /></th>
            <th><fmt:message key="label.lang.admin.status" /></th>
            <th><fmt:message key="label.lang.admin.changeStatus" /></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="trip" items="${cruisesTLD:getAllTrip()}">
            <c:set var="user" value="${cruisesTLD:getUserByUserId(trip.userId)}"/>
            <c:set var="liner" value="${cruisesTLD:getLinerById(trip.linerId)}"/>

            <c:set var="image" value="${cruisesTLD:getBlobFromInputStream(trip)}"/>

            <tr>
                <td>${user.surname} ${user.name}</td>
                <td>
                ${cruisesTLD:getAndWriteTempImageOfPassport(trip, pathProjectDirectory)}

                <img src="data:image/jpg;base64,${image}" width="425" height="250"/>
                </td>
                <td>${liner.name}</td>
                <td>${trip.dateStart} <fmt:message key="label.lang.admin.to" /> ${trip.dateEnd}</td>
                <c:choose>
                    <c:when test="${trip.isPaid == false}">
                        <td><fmt:message key="label.lang.admin.notPaid" /></td>
                    </c:when>
                    <c:otherwise>
                        <td><fmt:message key="label.lang.admin.paid" /></td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${trip.status.code == 0}">
                        <td><fmt:message key="label.lang.admin.pending" /></td>
                    </c:when>
                    <c:when test="${trip.status.code == 1}">
                        <td><fmt:message key="label.lang.admin.requiresPayment" /></td>
                    </c:when>
                    <c:when test="${trip.status.code == 2}">
                        <td><fmt:message key="label.lang.admin.rejected" /></td>
                    </c:when>
                    <c:when test="${trip.status.code == 3}">
                        <td><fmt:message key="label.lang.admin.confirmed" /></td>
                    </c:when>
                </c:choose>

                <td>
                <c:choose>
                <c:when test ="${trip.status.code != 3}">
                    <form action="?command=RequestsCatalog" method="POST">
                        <select name="status">
                            <c:if test="${trip.status.code != 0}">
                                <option value="0"><fmt:message key="label.lang.admin.pending" /></option>
                            </c:if>
                            <c:if test="${trip.status.code != 1 && trip.isPaid == false}">
                                <option value="1"><fmt:message key="label.lang.admin.requiresPayment" /></option>
                            </c:if>
                            <c:if test="${trip.status.code != 2 && trip.isPaid == false}">
                                <option value="2"><fmt:message key="label.lang.admin.rejected" /></option>
                            </c:if>
                            <c:if test="${trip.status.code != 3 && trip.isPaid == true}">
                                <option value="3"><fmt:message key="label.lang.admin.confirmed" /></option>
                            </c:if>
                        </select>
                        <br><br>
                        <input type="hidden" name="tripId" value="${trip.id}" />
                        <input type="submit" value="<fmt:message key="label.lang.admin.change" />" />
                    </form>
                </c:when>

                <c:otherwise>
                    <fmt:message key="label.lang.admin.changesMissing" />
                </c:otherwise>
                </c:choose>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>