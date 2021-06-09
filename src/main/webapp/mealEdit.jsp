<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Edit</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${param.action == "add" ? "Add meal" : "Edit meal"}</h2>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>DateTime:</dt>
        <dd><label><input required type="datetime-local" name="dateTime" size=30 value="${meal.dateTime}"></label></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><label><input required type="text" name="description" size=30 value="${meal.description}"></label></dd>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dd><label><input required type="number" name="calories" size=30 value="${meal.calories}"></label></dd>
    </dl>
    <button type="submit">Save</button>
    <button type="reset" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>