<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success massage</title>
<link
 href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
 rel="stylesheet"
 integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
 crossorigin="anonymous">
</head>
<body>
 <center>
  <div class="alert alert-success">
   <strong><%=request.getAttribute("Message")%></strong>
  </div>
 </center>
</body>
</html>