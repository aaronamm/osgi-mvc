<html>
    <head>
        <title>Index</title>
    </head>
    <body>
        <h1>Example3</h1>

        /views/index.ftl rendered by the example3-controller

        <a href="<@html.href controller="example3" action="displayViewFromViewBundle" />">Display View from View
            Bundle</a><br/>
        <a href="<@html.href controller="example3" action="displayViewFromAnyBundle" />">Display View from Any
            Bundle</a><br/>
        <a href="<@html.href controller="example3" action="displayViewFromAnyBundleAndAnyRange" />">Display View from
            Any Bundle
            and Any range</a>


    </body>
</html>