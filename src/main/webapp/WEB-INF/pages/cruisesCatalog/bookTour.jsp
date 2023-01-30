<jsp:include page="/WEB-INF/pages/templates/registrationTemplate.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<html lang="${lang}">

<html>
<head>
    <title><fmt:message key="label.lang.cruisesCatalog.bookTour.title" /></title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
    <div id="center">
    <h5><fmt:message key="label.lang.cruisesCatalog.bookTour.providePassport" /></h5>
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
    </div>
</body>
</html>