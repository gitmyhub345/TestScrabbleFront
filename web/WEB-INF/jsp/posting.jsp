<%-- 
    Document   : posting
    Created on : Dec 12, 2016, 12:05:09 AM
    Author     : Rider1
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
            <div class="row">
                <div id="" class="">
                    ${msg}
                </div>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <div class="col-md-8 content">
                    <div style="background-color: #DDD;">
			<!--flash messages-->
                    </div>
                    <div>
                        <h1>Hello ${user}! your avatar is: <img src="<c:url value="/resources/images/profile/${profile}"/>"></h1>
                        <c:forEach var="userPost" items="${postings}" varStatus="status">
                            <label>${status.index+1}</label> <span ><img src="<c:url value="/resources/images/profile/${userPost.get('avatar')}" />" height="25px" width="25px" style="background-color: #ff0000;"> 
                            </span><label>${userPost.get('body')}</label>

                            <!--<label>${userPost.post}</label><a href="<c:url value="/posts/${userPost.postID}" />">edit</a>-->
                            <br>
                        </c:forEach>
                    </div> 
                </div>
                <%@include file="sidepanel.jsp" %>
            </div>
    </body>
</html>
