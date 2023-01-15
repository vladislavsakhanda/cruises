<html>
    <head>
       <title>Accept User Page</title>
    </head>
    <body>
        <h1>Verifying Details</h1>

        <jsp:useBean id="v" class="com.my.classes.Validation"/>

        <jsp:setProperty name="v" property="name"/>

        <p>Username : <jsp:getProperty name="v" property="name"/></p>

        <%if(v.validate()){%>
            Welcome! You are a VALID USER<br/>
        <%}else{%>
            Error! You are an INVALID USER<br/>
        <%}%>
    </body>
</html>