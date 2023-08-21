## Drones

### Build & run

Before you launch application for the first time you need to install and launch PostgreSQL database. Also, you need to
create database 'drones_db' and schema 'changes' in it.

If you have IDE, e.g. IntelliJ IDEA, simply choose DroneApplication file and click the run button left from 'public
class DroneApplication'

In other case, open console at the root of this application. Type 'mvn clean package', press Enter and wait for result.
When process is finished, type 'java -jar target/drones-1.0.0.jar' and press Enter.

### Database

You can access database with any postgres client. Use this URL when creating a connection:
**jdbc:postgresql://localhost:5432/drones_db/drones_schema**. Username and password are 'postgres'.

### Requests

In IDE you can open request-examples folder and choose request you want to make to the server. Simply put your values or
use default and press play button on the left. You can also make all these requests in Postman or in CLI using curl or
anything you want.

### Tests

Before you run tests for the first time you need to install and launch PostgreSQL database. Also, you need to create
database 'drones_test_db' and schema 'changes' in it. Username and password are 'postgres'.

IDE: you can find all the tests in src/test/java folder, run them separately or all at once for each test file.

Or you can type 'mvn clean test' if you want to run all the tests at once.
