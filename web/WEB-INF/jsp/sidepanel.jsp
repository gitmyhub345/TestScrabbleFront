<%-- 
    Document   : sidepanel
    Created on : Dec 19, 2016, 6:55:42 PM
    Author     : Rider1
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-4 hidden-xs sidebar" >
        sidebar - more navigation?<br>ajax search here<br>
        <input class=search type="text" name="search" id="search" size=30>
        <img class=search id="searchuser" src="<c:url value="/resources/images/search.png"/>" heigth="30px" width="70px">
        <div id="usersearchlist"></div>
</div>
