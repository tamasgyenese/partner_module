# Partner Module
 Project with [Maven](https://maven.apache.org/guides/mini/guide-multiple-modules.html) to reserve a seat for an event. It calls the endpoints of
 [Ticket System Management](https://github.com/tamasgyenese/ticket_system_backend)' Ticket module using Base64 encoded token for User validation.
 <br>
 Endpoints:
 In the Api module there are three end points:
 * /ticket/getEvents -> return all Event [GET]
 * /ticket/getEvent/id -> return a specific event with details [GET]
 * /ticket/reserve [POST] requires: eventId, seatId, cardId
 <br>
 The Ticket module sends back a response with an API-KEY and the hash code of this value and the hashing logic are stored in
 Partner module. To compare these values we can restrict the communication, because we would like to get response only from Ticket module.
## Built With
* [Spring Boot 2](https://spring.io/projects/spring-boot)

## Authors
* Tamas Gyenese