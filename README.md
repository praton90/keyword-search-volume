# keyword-search-volume

Java REST API to get estimate keyword search volume from Amazon Autocomplete AJAX API.

The search volume score is defined in the range of 0-100.

The returned search value score only represents an approximate integer value for the keyword received.

## Getting Started

Please go through the following steps to run this app locally.

### Prerequisites

- Java 8
- Maven.

### How to run it locally?

- Clone the repository.
- Access project directory. `cd keyword-search-volume`
- Run the java app `mvn spring-boot:run`

## Running the tests

- Execute `mvn test` 

## Technical decisions

### My assumptions
My assumption is that the keyword must match exactly with the suggestion text because of the following description in the exercise.
> This endpoint should receive a single keyword as an input and should return a score for that
 same exact keyword.
 
Taking that into account I am checking that the keyword received match exactly with the suggestion.

### How the algorithm works?
For each character of the keyword I am sending a request to the Amazon Autocomplete API to get the suggestions until the exact keyword match with one of the suggestions received.
In that moment I am calculating the score in base of the order of the suggestion and the weight of the character. Each character of the keyword is assigned to a specific value that will be used to calculate the final search volume score. 

In case that the keyword received does not match with the suggestion received the search volume score will be 0.

### The (*hint) that we gave you is correct and if so - why?
I think that the hint is not correct because in my opinion the results that are part of the suggestion list are ordered by relevance. That's means that the first result of the suggestion list is more relevant, so have a grater search volume score, than the last one.

### Precision of the algorithm
It's really difficult to say how accurate the search volume score is because is an estimation. 
Since I am using a public API in a production environment and people is using it all the time the result could be different after some time.

## Built With
This service was created using [Spring Initializr](https://start.spring.io/)

* [SpringBoot](https://spring.io/projects/spring-boot) - Spring framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [Lombok](https://projectlombok.org/) - Library to avoid boilerplate code.
* [Assertj](https://joel-costigliola.github.io/assertj/) - Test assertion library.
* [Mockito](https://site.mockito.org/) - Mocking framework for unit tests.