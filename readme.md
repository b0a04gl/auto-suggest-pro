
---

# Auto Suggest Pro

Auto Suggest Pro is a Java project that provides a type-ahead suggestion feature backed by a Trie data structure and Redis. The project is divided into two modules: Aggregator and Suggester.


### Aggregator Module

- Asynchronously collects user-entered terms, maintains a real-time term frequency Trie for efficient data processing, and flushes the Trie into Redis every 15 minutes.
- Provides seamless support for efficient prefix-based lookup of suggestion data and ensures data freshness when stored in Redis.

### Suggester Module
- Serves user queries with the top 10 frequent suggestions by querying Redis, ensuring real-time and up-to-date results.
- Utilizes an in-memory Trie for rapid suggestion data lookup and periodic synchronization with Redis for freshness.


## Project Structure

The project follows a Maven project structure and is divided into two modules:

- `aggregator`: The Aggregator module.
- `Suggester`: The Suggester module.

Each module has its own `pom.xml` for dependency management.

## Dependencies

The project uses the following dependencies:

- Java (JDK 8 or higher)
- Spring Framework
- Redis
- Maven

## Configuration

You can configure the interval for transferring data to Redis by setting the `redis.transfer.interval` property in the `application.properties` or `application.yml` file. The interval is specified in seconds.

```properties
redis.transfer.interval=300000 # Set the transfer interval to 300000 milliseconds (5 minutes)
```

## Usage

1. Build the project using Maven:

   ```
   mvn clean install
   ```

2. Run each module separately. For example, you can start the Aggregator module with the following command:

   ```
   java -jar aggregator/target/aggregator.jar
   ```

   Start the Suggester module in a similar way.

3. Use the API provided by the Suggester to retrieve suggestions.

## API Endpoints
- aggregator module API:

   `POST http://localhost:8081/terms/collect?term=scatter`

- suggester module API:
   `GET http://localhost:8080/suggester/suggestWords?userInput=sc`


## Demo
![Screen Recording 2023-10-25 at 9.28.42 AM copy.gif](suggester%2Fsrc%2Fmain%2Fresources%2FScreen%20Recording%202023-10-25%20at%209.28.42%20AM%20copy.gif)
