
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bets</title>
</head>

<body>
All Bets<br>
<table border="1"; cellpadding="5">
    <tr>
        <td align="center">client name</td>
        <td ALIGN="center">game name</td>
        <td ALIGN="CENTER">winning team name</td>
        <td ALIGN="CENTER">money</td>
        <td align="center">status</td>
        <td align="center">result</td>
    </tr>
    <#list bets as bet>
        <tr>
            <td>${bet.user.name}</td>
            <td>${bet.game.name}</td>
            <td>${bet.winningTeam.name}</td>
            <td>${bet.money}</td>
            <td>${bet.game.status}</td>
            <td>${bet.game.winner.name}</td>
        </tr>
    </#list>
</table>
</body>
</html>








