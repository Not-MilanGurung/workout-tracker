# Running the application
Use the maven wrapper (mvnw for linux/mac and mvnw.cmd for windows) to compile and run the application
Install the modular jar file for bcrypt and it's dependency
`mvn install:install-file -Dfile="bcrypt-0.10.2.jar"  -DgroupId="at.favre.lib"  -DartifactId=bcrypt  -Dversion="0.10.2-mod"  -Dpackaging=jar`
`mvn install:install-file -Dfile="bytes-1.5.0.jar"  -DgroupId="at.favre.lib"  -DartifactId=bytes  -Dversion="1.5.0-mod"  -Dpackaging=jar`
Install the file to be able to add the backend as dependency for frontend
`mvn clean install`
The 'frontend' module is the entry point to the application
`./mvnw clean javafx:run -pl frontend`