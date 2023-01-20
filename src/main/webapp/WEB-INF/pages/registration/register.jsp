<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<s:head />

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Register</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
  </head>
  <body>
    <h3>Реєстрація</h3>

    <form action="register">
       <div id="messageValidation">${requestScope.messageName}</div>
        First Name: <input name="name" />
        <br><br>
        <div id="messageValidation">${requestScope.messageSurname}</div>
        Last Name: <input name="surname" />
        <br></br>
        <div id="messageValidation">${requestScope.messageEmail}</div>
        Email: <input name="email" />
        <br></br>
        <div id="messageValidation">${requestScope.messagePassword}</div>
        Password: <input name="password" />
        <br></br>
        <input type="submit" value="Register" />
    </form>
  </body>
</html>