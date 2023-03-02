<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<html>
  <head>
    <title><fmt:message key="label.lang.registration.register.title" /></title>
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