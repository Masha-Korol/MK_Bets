
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Game</title>
</head>
<body>

<table>
    <tr>
        <td>
            <fieldset>
                <legend>${sport}</legend>
                <form action="/makeGame/${manager.id}/${sport}" method="post">
                    Team 1 name:<br>
                    <select name="team1_name" required>
                        <#list teams as team>
                            <option name="team1_name">${team.name}</option>
                        </#list>
                    </select>

                    <br>Team 2 name:<br>
                    <select name="team2_name" required>
                        <#list teams as team>
                            <option name="team2_name">${team.name}</option>
                        </#list>
                    </select>

                    <br>Time for bets (in minutes):
                    <br><input type="number" name="delay" required>

                    <br>Game name: {team1_name}_{team2_name}_{date}
                    <br><input type="text" name="name" required>
                    <br><input type="submit" value="make">
                </form>
            </fieldset>
        </td>
    </tr>

    <tr>
        <td>
            <br>
            <fieldset>
                <legend><strong>Back to main page</strong></legend>
                <form action="/backToMainPage/${manager.id}" method="post">
                    <input type="submit" value="back">
                </form>
            </fieldset>
        </td>
    </tr>
</table>

</body>
</html>