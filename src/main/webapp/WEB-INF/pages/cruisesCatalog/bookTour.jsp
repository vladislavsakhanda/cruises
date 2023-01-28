<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<html>
<head>
    <title><fmt:message key="label.lang.cruisesCatalog.bookTour.title" /></title>
    <link
     href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
     rel="stylesheet"
     integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
     crossorigin="anonymous">
</head>
<body>
    <div><fmt:message key="label.lang.cruisesCatalog.bookTour.providePassport" /></div>
    <form method="post" class="form-group" action="bookTour" enctype="multipart/form-data">
     <input type="hidden" name="liner_id" value="${liner_id}" />
     <input type="hidden" name="price" value="${price}" />
     <input type="hidden" name="date_start" value="${date_start}" />
     <input type="hidden" name="date_end" value="${date_end}" />
     <div class="form-group">
      <label for="Profile Photo"></label>
      <input type="file" accept="image/*" name="passport" required/>
     </div>
     <input type="submit" value="<fmt:message key="label.lang.cruisesCatalog.bookTour.confirmRequest" />" class="btn btn-success">
    </form>
</body>
</html>