
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bets</title>
</head>
<body>

<h1>Your statistics</h1>

<table>
    <tr>
        <td>
            VICTORIES
            <br><strong>${client.wins}</strong>
        </td>

        <td>
            <pre>            </pre>DEFEATS
            <br><strong>${client.losses}</strong>
        </td>
    </tr>

    <tr>
        <td>
            <br>
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