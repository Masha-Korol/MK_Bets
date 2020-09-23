
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>edit</title>

    <style>
        .class4{
            width: 250px; height: 60px; /* Размеры */
            background: skyblue; /* Цвет фона */
            display: inline-block;
        }
    </style>

</head>
<body>

Your name: '${client.name}'

<table>
    <tr>
        <td>
            <fieldset class="class4">
                <form action="/editClient/${client.id}" method="post">
                    New Name:<br>
                    <input type="text" name="name" required>
                    <input type="submit" value="edit">
                </form>
            </fieldset>
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








