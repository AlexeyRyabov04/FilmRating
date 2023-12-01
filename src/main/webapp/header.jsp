<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["lang"]}'/>
<fmt:setBundle basename="messages"/>
<div>
    <ul class="header-list" style="list-style: none">
        <ul style="display: flex; justify-content: space-between; list-style: none">
            <li>
                <form action="controller" method="POST">
                    <input type="hidden" name="command" value="change_language">
                    <select name="language" onchange="this.form.submit()">
                        <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}>English</option>
                        <option value="ru" ${sessionScope.lang == 'ru' ? 'selected' : ''}>Русский</option>
                    </select>
                </form>
            </li>
            <li><c:out value="${user.getName()}"/></li>
            <li>
                <form action="controller" method="POST">
                    <input type="hidden" name="command" value="do_logout">
                    <button type="submit">
                        <fmt:message key="logout"/>
                    </button>
                </form>
            </li>
        </ul>
    </ul>
    <c:choose>
        <c:when test="${user.getRole() == 'admin'}">
            <c:if test="${user.getRole() == 'admin'}">
                <li style="list-style: none" class="admin">
                    <a href="<c:url value="/controller?command=get_users"/>">
                        <fmt:message key="admin_panel"/>
                    </a>
                </li>
            </c:if>
        </c:when>
        <c:otherwise>
            <li>
                <h2>
                    <fmt:message key="film_ratings"/>
                </h2>
            </li>
        </c:otherwise>
    </c:choose>
</div>
