<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Новый матч</title>
</head>
<body>
Страница матча
<br>
UUID: <%= request.getAttribute("uuid") %>
<br>
<table style="width: 50%; border-collapse: collapse; border-style: none; height: 36px; margin-left: 300">
    <tbody>
    <tr style="height: 18px;">
        <td style="width: 20%; text-align: center; height: 18px;"></td>
        <td style="width: 50%; text-align: center; height: 18px;">Игрок 1</td>
        <td style="width: 50%; text-align: center; height: 18px;">Игрок 2</td>
    </tr>
    <tr style="height: 18px;">
        <td style="width: 20%; text-align: center; height: 18px;"></td>
        <td style="width: 50%; height: 18px; text-align: center;">
            <form action="match-score?uuid=<%= request.getAttribute("uuid") %>" method="post">
                <%= request.getAttribute("namePlayer1")%>
                <br>
                <input type="hidden" name="idPlayer" value="<%= request.getAttribute("idPlayer1") %>"/>
                <input type="submit" value="игрок 1 выиграл текущее очко"/>
            </form>
        </td>
        <td style="width: 50%; height: 18px; text-align: center;">
            <form action="match-score?uuid=<%= request.getAttribute("uuid") %>" method="post">
                <%= request.getAttribute("namePlayer2")%>
                <br>
                <input type="hidden" name="idPlayer" value="<%= request.getAttribute("idPlayer2") %>"/>
                <input type="submit" value="игрок 2 выиграл текущее очко"/>
            </form>
        </td>

    </tr>
    <tr style="height: 18px;">
        <td style="width: 20%; text-align: center; height: 18px;">Текущий счет</td>
        <td style="width: 50%; text-align: center; height: 18px;"><%= request.getAttribute("pointPlayer1") %></td>
        <td style="width: 50%; text-align: center; height: 18px;"><%= request.getAttribute("pointPlayer2") %></td>
    </tr>
    <tr style="height: 18px;">
        <td style="width: 20%; text-align: center; height: 18px;">Гейм</td>
        <td style="width: 50%; text-align: center; height: 18px;"><%= request.getAttribute("gamePlayer1") %></td>
        <td style="width: 50%; text-align: center; height: 18px;"><%= request.getAttribute("gamePlayer2") %></td>
    </tr>
    <tr style="height: 18px;">
        <td style="width: 20%; text-align: center; height: 18px;">Сет</td>
        <td style="width: 50%; text-align: center; height: 18px;"><%= request.getAttribute("setPlayer1") %></td>
        <td style="width: 50%; text-align: center; height: 18px;"><%= request.getAttribute("setPlayer2") %></td>
    </tr>

    </tbody>
</table>
</body>
</html>
