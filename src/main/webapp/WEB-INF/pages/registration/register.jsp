<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">
<s:head />

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><fmt:message key="label.lang.registration.register.title" /></title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
  </head>
  <body>
    <h3><fmt:message key="label.lang.registration.register.registration" /></h3>

    <form action="register" method="post">
       <div id="messageValidation">${requestScope.messageName}</div>
        <fmt:message key="label.lang.registration.register.name" />: <input name="name" />
        <br><br>
        <div id="messageValidation">${requestScope.messageSurname}</div>
        <fmt:message key="label.lang.registration.register.surname" />: <input name="surname" />
        <br></br>
        <div id="messageValidation">${requestScope.messageEmail}</div>
        <fmt:message key="label.lang.registration.register.email" />: <input name="email" />
        <br></br>
        <div id="messageValidation">${requestScope.messagePassword}</div>
        <fmt:message key="label.lang.registration.register.password" />: <input name="password" />
        <br></br>
        <input type="submit" value="Register" />
    </form>
  </body>
</html>