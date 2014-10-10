# osgi-mvc #

Installation notes:

1. Download java (apache karaf 3.0.1 do not support Java 8)
2. Download apache-karaf-3.0.1
3. Download build with maven (mvn clean install)
4. Copy core-api/target/core-api-*.jar into karaf/deploy
5. Copy core-impl/target/core-impl-*.jar into karaf/deploy
6. Copy demo-controller/target/demo-controller-*.jar into karaf/deploy
7. Copy demo-views/target/demo-views-*.jar into karaf/deploy

Visit demo page at http://localhost:8181/demo/ or http://localhost:8181/demo/home/index