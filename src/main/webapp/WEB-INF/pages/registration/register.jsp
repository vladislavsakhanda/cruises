<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

  <body>

    <div id="form">
    <h3 id="formTitle"><fmt:message key="label.lang.registration.register.registration" /></h3>
    <form action="?command=Register" method="POST">
        <div><fmt:message key="label.lang.registration.register.name" />:</div>
        <span><input name="name" value="${name}"/></span>
        <c:if test="${requestScope.messageName != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messageName}" /></div>
        </c:if>

        <div><fmt:message key="label.lang.registration.register.surname" />:</div>
        <span><input name="surname" value="${surname}"/></span>
        <c:if test="${requestScope.messageSurname != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messageSurname}" /></div>
        </c:if>

        <div><fmt:message key="label.lang.registration.register.email" />:</div>
        <span><input name="email" value="${email}" /></span>
        <c:if test="${requestScope.messageEmail != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messageEmail}" /></div>
        </c:if>

        <div><fmt:message key="label.lang.registration.register.password" />:</div>
        <span><input name="password"  type="password" id="myInput" /></span>
        <c:if test="${requestScope.messagePassword != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messagePassword}" /></div>
        </c:if>
        <div><input type="checkbox" onclick="hidePassword()"><fmt:message key="label.lang.registration.register.showPassword" /></div>

        <div><input type="submit" value="Register" /></div>
    </form>

    </div>
  </body>
</html>