
## Challenge:

George is a local travel guide who provides private hiking tours of the mountains in his locality. Hikers would contact him over the phone to go on three different hike trails: Shire, Gondor and Mordor. He would enter
each booking request in a spreadsheet along with the hikers' information. As the number of requests for his services are increasing, he is finding it difficult to handle his business efficiently.

We have to design and implement a REST-based Backend application that would allow hikers to perform the following tasks:

-  View all the trails available for hiking

-  View a selected trail

-  Book a selected trail for hiking

-  View a booking

-  Cancel a booking

## How it works:
### **1. Docker. First, you need to install docker**
* Download Docker [Here](https://docs.docker.com/desktop/).
* Then open terminal and check:
```bash
docker info
```
or check docker version
```bash
docker -v
```
or docker compose version
```bash
docker-compose -v
```
### **2. Spring boot app**
* Build the maven project:
```bash
mvn clean install
```
* Build the maven project by skipping the tests (Optional):
```bash
mvn package -Dmaven.test.skip=true
```

* Running the containers:

This command will build the docker containers and start them.
```bash
docker-compose up
```
or

This is a similar command as above, except it will run all the processes in the background.
```bash
docker-compose -f docker-compose.yml up
```

Appendix A.

All commands should be run from project root (where docker-compose.yml locates)

* If you have to want to see running containers. Checklist docker containers
```bash
docker container list -a
```
or
```bash
docker-compose ps
```

### **Guide for using endpoints of the app:**
Swagger API [http://localhost:8088/swagger-ui.html](http://localhost:8088/swagger-ui.html)

and generation API docks [http://localhost:8088/v3/api-docs.yaml](http://localhost:8088/v3/api-docs.yaml)

Or You can look into the project folder for the postman collection that I have created **Tour-Guide-App.postman_collection.json**. Please note, we require postman in our system in order to run it as collection or to look into it as a endpoint refernces.

### **4. Docker control commands**
* Check all the images you have:
```bash
docker images
```
### **5. End stop app**
*  Stop containers:
```bash
docker-compose down
```
* Remove old stopped containers of docker-compose
```bash
docker-compose rm -f
```

## Thoughts On Design and Area of Improvement:

### Assumption:

For the scenario, where hiker has one or more hiker as a companion and any one or all of the hiker does not meet the hike trail age requirements, I am asking to re-initiate the booking with eligible hikers. However, it may change based on the requirement, we may also save the booking with eligible hikers.

### Design:

- Postgres has been used for application code whereas integration tests are using in memory H2 db
- spotify maven plugin is being used to generate the docker image as a part of maven build.
- In the application folder, I have also added a postman collection, running it as a part of CI pipeline (Jenkins / Circle CI) would automate the functional testing with predefined assertions.
- I am assuming few master data is loaded in the system when initialised, taking the help of application ready event.
- docker containers has a specific interval health check with the application actuator endpoint

### Area of Improvement:

- Though I have added integration test cases for all endpoints, but keeping the time constrain in mind, I was not able to add more Junits and integration test for all Classes. As a further improvement of this project, I would add Junits and integration test to cover all edge case scenarios.
- I have used application ready event to load master file data, However, I am aware of the fact that, we can do it as a part of postgres db startup from sql file as well. As a further improvement, I would implement that.
- I would improve swagger information which is available in swagger ui to more make it more user friendly.
