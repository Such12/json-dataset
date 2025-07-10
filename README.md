Dynamic JSON Dataset API
This Spring Boot application provides a flexible API for managing JSON dataset records, supporting dynamic insertion, grouping, and sorting operations. It uses a relational database (H2 in-memory by default) to persist the JSON data.

Features
Insert Record API: Easily add new JSON records to specific datasets.
Query API: Retrieve records from a dataset with dynamic group-by and sort-by capabilities.
Relational Database Storage: Persists JSON data in a structured manner using a relational database (H2, configurable for others).
Clear Request/Response Structures: Well-defined API endpoints with intuitive JSON payloads.

Technologies Used
Spring Boot: For rapid application development.
Spring Data JPA: For database interaction.
H2 Database: An in-memory relational database for easy setup and testing. (Can be swapped for PostgreSQL, MySQL, etc.)
Jackson: For JSON processing.

Getting Started
Prerequisites
Java 17 or higher

Maven

Running the Application
Clone the repository:

Bash

git clone https://github.com/YOUR_USERNAME/your-repo-name.git
cd your-repo-name
Build the project:

Bash

mvn clean install
Run the application:

Bash

mvn spring-boot:run
The application will start on port 8080 by default.

API Endpoints
The API base URL is http://localhost:8080/api/dataset.

1. Insert Record API
Inserts a JSON record into a specified dataset.
URL: /api/dataset/{datasetName}/record
Method: POST
URL Parameters:
datasetName: The name of the dataset (e.g., employee_dataset).
Request Body (JSON): Any valid JSON object representing the record.

JSON

{
    "id": 1,
    "name": "John Doe",
    "age": 30,
    "department": "Engineering"
}
Example Response (200 OK):

JSON

{
    "message": "Record added successfully",
    "dataset": "employee_dataset",
    "recordId": 1
}

2. Query API
Queries a specific dataset and performs group-by or sort-by operations.
URL: /api/dataset/{datasetName}/query
Method: GET
URL Parameters:
datasetName: The name of the dataset (e.g., employee_dataset).

Query Parameters:

groupBy: (Optional) The field name to group records by (e.g., department).
sortBy: (Optional) The field name to sort records by (e.g., age).
order: (Optional, default asc) The sort order (asc for ascending, desc for descending).

Example 1: Query API with Group-By
Request URL:

Bash

GET /api/dataset/employee_dataset/query?groupBy=department
Example Response (200 OK):

JSON

{
    "groupedRecords": {
        "Engineering": [
            { "id": 1, "name": "John Doe", "age": 30, "department": "Engineering" },
            { "id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering" }
        ],
        "Marketing": [
            { "id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing" }
        ]
    }
}

Example 2: Query API with Sort-By
Request URL:

Bash

GET /api/dataset/employee_dataset/query?sortBy=age&order=asc
Example Response (200 OK):

JSON

{
    "sortedRecords": [
        { "id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering" },
        { "id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing" },
        { "id": 1, "name": "John Doe", "age": 30, "department": "Engineering" }
    ]
}

Database Configuration
This application uses H2 Database by default, configured in src/main/resources/application.properties.

Properties

spring.datasource.url=jdbc:h2:mem:jsondb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update
You can access the H2 console at http://localhost:8080/h2-console to view the database content. Use jdbc:h2:mem:jsondb as the JDBC URL.

I will add a new section called "Testing" to the README.md and include instructions on how to run the tests using Maven.

Here's the updated README.md with the "Testing" section:

Dynamic JSON Dataset API
This Spring Boot application provides a flexible API for managing JSON dataset records, supporting dynamic insertion, grouping, and sorting operations. It uses a relational database (H2 in-memory by default) to persist the JSON data.

Features
Insert Record API: Easily add new JSON records to specific datasets.

Query API: Retrieve records from a dataset with dynamic group-by and sort-by capabilities.

Relational Database Storage: Persists JSON data in a structured manner using a relational database (H2, configurable for others).

Clear Request/Response Structures: Well-defined API endpoints with intuitive JSON payloads.

Technologies Used
Spring Boot: For rapid application development.

Spring Data JPA: For database interaction.

H2 Database: An in-memory relational database for easy setup and testing. (Can be swapped for PostgreSQL, MySQL, etc.)

Jackson: For JSON processing.

JUnit 5: For writing unit and integration tests.

Mockito: For mocking dependencies in tests.

Getting Started
Prerequisites
Java 17 or higher

Maven

Running the Application
Clone the repository:

Bash

git clone https://github.com/YOUR_USERNAME/your-repo-name.git
cd your-repo-name
Build the project:

Bash

mvn clean install
Run the application:

Bash

mvn spring-boot:run
The application will start on port 8080 by default.

API Endpoints
The API base URL is http://localhost:8080/api/dataset.

1. Insert Record API
Inserts a JSON record into a specified dataset.

URL: /api/dataset/{datasetName}/record

Method: POST

URL Parameters:

datasetName: The name of the dataset (e.g., employee_dataset).

Request Body (JSON): Any valid JSON object representing the record.

JSON

{
    "id": 1,
    "name": "John Doe",
    "age": 30,
    "department": "Engineering"
}
Example Response (200 OK):

JSON

{
    "message": "Record added successfully",
    "dataset": "employee_dataset",
    "recordId": 1
}
2. Query API
Queries a specific dataset and performs group-by or sort-by operations.

URL: /api/dataset/{datasetName}/query

Method: GET

URL Parameters:

datasetName: The name of the dataset (e.g., employee_dataset).

Query Parameters:

groupBy: (Optional) The field name to group records by (e.g., department).

sortBy: (Optional) The field name to sort records by (e.g., age).

order: (Optional, default asc) The sort order (asc for ascending, desc for descending).

Example 1: Query API with Group-By
Request URL:

Bash

GET /api/dataset/employee_dataset/query?groupBy=department
Example Response (200 OK):

JSON

{
    "groupedRecords": {
        "Engineering": [
            { "id": 1, "name": "John Doe", "age": 30, "department": "Engineering" },
            { "id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering" }
        ],
        "Marketing": [
            { "id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing" }
        ]
    }
}
Example 2: Query API with Sort-By
Request URL:

Bash

GET /api/dataset/employee_dataset/query?sortBy=age&order=asc
Example Response (200 OK):

JSON

{
    "sortedRecords": [
        { "id": 2, "name": "Jane Smith", "age": 25, "department": "Engineering" },
        { "id": 3, "name": "Alice Brown", "age": 28, "department": "Marketing" },
        { "id": 1, "name": "John Doe", "age": 30, "department": "Engineering" }
    ]
}


Testing
This project incorporates Test-Driven Development (TDD) principles, with a comprehensive suite of unit and integration tests to ensure the reliability and correctness of the application.

To run the tests, navigate to the project's root directory in your terminal and execute the following Maven command:

Bash

mvn test
This command will compile the test sources and run all tests defined in the src/test directory. You will see the test execution report in the console.
