<%@ include file="/WEB-INF/pages/templates/registrationTemplate.jsp" %>

<html>
<head>
    <title><fmt:message key="label.lang.cruisesCatalog.bookTour.title" /></title>
</head>
<body>
    <div id="center">
    <h5><fmt:message key="label.lang.cruisesCatalog.bookTour.providePassport" /></h5>
    <form method="POST" class="form-group" action="?command=BookTour" enctype="multipart/form-data">
     <input type="hidden" name="linerId" value="${linerId}" />
     <input type="hidden" name="price" value="${price}" />
     <input type="hidden" name="dateStart" value="${dateStart}" />
     <input type="hidden" name="dateEnd" value="${dateEnd}" />
     <div class="form-group">
      <label for="Profile Photo"></label>
      <input type="file" class="form-control" accept="image/*" name="passport" required/>

      <c:if test="${requestScope.errorMessageUpload != null}">
              <div id="messageValidation"><fmt:message key="${requestScope.errorMessageUpload}" /></div>
      </c:if>
     </div>
     <input type="submit" value="<fmt:message key="label.lang.cruisesCatalog.bookTour.confirmRequest" />" class="btn btn-success">
    </form>
    </div>

</body>
</html>