<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<head>
    <title><fmt:message key="label.lang.registration.profile.title" /></title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>

<div id="indent">
<p><fmt:message key="label.lang.registration.profile.hello" /> ${sessionScope.userSurname} ${sessionScope.userName}!
<fmt:message key="label.lang.registration.profile.helloContinue" /> ${sessionScope.userEmail}</p>
<a href="profile/logout"><fmt:message key="label.lang.registration.profile.user.logout" /></a>

<c:choose>
    <c:when test="${role != null}">
      <br><br><a href="/cruises/requestsCatalog">
      <fmt:message key="label.lang.registration.profile.admin.requestManagement" />
      </a><br>
    </c:when>
<c:otherwise>

<c:set var="trips" value="${cruisesTLD:getAllTripByUserId(sessionScope.userId)}"/>

<table class="styled-table">
        <thead>
        <tr>
            <th><fmt:message key="label.lang.registration.profile.user.number" /></th>
            <th><fmt:message key="label.lang.registration.profile.user.name" /></th>
            <th><fmt:message key="label.lang.registration.profile.user.date" /></th>
            <th><fmt:message key="label.lang.registration.profile.user.actions" /></th>
            <th><fmt:message key="label.lang.registration.profile.user.status" /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="trip" items="${trips}">
            <tr>
                <td>${trip.id}</td>
                <td><a href="cruisesCatalog/liner?id=${trip.liner_id}">${cruisesTLD:getLinerById(trip.liner_id).name}</a></td>
                <td>${trip.date_start} по ${trip.date_end}</td>
                <td style="align: center;"><c:choose>
                   <c:when test="${trip.status == 1}">
                         <form action="profile" method="POST">
                            <input type="hidden" name="action" value="changePayment">
                            <input type="hidden" name="id" value="${trip.id}">

                            <input type="submit" value="Сплатити" />
                         </form>
                   </c:when>
                   <c:when test="${trip.is_paid == false}">
                       <form action="profile" method="POST">
                            <input type="hidden" name="action" value="removeRequest">
                            <input type="hidden" name="id" value="${trip.id}">

                            <input type="submit" style="align: center;" value="<fmt:message key="label.lang.registration.profile.user.cancelRequest" />" />
                        </form>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.lang.registration.profile.user.actionsUnavailable" />
                    </c:otherwise>
                </c:choose></td>
                <c:choose>
                    <c:when test="${trip.status == 0}">
                        <td><fmt:message key="label.lang.registration.profile.user.status.pending" /></td>
                    </c:when>
                    <c:when test="${trip.status == 1}">
                        <td><fmt:message key="label.lang.registration.profile.user.status.requiresPayment" /></td>
                   </c:when>
                   <c:when test="${trip.status == 2}">
                        <td><fmt:message key="label.lang.registration.profile.user.status.rejected" /></td>
                   </c:when>
                   <c:when test="${trip.status == 3}">
                        <td><fmt:message key="label.lang.registration.profile.user.status.confirmed" /></td>
                   </c:when>
                </c:choose>
            </tr>
            </tbody>
        </c:forEach>
</table>
    </c:otherwise>
</c:choose>

</div>