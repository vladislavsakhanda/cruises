<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<head>
    <title><fmt:message key="label.lang.cruisesCatalog.cruisesCatalogLiner.title" /></title>
</head>

<body>

<form action="?command=NewCruise" method="POST">

<br>
<b><fmt:message key="label.lang.admin.newCruise.name" />:</b><input name="name" id="myInput" required/>
<fmt:message key="${requestScope.messageName}" />
<br><br>

<b><fmt:message key="label.lang.admin.newCruise.StartCruise" />:</b> <input type="date" min='${cruisesTLD:getCurrentDate()}' max='${cruisesTLD:getCurrentDatePlusYear()}' name="dateStart" required>
<fmt:message key="${requestScope.messageDateStart}" />
<br><br>

<b><fmt:message key="label.lang.admin.newCruise.EndCruise" />:</b> <input type="date" min='${cruisesTLD:getCurrentDate()}' max='${cruisesTLD:getCurrentDatePlusYear()}' name="dateEnd" required>
<fmt:message key="${requestScope.messageDateEnd}" />
<br><br>

<div><input type="submit" value="<fmt:message key="label.lang.admin.newCruise.AddCruise" />" /></div>
</form>

</body>




