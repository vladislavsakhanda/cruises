<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<head>
    <title><fmt:message key="label.lang.registration.profile.title" /></title>
</head>

<p><fmt:message key="label.lang.registration.profile.hello" /> ${sessionScope.userSurname} ${sessionScope.userName}!
<fmt:message key="label.lang.registration.profile.helloContinue" /> ${sessionScope.userEmail}</p>
<a href="profile/logout">Log out</a><br>

<c:choose>
    <c:when test="${role != null}">
      <a href="/cruises/requestsCatalog">
      <fmt:message key="label.lang.registration.profile.admin.requestManagement" />
      </a><br>
    </c:when>
<c:otherwise>

<p><fmt:message key="label.lang.registration.profile.user.requests" />:</p>
<c:set var="trips" value="${cruisesTLD:getAllTripByUserId(sessionScope.userId)}"/>

<table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <th><fmt:message key="label.lang.registration.profile.user.number" /></th>
            <th><fmt:message key="label.lang.registration.profile.user.name" /></th>
            <th><fmt:message key="label.lang.registration.profile.user.date" /></th>
            <th><fmt:message key="label.lang.registration.profile.user.actions" /></th>
            <th><fmt:message key="label.lang.registration.profile.user.status" /></th>
        </tr>
        <c:forEach var="trip" items="${trips}">
            <tr>
                <td>${trip.id}</td>
                <td><a href="cruisesCatalog/liner?id=${trip.liner_id}">${cruisesTLD:getLinerById(trip.liner_id).name}</a></td>
                <td>${trip.date_start} по ${trip.date_end}</td>
                <td><c:choose>
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

                            <input type="submit" value="Відмінити заявку" />
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
        </c:forEach>
</table>
    </c:otherwise>
</c:choose>