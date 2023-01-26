<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>

<p>Привіт, ${sessionScope.userSurname} ${sessionScope.userName}! Ви увійшли на сайт з пошти ${sessionScope.userEmail}</p>
<a href="profile/logout">Log out</a><br>

<c:choose>
    <c:when test="${role != null}">
      <a href="/cruises/requestsCatalog">Каталог заявок</a><br>
    </c:when>
    <c:otherwise>

<p>Наявні заявки:</p>
<c:set var="trips" value="${cruisesTLD:getAllTripByUserId(sessionScope.userId)}"/>

<table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <th>Номер</th>
            <th>Назва</th>
            <th>Дата</th>
            <th>Дії</th>
            <th>Статус</th>
        </tr>
        <c:forEach var="trip" items="${trips}">
            <tr>
                <td>${trip.id}</td>
                <td><a href="cruisesCatalog/liner?id=${trip.liner_id}">${cruisesTLD:getLinerById(trip.liner_id).name}</a></td>
                <td>${trip.date_start} по ${trip.date_end}</td>
                <c:choose>
                   <c:when test="${trip.status == 1}">
                     <td><a href="/cruises/profile/remove/request?id=${trip.id}">Відмінити заявку</a></td>
                     <td><a href="/cruises/profile/pay?id=${trip.id}">Сплатити</a></td>
                   </c:when>
                   <c:otherwise>
                      <td><a href="/cruises/profile/remove/request?id=${trip.id}">Відмінити заявку</a></td>
                   </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${trip.status == 0}">
                        <td>На розгляді</td>
                    </c:when>
                    <c:when test="${trip.status == 1}">
                        td>Потребує оплати</td>
                   </c:when>
                   <c:when test="${trip.status == 2}">
                        <td>Відхилено</td>
                   </c:when>
                   <c:otherwise>
                        <td>Підтверджено</td>
                   </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
</table>
    </c:otherwise>
</c:choose>