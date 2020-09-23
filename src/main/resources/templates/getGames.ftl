
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Games</title>
</head>

<body>
All Games<br>
<table border="1"; cellpadding="5">
    <tr>
        <td ALIGN="CENTER">name</td>
        <td ALIGN="CENTER">team1 name</td>
        <td ALIGN="CENTER">team2 name</td>
        <td align="center">winner team name</td>
        <td ALIGN="CENTER">status</td>
    </tr>
    <#list games as game>
        <tr>
            <td ALIGN="CENTER">${game.name}</td>
            <td ALIGN="CENTER">${game.team1.name}</td>
            <td ALIGN="CENTER">${game.team2.name}</td>
            <td align="center">${game.winner.name}</td>
            <td ALIGN="CENTER">${game.status}</td>
        </tr>
    </#list>
</table>
</body>
</html>








