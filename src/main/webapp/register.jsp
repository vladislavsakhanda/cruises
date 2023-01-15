<% taglib prefix="s" uri="/struts-tags"; %>

<body>
<html>

<s:form action="register">
 	  <s:textfield name="personBean.firstName" label="First name" />
 	  <s:textfield  name="personBean.lastName" label="Last name" />
 	  <s:textfield name="personBean.email"  label ="Email"/>
 	  <s:textfield name="personBean.age"  label="Age"  />
   	  <s:submit/>
</s:form>

</body>
</html>