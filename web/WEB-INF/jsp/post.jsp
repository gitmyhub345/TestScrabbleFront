<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta name="_csrf" content="${_csrf.token}"/>
        <title>Post Page</title>
    </head>
    <body  >
        <%@include file="scripts.jsp" %>
        <div class="container">
            <%@include file="header.jsp"%>
            <hr>
            <div class="row">
                <div class="msg">
                <c:if test="${not empty error}">
                    ${error}
                </c:if>
                <c:if test="${not empty msg}">
                    ${msg}
                </c:if>
                </div>
                <div id="postmenu" class="postmenu">
                    <ul>
                        <li id="addPost" class="hmenu">add post</li>
                        <li id="editPost" class="hmenu">edit post</li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <!--Begin mainpanel substitution-->
                <div class="col-md-8 content" >
                    <div style="background-color: #DDD;">
                            <!--flash messages-->
                    </div>
                    <div>${postmsg}<br>
                        <div id="postListing">
                            <c:if test="${empty posts}" ><label class="message">You have not posted any messages yet. Please post some messages to view them here.</label></c:if>
                            <c:forEach items="${posts}" var="post" varStatus="status">
                                <div id="post${post.get('postId')}">
                                    <c:if test="${post.get('u') == authNo }">
                                        <img class="hoveredit" src="resources/images/garbagecan.png" width=30dp height=30dp data-link="delete ${post.get('postid')}">
                                        <img class="hoveredit" src="resources/images/editpencil.png" width=30dp height=30dp data-link="edit ${post.get('postid')}">
                                        <img src="<c:url value="/resources/images/profile/${post.get('avatar')}"/>" width="30dp" height="30dp" >
                                    </c:if>
                                    <label>${post.get('nickname')} posted:</label> <label class="post"> ${post.get('postBody')}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>                
            <!--end mainpanel substitution -->
                <%@include file="sidepanel.jsp" %>
            </div>
            <div id="PostModalForm" class="ModalForm">
                <div id="PostModalContent" class="ModalContent">
                    <div id="ModalSpan" class="ModalHeader">
                        <span>Add Posts Form</span>
                        <span class="close" id="close">×</span>
                    </div>
                    <f:form action="/DragonWorld/posts/add" method="POST" id="PostForm" class="PostForm">
                        <p>please edit the fields your wish to change.</p>
                        <fieldset class="ModalFieldset">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <f:label class="FormLabel" path="post">message:</f:label> <f:input size="50" path="post"></f:input><br>
                            <f:label class="FormLabel" path="language">language:</f:label> <f:input size="20" path="language"></f:input><br>
                            <f:label class="FormLabel" path="isPrivate">Private:</f:label> <f:checkbox path="isPrivate"></f:checkbox><br>
                        </fieldset>
                        
                            <hr>
                            <input type="button" value="Cancel" id="ModalFormCancel" style="position: relative; float:right; right:10px;">
                            <input type="submit" value="submit" style="position: relative; float:right;right:25px;">
                        
                    </f:form>
                </div>
            </div>
        </div>
    </body>
</html>
