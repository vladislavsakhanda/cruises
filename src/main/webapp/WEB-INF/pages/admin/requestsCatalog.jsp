<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<head>
    <title><fmt:message key="label.lang.admin.title" /></title>
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