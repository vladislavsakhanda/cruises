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
    <h3><fmt:message key="label.lang.registration.register.registration" /></h3>

    <form action="register" method="post">
        <c:if test="${requestScope.messageName != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messageName}" /></div>
        </c:if>
        <fmt:message key="label.lang.registration.register.name" />: <input name="name" value="${name}"/>
        <br><br>
        <c:if test="${requestScope.messageSurname != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messageSurname}" /></div>
        </c:if>
        <fmt:message key="label.lang.registration.register.surname" />: <input name="surname" value="${surname}"/>
        <br></br>
        <c:if test="${requestScope.messageEmail != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messageEmail}" /></div>
        </c:if>
        <fmt:message key="label.lang.registration.register.email" />: <input name="email" value="${email}" />
        <br></br>
        <c:if test="${requestScope.messagePassword != null}">
            <div id="messageValidation"><fmt:message key="${requestScope.messagePassword}" /></div>
        </c:if>
        <fmt:message key="label.lang.registration.register.password" />: <input name="password"  type="password" id="myInput" />
        <input type="checkbox" onclick="hidePassword()"><fmt:message key="label.lang.registration.register.showPassword" />
        <br></br>
        <input type="submit" value="Register" />
    </form>
  </body>
</html>