<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3>${liners[0].name}<br><br></h3>
<h4>Деталі</h4>
${liners[0].description}<br><br>
<h4>Коротка круїзна програма</h4>
<c:set var="i" value="0"/>
<c:forEach items="${linersRoute}" var="entry">
    <c:if test="${i == '0'}">
        <div>День | Порт</div><br>
    </c:if>

    ${entry.key}  ${entry.value}<br>
    <c:set var="i" value="${i + 1}"/>
</c:forEach>
<br><br>
<c:set var="liner_id" value="${liners[0].getId()}"/>
<c:set var="price" value="${liners[0].price_coefficient * i}"/>
<c:set var="date_start" value="${liners[0].getDate_end()}"/>
<c:set var="date_end" value="${liners[0].getDate_start()}"/>
Вільних місць: ${freePlaces}<br>
Ціна: ${price} $<br>

<c:choose>
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

