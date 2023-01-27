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
                        Наразі дії недоступні
                    </c:otherwise>
                </c:choose></td>
                <c:choose>
                    <c:when test="${trip.status == 0}">
                        <td>На розгляді</td>
                    </c:when>
                    <c:when test="${trip.status == 1}">
                        <td>Потребує оплати</td>
                   </c:when>
                   <c:when test="${trip.status == 2}">
                        <td>Відхилено</td>
                   </c:when>
                   <c:when test="${trip.status == 3}">
                        <td>Підтверджено</td>
                   </c:when>
                </c:choose>
            </tr>
        </c:forEach>
</table>
    </c:otherwise>
</c:choose>