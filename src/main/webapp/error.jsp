<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set value='${requestScope["error"]}' var="error"/>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>${error}</h1>
</body>
</html>