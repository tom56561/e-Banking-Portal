# Summary

For an e-Banking Portal you have been given the task to design and implement a reusable **REST API** for returning the paginated list of money account transactions created in an arbitrary calendar month for a given customer who is logged-on in the portal. For each transaction ‘page’ return the total credit and debit values at the current exchange rate (from the third-party provider). The list of transactions should be consumed from a Kafka topic. Build a Docker image out of the application and prepare the configuration for deploying it to Kubernetes / OpenShift.

#  Assumptions

-   Every e-banking client has one or more accounts in different currencies (e.g. GBP, EUR, CHF)
-   There are approximately one hundred thousand e-banking customers, each with a couple thousands of transactions per month.
-   The transactions cover the last ten years and are stored in Kafka with the key being the transaction ID and the value the JSON representation of the transaction
-   The user is already authenticated and the API client invoking the transaction API will send a JWT token containing the user’s unique identity key (e.g. P-0123456789)
-   The exchange rate on any given date is provided by an external API

For simplicity reasons, consider a money account transaction composed of the following attributes:
-   Unique identifier (e.g. 89d3o179-abcd-465b-o9ee-e2d5f6ofEld46)
-   Amount with currency (eg GBP 100-, CHF 75)
-   Account IBAN (eg. CH93-0000-0000-0000-0000-0)
-   Value date (e.g. 01-10-2020)
-   Description (e.g. Online payment CHF)

# Diagram
### Jwt + Java Scurity
![Jwt Security Diagram](./png/jwtSecurity.png)


# API + JWT
#### 1. Get the JWT token
Using postman:
```bash
POST http://localhost:8080/auth/token

Basic Auth
Username:eddie
Password:1234
```

Using httpie:
```bash
http POST :8080/token --auth eddue:1234 -v
```
#### 2. Test Get Request with Bearer Token

```bash
GET http://localhost:8080/api/transactions/

Bearer Token
Token: eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZWRkaWUiLCJleHAiOjE2ODA2Mzc0OTAsImlhdCI6MTY4MDYzMzg5MCwic2NvcGUiOiJyZWFkIn0.f1SgPrAZg3xiqRNXAOCnh2FkOQbPz93AYEtgtmNh-eBM3O2UBIzkan8AWWV2wQv2-DUXGfFad2Ud9WQorVGBskUvANptCdwP3ZXC6YHiQD6piQvEed4iqI9WkiQvDBzmgJNMFqp6VDZ7wgX9sXvGZ-vzVfIN7ySKpWQOWIFHPnQSxu_n2AY7OrM-ds1lg1i4ZRSEOoI1XhClS4TEyGmJuDdz99UJRUuc0SA_yhzDyuzPz5zXeRnxqcSQpzHZ86Mo0EPupgtTta5a4noE3bqx4yhZmUVBeQ75cUY5ZeAxj2sk7zBr4sCfWQ1FnWLZ_oM-oZLj0ThQgvJvONWjMVePQg
```
#### 3. Post Request with Bearer Token to Mock data by using Kafka producer.

```bash
Post http://localhost:8080/api/transactions/mock

Bearer Token
Token: ENTER_JWT_TOKEN
```

Mock data format be like this:
```bash
"key": "P-0123456789",
"value": {
   "identityKey": "P-0123456789",
   "uniqueIdentifier": "89d3o179-blbc-465b-o9ee-e2d5f6ofEld46",
   "amount": "150",
   "currency": "CHF",
   "ibanAccount": "CH93-0000-0000-0000-0000-0",
   "valueDate": "01-03-2023",
   "description": "Online payment CHF"
        }

"key": "P-0123456789",
"value": {
   "identityKey": "P-0123456789",
   "uniqueIdentifier": "92d43cde-4s2o-5123-1521-523lsd018",
   "amount": "100",
   "currency": "USD",
   "ibanAccount": "CH93-0000-0000-0000-0000-0",
   "valueDate": "02-03-2023",
   "description": "Online payment USD"
        }
......

```
