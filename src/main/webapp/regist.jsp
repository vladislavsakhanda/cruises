
<html>
<head>
    <title>Insert title here</title>
</head>
<body>

<div align="center">
    <h1>Register Form</h1>
<!--    <form action="register" method="post" >-->
    <form action="validateUser.jsp" method="get">
        <table style="with: 80%">
            <tr>
                <td>Name</td>
                <td><input type="text" name="name" required="required"/></td>
            </tr>
            <tr>
                <td>Surname</td>
                <td><input type="text" name="surname" required="required"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" name="email" required="required"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password" required="required"/></td>
            </tr>
        </table>
        <input type="submit" value="register" />
    </form>
</div>
</body>
</html>