<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<head>
    <title><fmt:message key="label.lang.registration.profile.title" /></title>
</head>

<div id="indent">
<p><fmt:message key="label.lang.registration.profile.hello" /> ${sessionScope.userSurname} ${sessionScope.userName}!
<fmt:message key="label.lang.registration.profile.helloContinue" /> ${sessionScope.userEmail}</p>
<a href="/cruises?command=LogOut"><fmt:message key="label.lang.registration.profile.user.logout" /></a>

<c:choose>
    <c:when test="${role != null}">
      <br><br><a href="/cruises?command=RequestsCatalog">
      <fmt:message key="label.lang.registration.profile.admin.requestManagement" />
      </a><br>
    </c:when>
<c:otherwise>

<c:set var="trips" value="${cruisesTLD:getAllTripByUserId(sessionScope.userId)}"/>

<c:if test="${fn:length(trips) != 0}">
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
                <td><a href="/cruises?command=CruisesCatalogLiner&id=${trip.linerId}">${cruisesTLD:getLinerById(trip.linerId).name}</a></td>
                <td>${trip.dateStart} <fmt:message key="label.lang.admin.to" /> ${trip.dateEnd}</td>
                <td style="align: center;"><c:choose>
                   <c:when test="${trip.status.code == 1}">
                         <form action="?command=Profile" method="POST">
                            <input type="hidden" name="action" value="changePayment">
                            <input type="hidden" name="id" value="${trip.id}">

                            <input type="submit" value="<fmt:message key="label.lang.registration.profile.user.pay" />" />
                         </form>
                   </c:when>
                   <c:when test="${trip.isPaid == false}">
                       <form action="?command=Profile" method="POST">
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
                    <c:when test="${trip.status.code == 0}">
                        <td><fmt:message key="label.lang.registration.profile.user.status.pending" /></td>
                    </c:when>
                    <c:when test="${trip.status.code == 1}">
                        <td><fmt:message key="label.lang.registration.profile.user.status.requiresPayment" /></td>
                   </c:when>
                   <c:when test="${trip.status.code == 2}">
                        <td><fmt:message key="label.lang.registration.profile.user.status.rejected" /></td>
                   </c:when>
                   <c:when test="${trip.status.code == 3}">
                        <td><fmt:message key="label.lang.registration.profile.user.status.confirmed" /></td>
                   </c:when>
                </c:choose>
            </tr>
            </tbody>
        </c:forEach>
</table>
</c:if>
    </c:otherwise>
</c:choose>


</div>