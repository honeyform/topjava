<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
  <title>Meals list</title>
  <style>
    <%@include file='css/meals.css' %>
  </style>

</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meals list</h2>

<a href="meal?action=insert">Insert new meal</a>

<table border="1" cellspacing="0" cellpadding="2">
  <caption>Таблица калорий</caption>
  <tr>
    <th> Дата и время </th>
    <th> Описание </th>
    <th> Калории </th>
    <th colspan=2>Action</th>
  </tr>
  <c:forEach items="${meals}" var="meal">
    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>

    <tr class="${meal.exceed ? "exceeded" : "normal"}">
      <c:set var="cleanedDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}"/>
        <%--<td>--%>

        <%--<fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate" />--%>
        <%--<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />--%>

      <td>${cleanedDateTime}</td>
      <td>${meal.description}</td>
      <td>${meal.calories}</td>
      <td><a href="meal?action=edit&id=${meal.id}">Update</a></td>
        <%--<td><a href="<c:url value='meal?action=edit&id=${meal.id}'/>">Edit</a></td>--%>
      <td><a href="meal?action=delete&id=${meal.id}">Delete</a></td>
    </tr>

  </c:forEach>
</table>

</body>
</html>
