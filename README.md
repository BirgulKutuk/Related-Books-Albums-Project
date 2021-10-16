# Related-Books-Albums-Project

# Assignment

Using your favorite Go or Java framework / libraries build a service, that will accept a request with text parameter on input. It will return maximum of 5 books and maximum of 5 albums that are related to the input term. The response elements will only contain title, authors(/artists) and information whether it's a book or an album. For albums please use the iTunes API. For books please use Google Books API. Sort the result by title alphabetically. Make sure the software is production-ready from resilience, stability and performance point of view. The stability of the downstream service may not be affected by the stability of the upstream services. Results originating from one upstream service (and its stability /performance) may not affect the results originating from the other upstream service.

Make sure the service:

- Your service needs to respond within a minute;
- is self-documenting
- exposes metrics on response times for upstream services
- exposes health check
- Limit of results on upstream services must be configurable per environment and preconfigured to 5.

Bonus points:
- Think about resilience
- Think about concurrency

Please document how we can run it. Please shortly document your justification of technology / mechanism choice.


# Development

Related Books Albums is a Spring Boot and maven project.
getRelatedBooksAndAlbums is a RESTful web service. It takes input from the user and finds 5 related books in Google Books API and 5 related albums in iTunes API.
The service has Swagger implementation to call and test it. It's ready to call from test cases, web service, or UI.

* Limit of results on upstream services preconfigured to 5 (AppConstants.MAX_COUNT_OF_EACH_RELATED_ITEM)

* Server timeout set to 60 seconds.

* To run the application please run these commands;
mvn clean install
mvn spring-boot:run
Open "http://localhost:8080/swagger-ui/index.html#/" in a web browser.

* versions
Spring Boot : 2.5.5
Springfox Swagger  3.0.0
Gson : 2.8.8
iTunes : 4.0

Google Books Url : https://www.googleapis.com/books/v1/volumes
