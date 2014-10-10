<html>
    <head>
        <title>Index</title>
    </head>
    <body>
        <h1>HomeController.index()</h1>
        <div>Hello ${model.name}!</div>
        <form method="post" action="/demo/home/login">
            <input type="text" name="username" />
            <input type="password" name="password" />
            <input type="submit" value="Login!" />
        </form>
    </body>
</html>