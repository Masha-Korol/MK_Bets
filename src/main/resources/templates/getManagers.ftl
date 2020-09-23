
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Managers</title>
</head>

<body>
All Managers<br>
<table border="1"; cellpadding="5">
    <tr>
        <td ALIGN="CENTER">name</td>
    </tr>
    <#list managers as manager>
        <tr>
            <td>${manager.name}</td>
        </tr>
    </#list>
</table>
</body>
</html>








