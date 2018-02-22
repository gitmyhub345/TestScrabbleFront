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
            <div class="row">
                <%@include file="mainpanel.jsp" %>
                <div class="col-md-4 hidden-xs sidebar" id="ProfileForm">
                    <c:if test="${command.getProfileID() == 0}"><c:url var="faction" value="/profile/create" /></c:if>
                    <c:if test="${command.getProfileID() > 0}"><c:url var="faction" value="/profile/update" /></c:if>
                    <form:form name="UserProfile" action="${faction}" method="post" enctype="multipart/form-data" >
                        <form:label path="nickname">Nickname:</form:label><form:input type="text" path="nickname"></form:input><br>
                        <label for="avatarupload">Avatar:</label><input type="file" name="avatarupload" size="25dp"><br>
                        <c:if test="${!empty command.getAvatar() }">current Avatar: <img src="<c:url value="/resources/images/profile/${command.getAvatar()}"/>" widht="30pd" height="30pd"><br>your profile id is: ${command.getProfileID()}<br></c:if>
                        <form:label path="aboutMe">About me:</form:label><form:input type="text" path="aboutMe"></form:input><br>
                        <c:if test="${command.getProfileID() == 0}"><input type ="submit" value="create"></c:if>
                        <c:if test="${command.getProfileID() > 0}"><input type ="submit" value="update"></c:if>

                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
