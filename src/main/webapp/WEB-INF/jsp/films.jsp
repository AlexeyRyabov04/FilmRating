<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["lang"]}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<c:if test="${empty user}">
    <c:redirect url="/controller?page=login.jsp"/>
</c:if>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/modal.css">
    <title><fmt:message key="films_header"/></title>
</head>
<body>
<div>
    <%@ include file="/header.jsp" %>
    <main>
        <div>
            <c:if test="${user.getRole() == 'admin'}">
                <button onclick="openAddFilmModal()">
                    <span>+</span>
                    <fmt:message key="add_film"/>
                </button>
            </c:if>
        </div>
        <div>
            <c:set value='${requestScope["filmsList"]}' var="filmsList"/>
            <c:forEach var="film" items="${filmsList}">
                <div>
                    <a href="<c:url value="/controller?command=get_film&filmName=${film.getName()}"/>">
                        <div>${film.getRating()}</div>
                        <div>${film.getName()}</div>
                    </a>
                    <c:if test="${user.getRole() == 'admin'}">
                        <button
                                onclick="openEditFilmModal(`${film.getId()}`, `${film.getName()}`, `${film.getDescription()}`)">
                            <fmt:message key="edit_film"/>
                        </button>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <div class="modal-wrapper" id="addFilmModal">
            <div>
                <h2><fmt:message key="add_film_header"/></h2>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="add_film">

                    <label for="addFilmTitle"><fmt:message key="film_title"/></label>
                    <input type="text" name="filmTitle" id="addFilmTitle" required>

                    <label for="addFilmDescription"><fmt:message key="film_description"/></label>
                    <textarea name="filmDescription" id="addFilmDescription" required></textarea>
                    <button type="submit">
                        <fmt:message key="film_add_btn"/>
                    </button>
                </form>
            </div>
        </div>
        <div class="modal-wrapper" id="editFilmModal">
            <div>
                <h2><fmt:message key="edit_film_header"/></h2>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="edit_film">

                    <input type="hidden" name="filmId" id="editFilmId" value="">
                    <label for="editFilmTitle"><fmt:message key="film_title"/></label>
                    <input type="text" name="filmTitle" id="editFilmTitle" required>

                    <label for="editFilmDescription"><fmt:message key="film_description"/></label>
                    <textarea name="filmDescription" id="editFilmDescription" required></textarea>
                    <button type="submit">
                        <fmt:message key="film_edit_btn"/>
                    </button>
                </form>
            </div>
        </div>
    </main>
</div>
<script>
    openAddFilmModal = () => {
        const modal = document.getElementById("addFilmModal");
        modal.style.display = "flex";
    }

    openEditFilmModal = (filmId, filmTitle, filmDescription) => {
        const modal = document.getElementById("editFilmModal");
        modal.style.display = "flex";
        document.getElementById("editFilmId").value = filmId;
        document.getElementById("editFilmTitle").value = filmTitle;
        document.getElementById("editFilmDescription").value = filmDescription;
    }

    window.onclick = (event) => {
        const addModal = document.getElementById("addFilmModal");
        const editModal = document.getElementById("editFilmModal");
        if (event.target === addModal) {
            addModal.style.display = "none";
        }
        if (event.target === editModal) {
            editModal.style.display = "none";
        }
    }
</script>
</body>
</html>
