<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["lang"]}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<c:if test="${empty user}">
    <c:redirect url="/controller?page=login.jsp"/>
</c:if>
<c:set value='${sessionScope["role"]}' var="role"/>
<c:if test="${role == 'client'}">
    <c:redirect url="/controller?page=films.jsp"/>
</c:if>
<html>
<head>
    <title><fmt:message key="admin_panel"/></title>
</head>
<body>
<div>
    <%@ include file="/header.jsp" %>
    <main>
        <a href="<c:url value="/controller?command=get_films"/>"><</a>
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="user_name"/></th>
                <th><fmt:message key="user_rating"/></th>
                <th><fmt:message key="user_ban"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set value='${requestScope["usersList"]}' var="usersList"/>
            <c:forEach var="userInfo" items="${usersList}">
                <tr>
                    <td>${userInfo.getName()}</td>
                    <td>
                        <div>
                            <form action="controller" method="POST">
                                <input type="hidden" name="command" value="edit_user">
                                <input type="hidden" name="id" value="${userInfo.getId()}">
                                <input type="hidden" name="rating" value="${userInfo.getRating() - 1}">
                                <input type="hidden" name="ban" value="${userInfo.getIsBanned()}">
                                <button type="submit">-</button>
                            </form>
                                ${userInfo.getRating()}
                            <form action="controller" method="POST">
                                <input type="hidden" name="command" value="edit_user">
                                <input type="hidden" name="id" value="${userInfo.getId()}">
                                <input type="hidden" name="rating" value="${userInfo.getRating() + 1}">
                                <input type="hidden" name="ban" value="${userInfo.getIsBanned()}">
                                <button type="submit">+</button>
                            </form>
                        </div>
                    </td>
                    <td>
                        <form action="controller" method="POST">
                            <input type="hidden" name="command" value="edit_user">
                            <input type="hidden" name="id" value="${userInfo.getId()}">
                            <input type="hidden" name="rating" value="${userInfo.getRating()}">
                            <input type="hidden" name="ban" value="${!userInfo.getIsBanned()}">
                            <button type="submit">${userInfo.getIsBanned() ? "Unban" : "Ban"}</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </main>
</div>
</body>
</html>
