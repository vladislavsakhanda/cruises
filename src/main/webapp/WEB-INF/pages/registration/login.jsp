<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<html>
<head>
    <title><fmt:message key="label.lang.registration.login.title" /></title>
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