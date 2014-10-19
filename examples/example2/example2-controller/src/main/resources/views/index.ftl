<html>
    <head>
        <title>Index</title>
    </head>
    <body>
        <h1>Example2</h1>

    <#if loggedIn>
        <p>Welcome ${user.userName}!</p>
        <a href="<@html.href controller="example2" action="logout" />">Logout</a>
    <#else>
        <form method="post" action="<@html.href controller="example2" action="login" />">
            <p>Username: <input name="username" type="text" value=""/></p>

            <p>Password: <input name="password" type="password" value=""/></p>
            <input type="submit" value="Login"/>
        </form>
    </#if>

    </body>
</html>