<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body  >
        <%@include file="scripts.jsp" %>
        <div class="container">
            <%@include file="header.jsp"%>
            <hr>
            <c:if test="${! empty msg}">
                ${msg}
            </c:if>
            <div class="row">
            <%@include file="mainpanel.jsp" %>
                <div id="" class="">
                    <c:url var="faction" value="/register/add" />
                    <form:form name="registration" action="${faction}" method="post">
                        <form:label path="username">username:</form:label><form:input path="username" size="25"></form:input><br>
                        <form:label path="password">password:</form:label><form:input type="password" path="password" size="25"></form:input><br>
                        <label for="retype">retype password:</label><input type="password" name="retype" size="25" /><br>
                        <input type="submit" value="register">
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
