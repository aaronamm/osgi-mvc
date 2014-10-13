<html>
<head>
    <title>Index</title>
</head>
<body>
<h1>HomeController.index()</h1>

<div>Hello ${model.name}!</div>
<form method="post" action="<@html.href controller="home" action="login" />">
    <input type="text" name="username"/>
    <input type="password" name="password"/>
    <input type="submit" value="Login!"/>
</form>

<!-- Call the HomeController#index2 method with the supplied parameters and return the result -->
<@html.render controller="home" action="index2" params={ "param1" : 10, "param2" : model.name } />

<!-- Call the HomeController#index3 method with the supplied parameters and return the result -->
<@html.render controller="home" action="index3" params={ "model" : model } />

<!-- Render the view located in any bundle with highest version number within the version range [0, 1) -->
<@html.render view="bundle://:[0, 1)/views/global2.ftl" params={ "param1" : 10, "param2" : "Hello World!" } />

</body>
</html>