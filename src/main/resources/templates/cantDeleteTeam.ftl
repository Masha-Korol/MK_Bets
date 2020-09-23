<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Client</title>
</head>
<body>

<table>
    <tr>
        <td>
            You can't delete this team, table 'games' contains it's data
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
