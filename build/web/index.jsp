<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%--
    Document   : index
    Created on : Apr 22, 2015, 7:39:49 PM
    Author     : nbuser
--%>


<sql:query var="users" dataSource="jdbc/final">
    SELECT username, user_id FROM Users
</sql:query>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>IFPWAFCAD Homepage</title>
    </head>
    <body>
        <h1>Welcome to <strong>IFPWAFCAD</strong>, the International Former
            Professional Wrestlers' Association for Counseling and Development!
        </h1>

        <table border="0">
            <thead>
                <tr>
                    <th>IFPWAFCAD offers expert counseling in a wide range of fields.</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>To view the contact details of an IFPWAFCAD certified former
                        professional wrestler in your area, select a subject below:</td>
                </tr>
                <tr>
                    <td>
                        <form action="response.jsp">
                            <strong>Select a subject:</strong>
                            <select name="user_id">
                                <c:forEach var="row" items="${users.rows}">
                                    <option value="${row.user_id}">${row.username}</option>
                                </c:forEach>
                            </select>
                            <textarea name='message' placeholder="Message" required></textarea>
                            <input type="submit" value="submit" name="submit" />
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>