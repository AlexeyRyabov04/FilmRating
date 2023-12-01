<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["lang"]}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${requestScope["error"]}' var="error"/>
<c:set value='${requestScope["message"]}' var="message"/>
<html>
<head>
    <title><fmt:message key="register_header"/></title>
</head>
<body>
<div>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="do_registration">
        <h1><fmt:message key="register_header"/></h1>
        <div>
            <label for="name"><fmt:message key="name"/></label>
            <input type="text" id="name" name="name" required/>
        </div>
        <div>
            <label for="password"><fmt:message key="password"/></label>
            <input type="password" id="password" name="password" required/>
        </div>
        <div>
            <label for="re-password"><fmt:message key="repeat_password"/></label>
            <input type="password" id="re-password" name="re-password" required/>
        </div>
        <div>${error}</div>
        <div>${message}</div>
        <button type="submit">
            <fmt:message key="create_account"/>
        </button>
        <div>
            <span><fmt:message key="already_have_account"/></span>
            <a href="<c:url value="/controller?page=login.jsp"/>">
                <fmt:message key="sing_in"/>
            </a>
        </div>
    </form>
</div>
</body>
</html>
