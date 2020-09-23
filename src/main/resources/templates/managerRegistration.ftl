
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
            <fieldset>
                <form method="post" action="/addNewManager">
                    name:<input type="text" name="manager_name" required><br>
                    password:<input type="text" name="password" required><br>
                    <input type="submit" value="register"><br>
                </form>
            </fieldset>
        </td>
    </tr>

    <tr>
        <td>
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








