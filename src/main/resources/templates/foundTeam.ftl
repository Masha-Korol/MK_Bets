
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Team</title>
</head>
<body>

<table border="1"; cellpadding="5">
    <tr>
        <td align="center">sport</td>
        <td ALIGN="CENTER">name</td>
        <td align="center">games with this team and a winner</td>
    </tr>

    <tr>
        <td align="center">${team.sport}</td>
        <td align="center" bgcolor="#6495ed">${team.name}</td>
        <td align="center">
            <#list games as game>
                <pre><font face="Calibri">'${game.name}'         ${game.status}          ${game.winner.name}<br></font></pre>
            </#list>
        </td>
    </tr>
</table>

</body>
</html>
