<!DOCTYPE html>
<html>
<head>
    <title>Sending Email with Freemarker HTML Template Example</title>

    <meta charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

</head>
<body>
    <p>Уважаемый, Вас пригласил ${sender_surname}</p>
    <p>Присоединяйтесь к пространству ${surface_name}</p>

<p>
    <a href="http://localhost:8080/registration?email=${email}&surfaceId=${surfaceId}">Вступай</a>
</p>
</body>
</html>