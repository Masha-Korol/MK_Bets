
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main</title>

    <style>
        mark{
            background: tomato;
            padding: 0 3px;
        }
    </style>

</head>
<body>

<style>
    .class1{
        position: fixed;
        bottom: 90px;
        left: 980px;
        text-align: center;
        outline: 30px blue;
        background: powderblue;
        width: 200px;
        height: 300px;
    }
</style>

<style>
    .class2{
        position: fixed;
        bottom: 500px;
        left: 550px;
        text-align: center;
        outline: 30px blue;
        background: powderblue;
        width: 200px;
        height: 40px;
    }
</style>

<br><strong>GAMES <MARK>AVAILABLE</MARK> TODAY</strong>
<table border="1"; cellpadding="5">
    <tr>
        <td align="center">sport</td>
        <td ALIGN="CENTER"><strong>name</strong></td>
        <td ALIGN="CENTER">team1 name</td>
        <td ALIGN="CENTER">team2 name</td>
        <td ALIGN="CENTER">status</td>
    </tr>
    <#list avGames as game>
        <tr>
            <td align="center">${game.team1.sport}</td>
            <td ALIGN="CENTER" bgcolor="#6495ed"><strong>${game.name}</strong></td>
            <td ALIGN="CENTER">${game.team1.name}</td>
            <td ALIGN="CENTER">${game.team2.name}</td>
            <td ALIGN="CENTER" bgcolor="#fa8072">${game.status}</td>
        </tr>
    </#list>
</table>

<fieldset class="class2">
    <strong>Find a team:</strong><br>
    <form action="/findTeam" method="post">
        <input type="text" required name="name">
        <input type="submit" value="search">
    </form>
</fieldset>

<fieldset class="class1">
    <legend align="center"><strong>Enter your name</strong></legend>
    <form method="post" action="/login">
        <br><br><br>
        <label>Login:
            <input type="text" name="username" required><br></label>
        <label>Password:
            <input type="text" name="password" required><br></label>
        <input type="submit">
    </form>
    <br><br>
    Wrong password or login!

    <br><br><br><br><br>

    <form method="post" action="/registerNewClient">
        <input type="submit" value="register">
    </form>
</fieldset>

</body>
</html>








