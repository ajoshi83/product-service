#product-svc

This is a product service poc.  Service use Spring Boot, MySql, Docker containerization etc.

### Features demonstrated in poc
1. RAD using Spring Boot
2. Spring Boot Profiles
3. Spring Boot Actuators
4. Hibernate validators and custom messages
5. Global Exception Handler
6. REST CRUD operations for product model
7. Pagination
8. Sorting
9. Filtering
10. Versioning
11. Swagger Documentation
12. JavaDocs
13. JUnit and Mockito testing

### Prerequisites to run the service locally
1. Connectivity with MySQL Server.
2. Use 'schema.sql' file at project root folder to build the product schema in MySql.
3. Use 'initialdata.sql' to load initial data in the 'product.product' table
4. Modify 'spring.datasource.url' property in 'src/main/resources/application.yml' to point correct MySql server.

## How to build and service locally?
1. Service use Gradle for build management.  It has got a 'gradlew' wrapper at path 'gradle/wrapper/gradle-wrapper.jar'
2. Open the console and change your directory to 'product-service'
3. Run 
    ./gradlew clean build
4. Run 
    java -jar -Dspring.profiles.active=dev build/libs/product-service-0.0.1-SNAPSHOT.jar

## Where to see API documentation?
1. Check what profile you are using to run service.  Service's http port changes according to profile as configured in 'application.properties'.
2. Visit 'http://localhost:<<port>>/swagger-ui.htm' 
3. Follow the API documentation

## Dockerize the service and run service in docker container
1. Make sure, docker platform is installed on your machine
2. Run 
    ./gradlew clean build docker -i
3. Step1 after successful build should create a docker image product-service:0.0.1-SNAPSHOT
4. Run
    docker run -d -p 8080:8080 -e "spring.profiles.active=dev" --hostname="product-svc" --name="product-svc" product-service:0.0.1-SNAPSHOT
5. To avoid communication link failure error, make sure product-service container and MySql container are running in the same network
6. Run
    docker logs -f product-svc
7. After testing the service, Run below command to shutdown the container
    docker stop product-svc
