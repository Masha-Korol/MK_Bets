
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bets</title>
</head>
<body>

<table border="1"; cellpadding="5">
    <tr>
        <td ALIGN="CENTER">name</td>
        <td align="center">bets</td>
    </tr>

    <tr>
        <td align="center" bgcolor="#6495ed">${client.name}</td>
        <td align="center">
            <#list client.betsList as bet>
                    <pre><font face="Calibri">'${bet.game.name}'         ${bet.game.status}<br></font></pre>
            </#list>
        </td>
    </tr>
</table>

</body>
</html>
