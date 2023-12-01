<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["lang"]}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${requestScope["loginFlag"]}' var="loginFlag"/>
<c:if test="${not empty loginFlag}">
    <c:redirect url="/controller?command=get_films"/>
</c:if>
<c:set value='${requestScope["error"]}' var="error"/>
<html>
<head>
    <title><fmt:message key="login_header"/></title>
</head>
<body>
<div>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="do_login">
        <h1><fmt:message key="login_header"/></h1>
        <div>
            <label for="name"><fmt:message key="name"/></label>
            <input type="text" id="name" name="name" required/>
        </div>
        <div>
            <label for="password"><fmt:message key="password"/></label>
            <input type="password" id="password" name="password" required/>
        </div>
        <div>${error}</div>
        <button type="submit">
            <fmt:message key="login_button"/>
        </button>
        <div>
            <span><fmt:message key="no_account"/></span>
            <a href="<c:url value="/controller?page=registration.jsp"/>">
                <fmt:message key="sign_up"/>
            </a>
        </div>
    </form>
</div>
</body>
</html>
