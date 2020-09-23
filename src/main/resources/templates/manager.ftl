
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bets</title>

    <style>
        .class3{
            width: 90px; height: 30px; /* Размеры */
            background: tomato; /* Цвет фона */
            font-size: 11pt;
            display: inline-block;
            position: absolute;
            right: 50px;
        }
    </style>

</head>

<body>
Hello ${manager.name}!<br><br>

<table>
    <tr>
        <td>
            <fieldset>
                <legend><strong>All Clients</strong></legend>
                <form action="/getClients">
                    <input type="submit" value="see">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Delete Client</strong></legend>
                <form action="/areYouSureDeleteClient/${manager.id}" method="post">
                    Client name:

                    <select name="name" required>
                        <#list clients as client>
                            <option name="name">${client.name}</option>
                        </#list>
                    </select>

                    <input type="submit" value="delete">
                </form>
            </fieldset>
        </td>

        <td bgcolor="#fa8072">
            <fieldset>
                <legend><strong>Make a Game</strong></legend>
                Choose sport:<br>
                <form action="/makeGameTrans/${manager.id}" method="post">
                    <select name="sport">
                        <option name="sport">baseball</option>
                        <option name="sport">basketball</option>
                        <option name="sport">football</option>
                    </select>
                    <input type="submit" value="make">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Delete Team</strong></legend>
                <form action="/areYouSureDeleteTeam/${manager.id}" method="post">
                    Team id:
                    <select name="team_name" required>
                       <#list teams as team>
                           <option name="team_name">${team.name}</option>
                       </#list>
                    </select>

                    <br><input type="submit" value="delete">
                </form>
            </fieldset>
        </td>

        <td>
            All Teams<br>
            <table border="1"; cellpadding="5">
                <tr>
                    <td>name</td>
                    <td>sport</td>
                </tr>
                <#list teams as team>
                    <tr>
                        <td bgcolor="#87ceeb">${team.name}</td>
                        <td>${team.sport}</td>
                    </tr>
                </#list>
            </table>
        </td>
    </tr>

    <tr>
        <td>
            <fieldset>
                <legend><strong>All Bets</strong></legend>
                <form action="/getBets">
                    <input type="submit" value="all bets">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Find Client By Name</strong></legend>
                <form action="/findClientByName/${manager.id}" method="post">
                    Id:
                    <select name="name" required>
                        <#list clients as client>
                            <option name="name">${client.name}</option>
                        </#list>
                    </select>

                    <br><input type="submit" value="find">
                </form>
            </fieldset>
        </td>

        <td>
            <br><strong>All Games Available</strong>
            <table border="1"; cellpadding="5">
                <tr>
                    <td align="center">game name</td>
                    <td ALIGN="CENTER">team1 name</td>
                    <td ALIGN="CENTER">team2 name</td>
                    <td ALIGN="CENTER">status</td>
                </tr>
                <#list avGames as game>
                    <tr>
                        <td align="center">${game.name}</td>
                        <td ALIGN="CENTER">${game.team1.name}</td>
                        <td ALIGN="CENTER">${game.team2.name}</td>
                        <td ALIGN="CENTER" bgcolor="#fa8072">${game.status}</td>
                    </tr>
                </#list>
            </table>
        </td>

        <td>
            <fieldset>
                <legend><strong>All Bets Available</strong></legend>
                <form action="/getAvailableBets">
                    <input type="submit" value="see">
                </form>
            </fieldset>
        </td>
    </tr>

    <tr>
        <td>
            <fieldset>
                <legend><strong>All Games</strong></legend>
                <form action="/getGames" >
                    <input type="submit" value="see">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Add new Team</strong></legend>
                <form action="/transmitionToAddNewTeam/${manager.id}" method="post">
                    <input type="submit" value="add">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Delete Game</strong></legend>
                <form action="/areYouSureDeleteGame/${manager.id}" method="post">
                    name:
                    <select name="name" required>
                        <#list games as game>
                            <option name="name">${game.name}</option>
                        </#list>
                    </select>

                    <br><input type="submit" value="delete">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Register New Manager</strong></legend>
                <form action="/registerNewManager/${manager.id}">
                    <input type="submit" value="register">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Delete All Games</strong></legend>
                <form action="/areYouSureDeleteAllGames/${manager.id}" method="post">
                    <input type="submit" value="delete">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Find Game By name</strong></legend>
                <form action="/findGame" method="post">
                    name:
                    <select name="name" required>
                        <#list games as game>
                            <option name="name">${game.name}</option>
                        </#list>
                    </select>

                    <br><input type="submit" value="find">
                </form>
            </fieldset>
        </td>
    </tr>

    <tr>
        <td>
            <fieldset>
                <legend><strong>All Managers</strong></legend>
                <form action="/getManagers" >
                    <input type="submit" value="see">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Delete Manager</strong></legend>
                <form action="/areYouSureDeleteManager/${manager.id}" method="post">
                    Id:
                    <select name="managerToDelete_name" required>
                        <#list managers as manager>
                            <option name="managerToDelete_name">${manager.name}</option>
                        </#list>
                    </select>

                    <br><input type="submit" value="delete">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <form action="/transmitionToEditTeam/${manager.id}" method="post">
                    <legend><strong>Edit Team</strong></legend>
                    Team id:
                    <select name="team_name" required>
                        <#list teams as team>
                            <option name="team_name">${team.name}</option>
                        </#list>
                    </select>

                    <br><input type="submit" value="edit">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <form action="/transmitionToEditManager/${manager.id}" method="post">
                    <legend><strong>Edit Profile</strong></legend>
                    <br><input type="submit" value="edit">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset>
                <legend><strong>Exit profile</strong></legend>
                <form action="/exitToMainPage">
                    <br><input type="submit" value="exit">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset class="class3">
                <legend><strong>Delete Profile</strong></legend>
                <form action="/areYouSureDeleteManagerYourself/${manager.id}" method="post">
                    <input type="submit" value="delete">
                </form>
            </fieldset>
        </td>
    </tr>
</table>


<style>
    .class2 {
        position: fixed;
        left: 25px;
        bottom: 40px;
        width: 100px;
        height: 25px;
        outline: 6px crimson;
    }
</style>

</body>
</html>








