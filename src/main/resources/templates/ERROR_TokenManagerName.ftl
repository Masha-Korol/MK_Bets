
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
                    name:<input type="text" name="manager_name"><br>
                    password:<input type="text" name="password"><br>
                    <input type="submit" value="register"><br>
                </form>
            </fieldset>

            <br>Name '${name}' has already been token! Try something else
        </td>
    </tr>
</table>

</body>
</html>








