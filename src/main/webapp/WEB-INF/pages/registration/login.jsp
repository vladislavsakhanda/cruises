<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><fmt:message key="label.lang.registration.login.title" /></title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </head>

<script>
    function hidePassword() {
      var x = document.getElementById("myInput");
      if (x.type === "password") {
        x.type = "text";
      } else {
        x.type = "password";
      }
    }
</script>

  <div id="form">
  <body>
    <h3><fmt:message key="label.lang.registration.login.login" /></h3>

    <form action="?command=Login" method="POST">

        <div><fmt:message key="label.lang.registration.login.email" />:</div>
        <span><input name="email" value="${email}" /></span>
        <c:if test="${requestScope.messageEmail != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messageEmail}" /></div>
        </c:if>

        <div><fmt:message key="label.lang.registration.login.password" />:</div>
        <input name="password" type="password" id="myInput" />
        <c:if test="${requestScope.messagePassword != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messagePassword}" /></div>
        </c:if>
        <div><input type="checkbox" onclick="hidePassword()"><fmt:message key="label.lang.registration.register.showPassword" /></div>

        <c:if test="${requestScope.messageErrorLogin != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messageErrorLogin}" /></div>
        </c:if>

        <div><input type="submit" value="<fmt:message key="label.lang.registration.login.signIn" />" /></div>
    </form>

    </div>
  </body>
</html>