<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>are you sure</title>
</head>
<body>

Are you sure you want to delete all your bets?
<form action="/deleteAllBets/${client.id}" method="post">
    <br>yes: <input type="radio" name="answer" value="yes" required>
    <br>no: <input type="radio" name="answer" value="no">
    <br><input type="submit" value="ok">
</form>

</body>
</html>