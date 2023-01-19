<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib uri="/struts-tags" prefix="s" %>

<p>Привіт, ${sessionScope.userSurname} ${sessionScope.userName}! Ви увійшли на сайт з пошти ${sessionScope.userEmail}</p>

<a href="logout">Log out</a>
