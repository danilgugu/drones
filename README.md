## Drones

### Build & run

If you have IDE, e.g. IntelliJ IDEA, simply choose DroneApplication file and click the run button left from 'public
class DroneApplication'

In other case, open console at the root of this application. Type 'mvn clean package', press Enter and wait for result.
When process is finished, type 'java -jar target/drones-1.0.0.jar' and press Enter.

### Database

After you start the application you can access the database via the link http://localhost:8080/h2-console
Enter password: 'sa' and press Connect. Database will be removed after you stop the application. If you want to prevent
it, you can use file-based storage. You need just edit **spring.datasource.url** property in application.properties file
this way:
spring.datasource.url=jdbc:h2:file:/data/demo

### Requests

In IDE you can open request-examples folder and choose request you want to make to the server. Simply put your values or
use default and press play button on the left. You can also make all these requests in Postman or in CLI using curl or
anything you want.

### Tests

IDE: you can find all the tests in src/test/java folder, run them separately or all at once for each test file.

Or you can type 'mvn clean test' if you want to run all the tests at once.
