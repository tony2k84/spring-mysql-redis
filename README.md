## Oveview
springboot-mysql-redis. This is a sample test project to test mysql and redis integration from spring boot. This revolves around the idea of storing the interview candidates in database and searching based on the id should happen really fast. For this, we bring the data from database post the first query to redis as a cache. Thought process around this is to have this in cache and then whoever are selected, those can be persisted in the database later as the next enhancement or step.

The application is 2 folds
* Initialization and creation of candidates using POST API
* Exposing the list of candidates to be fetched from database
* Exposing an API to find candidates by ID from cache or database accordingly.

## Setup
This assume that REDIS is running on localhost 6379 (default) port. If this is changed, please update the application.properties file accordingly.

This assumes that MYSQL is running on localhost 3306 (default) port. If this is changed, please update the application.properties file accordingly.

## Testing
Run the application as standard spring boot application

Invoke post API to create sample random users. POST /candidate. This can be called multiple times to add entry to the database tables.

Invoke API to get all users. GET /candidates
Invoke API to get a user by ID. GET /candidate?id=<VALUE>

## Observations
It is observed that with large datasets, the database calls works 22ms where as from cache it works in 5ms. This can be exptrapolated to more data and is clear that cache is they way to go when load/performance on database is a concern for end user facing applications.