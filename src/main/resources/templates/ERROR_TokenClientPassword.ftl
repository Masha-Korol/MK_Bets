
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
            <fieldset>
                <form method="post" action="/addNewClient">
                    name:<input type="text" name="client_name" required><br>
                    password:<input type="text" name="password" required><br>
                    <input type="submit" value="register">
                </form>
            </fieldset>

            <br>Password '${password}' has already been token! Try something else
        </td>
    </tr>
</table>

</body>
</html>








