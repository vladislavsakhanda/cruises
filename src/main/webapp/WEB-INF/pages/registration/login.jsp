<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>

<s:form action="loginprocess">
      <s:textfield name="userBean.email"  label ="Email"/>
      <s:textfield name="userBean.password"  label="Password"/>
      <s:submit/>
</s:form>