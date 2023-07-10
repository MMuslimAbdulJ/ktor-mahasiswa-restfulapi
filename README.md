# API Spec

## About this project
This is a project that I created to practice writing code that adheres to the principles of Clean Code and Clean Architecture (I hope). 
With the help of Kotlin magic and the Ktor framework, I can freely create whatever I want in this project. 
Ktor is a more functional-oriented framework, which allows me to easily switch between using OOP and Functional programming in Kotlin. 
Kotlin is excellent for combining both paradigms. However, in one particular case, I felt confused when using this framework because the available resources for Ktor are still very limited. 
This is mainly because Ktor is a relatively new framework, especially within the Kotlin programming community, where Spring is more commonly used. Grazie ✌🏻...

## Main dependencies
- Engine : `Netty`
- Connection Pool : `HikariCP`
- Serialization : `Content Negotiation`,`kotlinx.serialization`, `Jackson`
- ORM : `Ktorm`
- Dependency Injection Helper : `Koin`
- DotEnv : `cdimascio/dotenv-kotlin`
- Unit Test/Integration Test : `JUnit 5`
- Mock Test : `Mockito`

## Create Mahasiswa

Request :

-   Method : POST
-   Endpoint : `api/mahasiswa/{nim}`
-   Header :
    -   Content-Type : application/json
    -   Accept : application/json
-   Body :

```json
{
    "nim": "string, unique (9)",
    "nama": "string",
    "fakultas": "string",
    "prodi": "string"
}
```

Response :

```json
{
    "code": "number",
    "status": "string",
    "data": {
        "nim": "string, unique (9)",
        "nama": "string",
        "fakultas": "string",
        "prodi": "string",
        "created_at": "datetime",
        "updated_at": "datetime"
    }
}
```

## Get All Mahasiswa

Request :

-   Method : GET
-   Endpoint : `api/mahasiswa`
-   Header :
    -   Accept : appliaction/json

Response :

```json
{
    "code": "number",
    "status": "string",
    "data": [
        {
            "nim": "string, unique (9)",
            "nama": "string",
            "fakultas": "string",
            "prodi": "string",
            "created_at": "datetime",
            "updated_at": "datetime"
        },
        {
            "nim": "string, unique (9)",
            "nama": "string",
            "fakultas": "string",
            "prodi": "string",
            "created_at": "datetime",
            "updated_at": "datetime"
        },
        ...
    ]
}
```

## Get Mahasiswa By NIM

Request :

-   Method : GET
-   Endpoint : `api/mahasiswa/{nim}`
-   Header :
    -   Accept : appliaction/json

Response :

```json
{
    "code": "number",
    "status": "string",
    "data": {
        "nim": "string, unique (9)",
        "nama": "string",
        "fakultas": "string",
        "prodi": "string",
        "created_at": "datetime",
        "updated_at": "datetime"
    }
}
```

## Update Mahasiswa

Request :

-   Method : PATCH
-   Endpoint : `api/mahasiswa{nim}`
-   Header :
    -   Content-Type : application/json
    -   Accept : application/json
-   Body :

```json
{
    "nama": "string",
    "fakultas": "string",
    "prodi": "string"
}
```

Response :

```json
{
    "code": "number",
    "status": "string",
    "data": {
        "nama": "string",
        "fakultas": "string",
        "prodi": "string",
        "updated_at": "datetime"
    }
}
```

## Delete Mahasiswa

Request :

-   Method : DELETE
-   Endpoint : `api/mahasiswa/{nim}`
-   Header :
    -   Accept : application/json

Response :

```text
No Content
```