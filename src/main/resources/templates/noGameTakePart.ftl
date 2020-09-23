
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Make bet</title>
</head>
<body>

<table>
    <tr>
        <td>
            <fieldset>
                <legend><strong>Make Bet</strong></legend>
                <br>'none' if draw
                <form action="/makeBet/${sport}/${client.id}" method="post">
                    <br>choose game:

                    <select name="name" required>
                        <#list games as game>
                            <option name="name">${game.name}</option>
                        </#list>
                    </select>

                    <br><br>Select Team:

                    <select name="winner" required>
                        <#list teams as team>
                            <option name="team">${team.name}</option>
                        </#list>
                    </select>

                    <br><br>Money:
                    <br><input type="text" name="money" required><br>
                    <input type="submit" value="bet">
                </form>
            </fieldset>
        </td>

        <td></td><td></td><td></td><td></td><td></td>

        <td>
            <table border="1"; cellpadding="5">
                <tr>
                    <td ALIGN="CENTER">name</td>
                    <td ALIGN="CENTER">team1 name</td>
                    <td ALIGN="CENTER">team2 name</td>
                    <td ALIGN="CENTER">status</td>
                </tr>
                <#list games as game>
                    <tr>
                        <td ALIGN="CENTER" bgcolor="#fa8072">${game.name}</td>
                        <td ALIGN="CENTER">${game.team1.name}</td>
                        <td ALIGN="CENTER">${game.team2.name}</td>
                        <td ALIGN="CENTER">${game.status}</td>
                    </tr>
                </#list>
            </table>
        </td>
    </tr>

    <tr>
        <td>
            There's no team '${name}' taking part in this game
        </td>
    </tr>

    <tr>
        <td>
            <fieldset>
                <legend><strong>Back to main page</strong></legend>
                <form action="/backToMainGage/${client.id}" method="post">
                    <input type="submit" value="back">
                </form>
            </fieldset>
        </td>
    </tr>
</table>

</body>
</html>








