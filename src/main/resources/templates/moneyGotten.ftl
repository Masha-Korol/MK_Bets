
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>add money</title>


    <style>
        .class4{
            width: 250px; height: 60px; /* Размеры */
            background: skyblue; /* Цвет фона */
            display: inline-block;
        }
    </style>

</head>
<body>

<table>
    <tr>
        <td>
            <fieldset class="class4">
                <legend><strong>Get Money</strong></legend>
                <form action="/getMoneyFromClient/${client.id}" method="post">
                    money: <input type="text" name="money" required/><br/>
                    <input type="submit" value="get">
                </form>
            </fieldset>
        </td>

        <td>
            <br>Your account now = <strong>${client.money}</strong>
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