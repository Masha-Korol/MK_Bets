
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Client</title>

<style>
    phone{
        background: url("Фон.jpg");
    }
</style>

<style>
    mark{
        background: tomato;
        padding: 0 3px;
    }
</style>

<style>
    .class2 {
        width: 250px; height: 60px; /* Размеры */
        background: cornflowerblue; /* Цвет фона */
        display: inline-block;
    }
</style>

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

<style>
    .class4{
        width: 250px; height: 60px; /* Размеры */
        background: skyblue; /* Цвет фона */
        display: inline-block;
    }
</style>

<style>
    p {
        width: 100px;	height: 60px;
        background: skyblue;
        outline: 2px solid #000;
        border: 3px solid #fff;
        border-radius: 10px;
        text-align: center;
        vertical-align: middle;
        justify-content: center;
    }
</style>

<style>
    #main{
        display: none;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }
    #okno {
        text-align: center;
        position: absolute;
        margin: auto;
        left: 400px;
        top: 230px;
   }
    #main:target {display: block;}
</style>

<style>
    .class1{
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

</head>
<body>

<fieldset class="class1">
    <strong>Find a team:</strong><br>
    <form action="/findTeam" method="post">
        <input type="text" required name="name">
        <input type="submit" value="search">
    </form>
</fieldset>

<table cellpadding="0">
    <tr>
        Hello ${client.name}!<br><br>
    </tr>

    <tr>
        <p>Your balance = ${client.money}</p><br>
        <form action="/addMoneyTrans/${client.id}" method="post">
            <input type="submit" value="+">
        </form>
        <form action="/getMoneyTrans/${client.id}" method="post">
            <input type="submit"  value="-">
        </form>
    </tr>

    <tr>
        <td>
            <br><strong>Available <mark>Bets</mark></strong>
            <table border="1"; cellpadding="5" width="250">
                <tr>
                    <td ALIGN="center">game name</td>
                    <td ALIGN="CENTER">name of team which you made bet for</td>
                    <td ALIGN="CENTER">money</td>
                </tr>
                <#list bets as bet>
                    <tr>
                        <td>${bet.game.name}</td>
                        <td>${bet.winningTeam.name}</td>
                        <td>${bet.money}</td>
                    </tr>
                </#list>
            </table>
        </td>

        <td>
            <a href="#main">Not Available Bets</a>

            <a href="#" id="main">
                <div id="okno">
                    <br><table border="1"; cellpadding="5">
                        <tr>
                            <td ALIGN="center">game name</td>
                            <td ALIGN="CENTER">name of winning team</td>
                            <td ALIGN="CENTER">money</td>
                            <td align="center">actual winner</td>
                        </tr>
                        <#list betsFinished as bet>
                            <tr>
                                <td>${bet.game.name}</td>
                                <td>${bet.winningTeam.name}</td>
                                <td>${bet.money}</td>
                                <td>${bet.game.winner.name}</td>
                            </tr>
                        </#list>
                    </table>
                </div>
            </a>
        </td>

        <td width="250"></td>
        <td width="250"></td>

        <td>
            <fieldset class="class4">
                <legend><strong>Make a Bet</strong></legend>
                <form action="/goToMakingBet/${client.id}" method="post">
                    <select name="sport">
                        <option name="sport">baseball</option>
                        <option name="sport">basketball</option>
                        <option name="sport">football</option>
                    </select>
                    <br><input type="submit" value="make">
                </form>
            </fieldset>
        </td>
    </tr>

    <tr>
        <td>
            <fieldset class="class4">
                <form action="/transmitionToEditClient/${client.id}" method="post">
                    <legend><strong>Edit Profile</strong></legend>
                    <br><input type="submit" value="edit">
                </form>
            </fieldset>
        </td>

        <td width="250"></td>
        <td width="250"></td>
        <td width="250"></td>


        <td>
            <br><strong>All <mark>Games</mark> Available Today!</strong>
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
        </td>
    </tr>

    <tr>
        <td>
            <fieldset class="class2">
                <legend><strong>Exit profile</strong></legend>
                <form action="/logout">
                    <br><input type="submit" value="exit">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset class="class2">
                <legend><strong>Delete Bet</strong></legend>
                <form action="/areYouSureDeleteBet/${client.id}" method="post">
                    Game name:<br>

                    <select name="name" required>
                        <#list games as game>
                            <option name="name">${game.name}</option>
                        </#list>
                    </select>

                    <input type="submit" value="delete">
                </form>
            </fieldset>
        </td>
    </tr>

    <tr>
        <td>
            <fieldset class="class2">
                <legend><strong>Delete All Bets</strong></legend>
                <form action="/areYouSureDeleteBets/${client.id}" method="post">
                    <input type="submit" value="delete">
                </form>
            </fieldset>
        </td>

        <td>
            <fieldset class="class2">
                <legend><strong>Statistics</strong></legend>
                <form action="/getStatistics/${client.id}" method="post">
                <input type="submit" value="see">
                </form>
            </fieldset>
        </td>
    </tr>

    <tr>
        <td width="250"></td>
        <td width="250"></td>
        <td width="250"></td>

        <td>
            <fieldset class="class3">
                <legend>Delete Profile</legend>
                <form action="/areYouSure/${client.id}" method="post">
                    <input type="submit" value="delete">
                </form>
            </fieldset>
        </td>
    </tr>
</table>

</body>
</html>








