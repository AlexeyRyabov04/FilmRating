<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["lang"]}'/>
<fmt:setBundle basename="messages"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<c:if test="${empty user}">
    <c:redirect url="/controller?page=login.jsp"/>
</c:if>
<c:set value='${requestScope["film"]}' var="film"/>
<c:set value='${requestScope["isButtonDisabled"]}' var="isDisabled"/>
<html>
<head>
    <title>${film.getName()}</title>
</head>
<body>
<div>
    <%@ include file="/header.jsp" %>
    <main>
        <a href="<c:url value="/controller?command=get_films"/>"><</a>
        <div>
            <div>
                <div>
                    <c:choose>
                        <c:when test="${isDisabled}">
                            <button disabled="disabled">
                                <span>+</span>
                                <<fmt:message key="film_add_review"/>
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button onclick="openReviewModal()">
                                <span>+</span>
                                <fmt:message key="film_add_review"/>
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div>
                <div>
                    ${film.getName()}
                    <span>${film.getRating()}</span>
                </div>
                <div>${film.getDescription()}</div>
                <div>
                    <h2><fmt:message key="reviews_header"/></h2>
                    <c:set value='${requestScope["reviews"]}' var="reviews"/>
                    <c:forEach var="review" items="${reviews}">
                        <div>
                            <div>
                                <div style="display: flex">
                                <p>${review.getUserName()}&nbsp; </p>
                                <p>${review.getUserRating()}</p>
                                </div>
                                <p>${review.getFilmRating()}</p>
                                <p>${review.getText()}</p>
                                <c:if test="${user.getName() == review.getUserName()}">
                                    <form action="controller" method="post" style="margin-left: auto">
                                        <input type="hidden" name="command" value="delete_review">
                                        <input type="hidden" name="filmName" value="${film.getName()}">
                                        <input type="hidden" name="reviewId" value="${review.getId()}">
                                        <button><fmt:message key="review_delete"/></button>
                                    </form>
                                </c:if>

                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div id="reviewModal">
                    <div>
                        <h2><fmt:message key="film_add_review"/></h2>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="add_review">
                            <input type="hidden" name="filmName" value="${film.getName()}">
                            <label for="userRating"><fmt:message key="review_rating"/></label>
                            <input
                                    id="userRating"
                                    type="number"
                                    name="userRating"
                                    value="10"
                                    min="1"
                                    max="10"
                                    step="1"
                                    required
                            >
                            <label for="reviewText"><fmt:message key="review_review"/></label>
                            <textarea name="reviewText" id="reviewText" required></textarea>
                            <button type="submit"><fmt:message key="review_submit"/></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
<script>
    openReviewModal = () => {
        const modal = document.getElementById("reviewModal");
        modal.style.display = "flex";
    }

    window.onclick = (event) => {
        const modal = document.getElementById("reviewModal");
        if (event.target === modal) {
            const userRatingInput = document.getElementById("userRating");
            const reviewTextInput = document.getElementById("reviewText");
            userRatingInput.value = 10;
            reviewTextInput.value = '';
            modal.style.display = "none";
        }
    }
</script>
</body>
</html>
