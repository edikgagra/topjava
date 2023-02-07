<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://topjava.javawebinar.ru/functions" prefix="function" %>

<html>
<head>
  <title>User meals</title>
  <style type="text/css">
    .exceeded {
      color: red;
    }
    .normal {
      color: green;
    }
  </style>
</head>
<body>
<h3>
  <a href="index.html">Home</a>
</h3>

<h2>Meal List</h2>
<a href="meals?action=create">Add Meal</a>

<table border="1" cellpadding="8" cellspacing="0">
  <thead>
  <tr>
    <th>Дата/Время</th>
    <th>Описание</th>
    <th>Калории</th>
    <th></th>
    <th></th>
  </tr>
  </thead>

  <tbody>
  <jsp:useBean id="meals" scope="request"
               type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
  <c:forEach var="meal" items="${meals}">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
    <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
      <td>
        <%=TimeUtil.toString(meal.getDateTime())%>
      </td>
      <td>${meal.description}</td>
      <td>${meal.calories}</td>
      <td>
        <a href="meals?action=update&id=${meal.id}">Update</a>
      </td>
      <td>
        <a href="meals?action=delete&id=${meal.id}">Delete</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>