<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Список еды</h2>
    <hr>
    <form id="filter" role="form" method="post" action="meals">

        <div >
            <label for="beginDate">From Date:</label>
            <input type="date" name="beginDate"  id="beginDate">
        </div>
        <div>
            <label for="endDate">To Date:</label>
            <input type="date" name="endDate" id="endDate">
        </div>
        <div >
            <label for="beginTime">From Time:</label>
            <input type="time" name="beginTime" id="beginTime">
        </div>
        <div>
            <label for="endTime">To Time:</label>
            <input type="time" name="endTime" id="endTime">
        </div>

        <div >
            <div >
                <button type="submit">Filter</button>
            </div>
        </div>

    </form>
</section>
<section>
    <a href="meals?action=create">Add Meal</a>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
