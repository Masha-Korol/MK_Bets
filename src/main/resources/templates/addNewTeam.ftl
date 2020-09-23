
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Managers</title>
</head>
<body>

<table>
    <tr>
        <td>
            <form action="/addNewTeam/${manager.id}" method="post">
                <br>Team name:
                <br><input type="text" name="name" required>
                <br><select name="sport">
                    <option name="sport">baseball</option>
                    <option name="sport">basketball</option>
                    <option name="sport">football</option>
                </select>
                <br><input type="submit" value="add">
            </form>
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