
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ERROR</title>
</head>
<body>

<table border="1"; cellpadding="5">
    <tr>
        <td ALIGN="CENTER">name</td>
        <td ALIGN="CENTER">team1 name</td>
        <td ALIGN="CENTER">team2 name</td>
        <td align="center">winner team name</td>
        <td ALIGN="CENTER">status</td>
        <td align="center">all bets (money)</td>
    </tr>

    <tr>
        <td ALIGN="CENTER">${game.name}</td>
        <td ALIGN="CENTER">${game.team1.name}</td>
        <td ALIGN="CENTER">${game.team2.name}</td>
        <td ALIGN="center">${game.winnerName}</td>
        <td ALIGN="CENTER">${game.status}</td>
        <td>
            <#list game.betList as bet>
                ${bet.money}<br>
            </#list>
        </td>
    </tr>
</table>

</body>
</html>








