<html>
    <head>
        <title>Available Examples</title>
    </head>
    <body>
        <h1>Available examples</h1>
        <#list controllers as controller>
            ${controller.className}
        </#list>
    </body>
</html>