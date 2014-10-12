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

        <!-- Call the HomeController#index method with the supplied parameters and return the result -->
        <@html.render controller="home" action="index2" params={ "param1" : 10, "param2" : "Hello World!" } />

        <!-- Render the view with the supplied context parameters in this bundle -->
        <#--<@html.render view="/path/to/view.ftl" params={ "param1" : 10, "param2" : "Hello World!" } /> -->

        <!-- Render a view located in a bundle within the version range [0, 1) with the supplied context parameters -->
        <#--<@html.render view="bundle://:[0, 1)/path/to/view.ftl" params={ "param1" : 10, "param2" : "Hello World!" } /> -->

    </body>
</html>