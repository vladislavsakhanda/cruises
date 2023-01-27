<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>

<table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <th>Клієнт</th>
            <th>Паспорт</th>
            <th>Круїз</th>
            <th>Дата</th>
            <th>Оплата</th>
            <th>Статус</th>
            <th>Зміна статусу</th>
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
                <td>${trip.date_start} по ${trip.date_end}</td>
                <c:choose>
                    <c:when test="${trip.is_paid == false}">
                        <td>Не сплачено</td>
                    </c:when>
                    <c:otherwise>
                        <td>Сплачено</td>
                    </c:otherwise>
                </c:choose>
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

                <td>
                <c:choose>
                <c:when test ="${trip.status != 3}">
                    <form action="requestsCatalog" method="POST">
                        <select name="status">
                            <c:if test="${trip.status != 0}">
                                <option value="0">На розгляді</option>
                            </c:if>
                            <c:if test="${trip.status != 1 && trip.is_paid == false}">
                                <option value="1">Потребує оплати</option>
                            </c:if>
                            <c:if test="${trip.status != 2 && trip.is_paid == false}">
                                <option value="2">Відхилено</option>
                            </c:if>
                            <c:if test="${trip.status != 3 && trip.is_paid == true}">
                                <option value="3">Підтверджено</option>
                            </c:if>
                        </select>
                        <br><br>
                        <input type="hidden" name="trip_id" value="${trip.id}" />
                        <input type="submit" value="Змінити" />
                    </form>
                </c:when>

                <c:otherwise>
                    Дії відсутні
                </c:otherwise>
                </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>