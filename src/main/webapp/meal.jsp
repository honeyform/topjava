<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
  <title>Add new user</title>
</head>
<body>
<%--<jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.Meal"/>--%>
<form method="POST" action='meals' name="frmAddUser">
  ID : <input type="text" readonly="readonly" name="id"
              value="<%= request.getParameter("id")%>" /> <br />
  Дата и время : <input
        type="text" name="dateTime"
<%--value="<fmt:formatDate pattern="MM/dd/yyyy" value="<%= request.getParameter("dateTime")%>" /> <br />--%>
        value="<%= request.getParameter("meal")%>" /> <br />
  Описание : <input
        type="text" name="description"
        value="<%= request.getParameter("description")%>" /> <br />
  Калорий : <input
        type="text" name="calories"
        value="<%= request.getParameter("calories")%>" /> <br />
  <input type="submit" value="Добавить" />
</form>
</body>
</html>