<%-- 
    Document   : Login
    Created on : Nov 14, 2016, 11:31:29 PM
    Author     : Rider1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<html>
    <head>
        <title>Spring Security Example </title>
    </head>
    <body>
        <!--<div th:if="${param.error}">
            Invalid username and password.
        </div>
        <div th:if="${param.logout}">
            You have been logged out.
        </div>-->
        <form th:action="@{/login}" method="POST">
        <!--<form name="loginForm" action="<c:url value='/j_spring_security_check' />" method="POST">-->
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
	</c:if>
	<c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
	</c:if>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div><label> User Name : <input type="text" name="username"/> </label></div>
            <div><label> Password: <input type="password" name="password"/> </label></div>
            <div><input type="submit" value="Sign In"/></div>
        </form>
    </body>
</html>
 
            