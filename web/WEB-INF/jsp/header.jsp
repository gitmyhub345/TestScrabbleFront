<%-- 
    Document   : header
    Created on : Dec 19, 2016, 5:33:45 PM
    Author     : Rider1
--%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<div class=row>
			<div class="col-md-10 header">
                            Microblog: <!--<a href="/index">Home</a>-->
			</div>
			<div class="col-md-2 topmenu">
                            <img class="profile" id="profile" src="<c:url value="/resources/images/profile/TLuser.png" />" width="30px" height="30px">
				<div class="menu" id="menu">
                                    <sec:authorize access="isAuthenticated()" var="auth" />
                                        <c:if test="${auth == 'true'}">
					<a href="/DragonWorld/welcome">Home</a><br>
					<a href="/DragonWorld/profile">Your profile</a><br>
                                        <a href="/DragonWorld/posts">Your posts</a><br>
					<a href="/DragonWorld/logout">logout</a><br>
                                        </c:if>
                                        <c:if test="${auth == 'false'}">
                                            <c:if test="${pageContext.request.getRequestURL().substring(pageContext.request.getRequestURL().lastIndexOf('/')+1) == 'welcome.jsp'}">
                                                <a><span id="login">login</span></a><br>
                                                <a href="/DragonWorld/register">Register for an account</a>
                                            </c:if>
                                            <c:if test="${pageContext.request.getRequestURL().substring(pageContext.request.getRequestURL().lastIndexOf('/')+1) == 'register.jsp'}">
                                                <a href="/DragonWorld/welcome" >Home</a><br>
                                            </c:if>

                                        </c:if>
                                    
				</div>
			</div>
		</div>


