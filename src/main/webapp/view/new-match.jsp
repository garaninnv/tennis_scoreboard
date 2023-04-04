<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Новый матч</title>
</head>
<body>
<form action="new-match" method="post">
<table style="width: 100%; border-collapse: collapse; border-style: none; height: 36px;">
    <tbody>
    <tr style="height: 18px;">
        <td style="width: 50%; text-align: center; height: 18px;">Игрок 1</td>
        <td style="width: 50%; text-align: center; height: 18px;">Игрок 2</td>
    </tr>
    <tr style="height: 18px;">
        <td style="width: 50%; height: 18px; text-align: center;"><input type="text" name="player1" size="40" /></td>
        <td style="width: 50%; height: 18px; text-align: center;"><input type="text" name="player2" size="40" /></td>
    </tr>
    </tbody>
</table>
<p></p>
<input type="submit" value="Начать"/>
</form>
</body>
</html>
