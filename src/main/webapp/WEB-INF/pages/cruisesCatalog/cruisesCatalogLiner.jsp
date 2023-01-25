<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cruisesTLD" uri="/WEB-INF/tlds/cruises.tld" %>

<c:set var="liner" value="${cruisesTLD:getLinerById(id)}"/>
<c:set var="liner_id" value="${liner.getId()}"/>
<c:set var="price" value="${cruisesTLD:countPriceLiner(liner)}"/>
<c:set var="date_start" value="${liner.getDate_end()}"/>
<c:set var="date_end" value="${liner.getDate_start()}"/>
<c:set var="freePlaces" value="${cruisesTLD:getLinerFreePlaces(liner)}"/>

<h3>${liner.name}<br><br></h3>
<h4>Деталі</h4>
${liner.description}<br><br>
<h4>Коротка круїзна програма</h4>

<div>День | Порт</div><br>
<c:forEach items="${cruisesTLD:getLinerRouteInMap(liner)}" var="entry">
    ${entry.key}  ${entry.value}<br>
</c:forEach>
<br><br>
Дата: ${date_start} по ${date_end}<br>
Вільних місць: ${freePlaces}<br>
Ціна: ${price} $<br>

<c:set var="trip" value="${cruisesTLD:getTripByUserIdAndLinerId(sessionScope.userId, liner_id)}"/>
<c:choose>
    <c:when test="${trip != null}">
        <p>Ваша заявка на цей круїз вже існує. Ви можете перевірити її статус в профілі.</p>
    </c:when>
    <c:when test="${freePlaces == 0}">
        <p>На даний момент всі місця зайняті, Ви можете обрати будь-який інший круїз.<p>
    </c:when>
    <c:when test="${sessionScope.userEmail == null}">
        <p>Тільки авторизовані користувачі можуть оформляти заявки.<p>
    </c:when>
    <c:otherwise>
        <form method="get" action="bookTour">
                    <input type="hidden" name="liner_id" value="${liner_id}" />
                    <input type="hidden" name="price" value="${price}" />
                    <input type="hidden" name="date_start" value="${date_start}" />
                    <input type="hidden" name="date_end" value="${date_end}" />
                    <input type='submit' name="Submit" value="Оформити заявку в круїз" />
            </form>
    </c:otherwise>
</c:choose>
<br>
<a href="/cruises/cruisesCatalog">Обрати інший круїз</a>



