# zoo-manager
Technologies used
- JDK 17
- Spring
- H2 in-memory database

REST endpoints
- adding zones
- adding animal and appending it to zone
- getting animals from zone
- getting animals by name
- getting reports which zone needs the most food
- getting reports which zone has least animals

## Running the application

To run the application either download .jar file from releases or clone the repository and do `./mvnw clean spring-boot:run`

Tomcat server will be listening by default on **localhost:8080**

:exclamation: **Keep in mind that in file `LoadDatabase.java` are available preloaded animals and zone. If you want to use them you can safely uncomment method `initDatabase()` in the file** :exclamation:

## Endpoints
- **/zones**  
*Supports both `GET` and `POST` methods for retrieving and creating entities respectively*

  - **/least-populated**  
  *Supports `GET` method, returns zone that has the least animals assigned to it*
  
  - **/most-food**  
  *Supports `GET` method, returns zone that required the most food based on its habitants*
  
  - **/id/{id}**  
  *Supports `GET` method, returns zone entity by specified id*
  
  - **/name/{name}**
  *Supports `GET` method, returns zone entity by specified name*
  
- **/animals**  
*Supports both `GET` and `POST` methods for retrieving and creating entities respectively*

  - **/id/{id}**  
  *Supports `GET` method, returns animal entity by specified id*
  
  - **/name/{name}**
  *Supports `GET` method, returns animal entity/entities by specified name*


## Creating new entities
In examples below I will be using `curl` tool

- Creating zone with name *z2*
  - `curl -X POST localhost:8080/zones -H 'Content-type:application/json' -d '{"name": "z2"}'`
- Creating *lion* with name *CreatedLion* and assigning it to zone *z2*
  - `curl -X POST localhost:8080/animals -H 'Content-type:application/json' -d '{"name": "CreatedLion", "species": "Lion", "zone": "z2"}'`
  
If above commands were succesfully processed you should see the entity body in response.
![image](https://user-images.githubusercontent.com/29582159/208294430-8b2d4a0c-7e4b-4399-90bf-9be10da6603d.png)

![image](https://user-images.githubusercontent.com/29582159/208294450-2f88cb9b-0e6e-4b02-98f3-2f3251100145.png)
