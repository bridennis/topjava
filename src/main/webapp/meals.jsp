<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .red_row { background: red; color: white; }
        .green_row { background: green; color: white; }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>Date/time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Limit</th>
    </tr>
    <jsp:useBean id="mealWithExceedList" scope="request" type="java.util.ArrayList"/>
    <c:forEach var="meal" items="${mealWithExceedList}">
    <tr class="<c:out value="${meal.isExceed() ? 'red_row': 'green_row'}"/>">
        <td><javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm" /></td>
        <td>${meal.getDescription()}</td>
        <td>${meal.getCalories()}</td>
        <td>${meal.isExceed()}</td>
    </tr>
    </c:forEach>

</table>
</body>
</html>