<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>

<head>
<meta charset="UTF-8">
<title>test.jsp</title>
</head>

<body>
    <c:forEach items="${liners}" var="liner">
        <tr>
            <h3><td>${liner.name}</td><br></h3>
            <td>${liner.description}</td><br>
            <td>${liner.capacity}</td><br>
            <td>${liner.route}</td><br>
            <td>${liner.id}</td><br>
        </tr>
        <br>
    </c:forEach>

    <c:forEach items="${schedules}" var="schedule">
        <tr>
            <h3><td>${schedule.liner_id}<td></h3>
            <td>${schedule.route_and_schedule}</td>
        </tr>
        <br>
    </c:forEach>
</body>

</html>
