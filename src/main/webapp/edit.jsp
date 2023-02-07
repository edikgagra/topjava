<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Edit meal</title>

  <style rel="stylesheet">
    dl {
      background: none repeat scroll 0 0 #FAFAFA;
      margin: 8px 0;
      padding: 0;
    }
    dt {
      display: inline-block;
      width: 170px;
    }
    dd {
      display: inline-block;
      margin-left: 8px;
      vertical-align: top;
    }
  </style>
</head>
<body>
<h3>
  <a href="meals">Meals</a>
</h3>
<h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
<hr>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post" action="meals">
  <input type="hidden" name="id" value="${meal.id}">
  <dl>
    <dt>Дата/Время:</dt>
    <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"/></dd>
  </dl>
  <dl>
    <dt>Описание:</dt>
    <dd><input type="text" name="description" value="${meal.description}"/></dd>
  </dl>
  <dl>
    <dt>Калории:</dt>
    <dd><input type="number" name="calories" value="${meal.calories}"></dd>
  </dl>

  <button type="submit">Save</button>
  <button type="button" onclick="window.history.back()">Cancel</button>
</form>

</body>
</html>