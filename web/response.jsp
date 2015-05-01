<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%--
    Document   : response
    Created on : Apr 22, 2015, 8:52:57 PM
    Author     : nbuser
--%>

<sql:query var="users" dataSource="jdbc/final">
    SELECT * FROM Users
    WHERE user_id = ? <sql:param value="${param.user_id}"/>
</sql:query>

<c:set var="userDetails" value="${users.rows[0]}"/>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>${userDetails.username}</title>
    </head>

    <body>
        <table>
            <tr>
                <th colspan="2">${userDetails.name}</th>
            </tr>
            <tr>
                <td><strong>Description: </strong></td>
                <td><span style="font-size:smaller; font-style:italic;">Hi I am description</span></td>
            </tr>
            <tr>
                <td><strong>Counselor: </strong></td>
                <td><strong>${userDetails.username}</strong>
                    <br><span style="font-size:smaller; font-style:italic;">
                    <em>member since: ${userDetails.create_time}</em></span></td>
            </tr>
            <tr>
                <td><strong>Contact Details: </strong></td>
                <td><strong>email: </strong>
                    <br><strong>pw: </strong>${userDetails.password}</td>
            </tr>
        </table>
    </body>
</html>