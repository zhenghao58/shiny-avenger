<%-- 
    Document   : login
    Created on : Apr 27, 2015, 4:58:37 PM
    Author     : apple
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>

    <body>
        <form method='post' action="LoginServlet">

            Please enter your username 		
            <input type="text" name="un"/><br>		

            Please enter your password
            <input type="text" name="pw"/>

            <input type="submit" value="submit">			

        </form>
    </body>
</html>
