<%-- 
    Document   : welcome
    Created on : Dec 7, 2016, 3:55:34 PM
    Author     : Rider1
--%>

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
            <div class="row">
                <script type="text/javascript">
                    //var cart = <c:out value="${cart}"></c:out>
                    
                    function additem(item){
                        alert(item);
                    //    cart.addItem(item);
                    }
                </script>
                ${cart.getCartID()} has ${cart.numItems()} items.<br><input type="button" value="add item" onclick="additem('1234')}">
                <br><c:forEach var = "item" items="${cart.getItems()}">
                    ${item}<br>
                </c:forEach>
            </div>
            <hr>
            <div class="row">
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <c:if test="${not empty msg2}">
                    <div class="msg">${msg2}</div>
                </c:if>
                <%@include file="mainpanel.jsp" %>
                <%@include file="sidepanel.jsp" %>
            </div>
            <div id="loginModalForm" class="ModalForm">
                <div id="loginModalContent" class="ModalContent">
                    <div id="ModalSpan" class="ModalHeader">
                        <span>Login Form</span>
                        <span class="close" id="close">×</span>
                    </div>
                    <form action="/DragonWorld/welcome" method="POST" id="loginForm" class="loginForm">
                        <p>all fields are mandatory</p>
                        <fieldset class="ModalFieldset">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <label class="loginFormLabel" for="username">Name:</label><input id="name" size="15" name="username"><br>
                            <label class="loginFormLabel" for="password">Password:</label><input id="password" type="text" size="20" name="password"><br>
                        </fieldset>
                        
                            <hr>
                            <input type="button" value="Cancel" id="ModalFormCancel" style="position: relative; float:right; right:10px;">
                            <input type="submit" value="submit" style="position: relative; float:right;right:25px;">
                        
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
