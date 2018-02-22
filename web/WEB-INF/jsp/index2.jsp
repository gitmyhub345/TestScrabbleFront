<%-- 
    Document   : index
    Created on : Dec 7, 2016, 10:40:04 PM
    Author     : Rider1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!!!</h1>
        <div>${msg}</div><br>
        <div><c:forEach var="item" items="${prm}" >
                ${item} <br />
            </c:forEach>
        </div>
    </body>
</html>
