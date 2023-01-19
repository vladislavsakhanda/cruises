<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>

<s:form action="loginprocess">
    <s:textfield name="email" label="Email"></s:textfield>
    <s:password name="password" label="Password"></s:password>
<s:submit value="login"></s:submit>
</s:form>