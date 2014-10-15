# osgi-mvc #

Installation notes:

1. Download & Install java (apache karaf 3.0.1 do not support Java 8)
2. Download & Unpack apache-karaf-3.0.1
3. Download build with maven (mvn clean install)
4. Copy core-\*/target/core-*.jar into karaf/deploy
4.1 If apache-commons libraries are missing then copy the external-libraries.jar as well
5. Copy templates-\*/target/templates-*.jar into karaf/deploy
5. Copy demo-\*/target/demo-*.jar into karaf/deploy
6. Start karaf (./bin/karaf.sh)
7. Ensure that the war feature is installed (feature:install war)

Visit demo page at http://localhost:8181/demo/ or http://localhost:8181/demo/home/index
