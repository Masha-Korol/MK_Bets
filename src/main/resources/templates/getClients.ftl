
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Clients</title>
</head>

<body>
All Clients<br>
<table border="1"; cellpadding="5">
    <tr>
        <td ALIGN="CENTER">name</td>
        <td align="center">all bets (game's name + status)</td>
    </tr>
    <#list clients as client>
        <tr>
            <td bgcolor="#fa8072">${client.name}</td>
            <td>
                <#list client.betsList as bet>
                    <pre><font face="Calibri">${bet.game.name}         ${bet.game.status}<br></font></pre>
                </#list>
            </td>
        </tr>
    </#list>
</table>
</body>
</html>








