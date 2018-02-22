<%-- 
    Document   : mainpanel
    Created on : Dec 21, 2016, 4:36:21 PM
    Author     : Rider1
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="col-md-8 content" >
    <div style="background-color: #DDD;">
            <!--flash messages-->
    </div>
    <div>${postmsg}<br>
        <div id="postListing">
            <c:if test="${empty posts}" >You have not posted any messages yet. Please post some messages to view them here.</c:if>
            <c:forEach items="${posts}" var="post" varStatus="status">
                <label>${post.get('nickname')} posted: </label><label class="post">${post.get('postbody')}</label><img src="<c:url value="/resources/images/profile/${post.get('avatar')}"/>" width="30dp" height="30dp" data-link="${post.get('postid')}"><br>
            </c:forEach>
        </div>
    </div>
</div>