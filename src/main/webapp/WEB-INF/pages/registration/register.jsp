<%@ taglib prefix="s" uri="/struts-tags" %>
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

    <s:form action="register">
      <s:textfield name="userBean.name" label="First name" />
      <s:textfield name="userBean.surname" label="Last name" />
      <s:textfield name="userBean.email"  label ="Email"/>
      <s:textfield name="userBean.password"  label="Password"  />
      <s:submit/>
    </s:form>
  </body>
</html>