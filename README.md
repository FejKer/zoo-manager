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

## Getting existing entities
By calling `GET` request on endpoint `/animals` we can simply get every animal in our database.

To search for an entity with its `id` or `name` please refer to endpoints description above.

```
{
    "_embedded": {
        "lionList": [
            {
                "id": 1,
                "name": "CreatedLion",
                "species": "Lion",
                "requiredFood": 11,
                "zoneName": "z2",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/animals/id/1"
                    },
                    "zone": {
                        "href": "http://localhost:8080/zones/id/1"
                    },
                    "animals": {
                        "href": "http://localhost:8080/animals"
                    }
                }
            },
            {
                "id": 2,
                "name": "CreatedLion",
                "species": "Lion",
                "requiredFood": 11,
                "zoneName": "z2",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/animals/id/2"
                    },
                    "zone": {
                        "href": "http://localhost:8080/zones/id/1"
                    },
                    "animals": {
                        "href": "http://localhost:8080/animals"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/animals"
        }
    }
}
```

By calling `GET` request on `/zones` API returns every zone in database as well as relevant information about animals that are assigned to it, like `zoneRequiredFood` or `animalsCount`, as well as whole `animalSet`

```
{
    "_embedded": {
        "zoneList": [
            {
                "id": 1,
                "animalsCount": 2,
                "zoneRequiredFood": 22,
                "name": "z2",
                "animalSet": [
                    {
                        "id": 1,
                        "name": "CreatedLion",
                        "species": "Lion",
                        "requiredFood": 11,
                        "zoneName": "z2"
                    },
                    {
                        "id": 2,
                        "name": "CreatedLion",
                        "species": "Lion",
                        "requiredFood": 11,
                        "zoneName": "z2"
                    }
                ],
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/zones/id/1"
                    },
                    "zones": {
                        "href": "http://localhost:8080/zones"
                    },
                    "animals": [
                        {
                            "href": "http://localhost:8080/animals/id/1"
                        },
                        {
                            "href": "http://localhost:8080/animals/id/2"
                        }
                    ]
                }
            },
            {
                "id": 2,
                "animalsCount": 0,
                "zoneRequiredFood": 0,
                "name": "z1",
                "animalSet": [],
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/zones/id/2"
                    },
                    "zones": {
                        "href": "http://localhost:8080/zones"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/zones"
        }
    }
}
```
