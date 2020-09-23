
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

Your name: '${manager.name}'

<table>
    <tr>
        <td>
            <fieldset class="class4">
                <form action="/editManager/${manager.id}" method="post">
                    New Name:<br>
                    <input type="text" name="name" required>
                    <input type="submit" value="edit">
                </form>
            </fieldset>
        </td>

        <td>
            Name '${newName}' has already been token. Try something else
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








