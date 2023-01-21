<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${sessionScope.userEmail != null}">
<a href="test2">Замовити</a>
</c:if>

<h3>${liners[1].name}<br><br></h3>
<h4>Деталі</h4>
${liners[1].description}<br><br>
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
<c:set var="price" value="${liners[1].price_coefficient * i}"/>
Ціна: ${price} $


