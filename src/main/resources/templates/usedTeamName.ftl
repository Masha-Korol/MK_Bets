
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>edit</title>
</head>
<body>

Team name: '${team.name}'

<table>
    <tr>
        <td>
            <br>
            <form action="/editTeam/${team.team_id}" method="post">
                New Name:<br>
                <input type="text" name="name" required>
                <input type="submit" value="edit">
            </form>
        </td>
    </tr>

    <tr>
        <td>
            <br>
            Team name '${name}' has already been token. Try something else
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








