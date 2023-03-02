<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<head>
    <title><fmt:message key="label.lang.registration.login.title" /></title>
</head>

<center>
  <div class="alert alert-success">
   <strong><fmt:message key="label.lang.registration.login.success" /> ${sessionScope.userEmail}</strong>
  </div>
</center>