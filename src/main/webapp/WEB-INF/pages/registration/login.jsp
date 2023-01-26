<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Login</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
  </head>
  <body>
    <h3>Увійти</h3>

    <form action="login" method="post">
        <div id="messageValidation">${requestScope.messageEmail}</div>
        Email: <input name="email" />
        <br></br>
        <div id="messageValidation">${requestScope.messagePassword}</div>
        Password: <input name="password" />
        <br></br>
        <input type="submit" value="Login" />
    </form>
    <div id="messageValidation">${requestScope.messageErrorLogin}</div>
  </body>
</html>