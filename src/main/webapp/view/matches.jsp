<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>Поиск матча по имени игрока</p>
<form action="matches" method="get">
    <input type="text" name="filter_by_player_name" size="40" />
    <input type="submit" value="Найти"/>
</form>
<br>
<br>
<table style="width: 100%; border-collapse: collapse; border-style: none; height: 36px;" border="1">
    <tbody>
    <tr style="height: 18px;">
        <td style="width: 25%; text-align: center; height: 18px;">ID game</td>
        <td style="width: 25%; text-align: center; height: 18px;">Name player #1</td>
        <td style="width: 25%; text-align: center; height: 18px;">Name player #2</td>
        <td style="width: 25%; text-align: center; height: 18px;">Winner player</td>
    </tr>
    <c:forEach var="entity" items="${entitys}">
        <tr style="height: 18px;">
            <td style="width: 25%; height: 18px; text-align: center;"><c:out value="${entity.id}"/></td>
            <td style="width: 25%; height: 18px; text-align: center;"><c:out value="${entity.player1.name}"/></td>
            <td style="width: 25%; height: 18px; text-align: center;"><c:out value="${entity.player2.name}"/></td>
            <td style="width: 25%; height: 18px; text-align: center;"><c:out value="${entity.winner.name}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
