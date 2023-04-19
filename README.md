
This e-Banking Portal provides a reusable ***REST API*** for returning the paginated list of money account transactions created in an arbitrary calendar month for a given customer who is logged-on in the portal. For each transaction 'page', it returns the total credit and debit values at the current exchange rate (from a third-party provider). The list of transactions is consumed from a Kafka topic. This project includes a Docker image of the application and the configuration for deploying it to Kubernetes / OpenShift.

# Features

- Paginated list of transactions for a specific customer and calendar month
- Total credit and debit values at the current exchange rate
- Consumption of transaction data from a Kafka topic
- JWT authentication and Java Security
- Docker containerization
- Kubernetes/OpenShift deployment configuration

#  Bussines Requirements

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

### Component Diagram
![Component Diagram](./png/Component%20Diagram.png)


# API
### Access the Swagger API documentation
Navigate to the Swagger API documentation page:

```bash
http://localhost:8080/swagger-ui/index.html#/
```
![API](./png/api.png)


# Set Up

## Docker
For setting kafka and zookeeper, I using [zk-single-kafka-single.yml](https://github.com/conduktor/kafka-stack-docker-compose/blob/master/zk-single-kafka-single.yml)

I have also pushed my e-banking-portal image to Docker Hub under the repository [eddie56/ebanking-portal](https://hub.docker.com/repository/docker/eddie56/ebanking-portal/general)

Start running the Docker containers for  **ebanking-portal**, **kafka**, and **zookeeper**:
```bash
docker-compose up 
```

Docker Container:
```bash
CONTAINER ID   IMAGE                             COMMAND                  CREATED         STATUS         PORTS                                                                      NAMES
2608f3d5a033   eddie56/ebanking-portal:latest    "java -jar /app/kafk…"   3 minutes ago   Up 3 minutes   0.0.0.0:8080->8080/tcp                                                     ebanking-portal
e79ac332c5bb   confluentinc/cp-kafka:7.3.2       "/etc/confluent/dock…"   7 hours ago     Up 3 minutes   0.0.0.0:9092->9092/tcp, 0.0.0.0:9999->9999/tcp, 0.0.0.0:29092->29092/tcp   kafka1
02cf7bfb4c06   confluentinc/cp-zookeeper:7.3.2   "/etc/confluent/dock…"   7 hours ago     Up 3 minutes   2888/tcp, 0.0.0.0:2181->2181/tcp, 3888/tcp                                 zoo1
```

***

## Kafka
Access the shell of the "kafka1" container:
```bash
docker exec -it kafka1 bash
```

Run the kafka-topics script to create the "transactions" topic with 3 partitions:
```bash
kafka-topics --create --bootstrap-server kafka1:19092 --topic transactions --partitions 3 --replication-factor 1
```
Check the details of the existing topic:
```bash
kafka-topics --bootstrap-server kafka1:19092 --topic transactions --describe
```
```bash
Topic: transactions	TopicId: t4xrMkS-Q1eQYfAoWLisBQ	PartitionCount: 3	ReplicationFactor: 1	Configs:
	Topic: transactions	Partition: 0	Leader: 1	Replicas: 1	Isr: 1
	Topic: transactions	Partition: 1	Leader: 1	Replicas: 1	Isr: 1
	Topic: transactions	Partition: 2	Leader: 1	Replicas: 1	Isr: 1
```

# Test

#### 1. Authenticate and obtain a JWT token
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
#### 2. Test GET Request with Bearer Token

```bash
GET http://localhost:8080/api/transactions/

Bearer Token
Token: ENTER_JWT_TOKEN
```
#### 3. Mock data using Kafka producer.

```bash
POST http://localhost:8080/api/transactions/mock

Bearer Token
Token: ENTER_JWT_TOKEN
```

#### 4. Test GET Request to retrieve the transactions for March 2023

```bash
GET http://localhost:8080/api/transactions/03/2023

Bearer Token
Token: ENTER_JWT_TOKEN
```

User information:

| UserName      | Password      | Identity Key| Account Iban |
| ------------- |:-------------:| :--------:  | :--------:   |
| eddie         | 1234          | P-0123456789| CH93-0000-0000-0000-0000-0 |
| tyler         | 4321          | P-1111111111| CH93-0000-0000-0000-0000-1 |

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


Tyler GET Request to retrieve the transactions for March 2023
```bash
http://localhost:8080/api/transactions/03/2023
{
    "content": [
        {
            "identityKey": "P-1111111111",
            "uniqueIdentifier": "27t8p661-ks1m-627c-t5bb-g6r2f1joYqh88",
            "amount": 50,
            "amountCurrency": "JPY",
            "accountIban": "CH93-0000-0000-0000-0000-1",
            "valueDate": "2023-03-12T05:00:00.000+00:00",
            "description": "Online payment JPY"
        },
        {
            "identityKey": "P-1111111111",
            "uniqueIdentifier": "41b4c072-fm8n-029x-r4dd-f4s7e1heTwp92",
            "amount": 50,
            "amountCurrency": "JPY",
            "accountIban": "CH93-0000-0000-0000-0000-1",
            "valueDate": "2023-03-11T05:00:00.000+00:00",
            "description": "Online payment JPY"
        },
        {
            "identityKey": "P-1111111111",
            "uniqueIdentifier": "72e3q890-kb7s-811z-a2cc-b2k4t8klRfh45",
            "amount": 20,
            "amountCurrency": "JPY",
            "accountIban": "CH93-0000-0000-0000-0000-1",
            "valueDate": "2023-03-09T05:00:00.000+00:00",
            "description": "Online payment JPY"
        }
    ],
    "page": 0,
    "size": 5,
    "totalPages": 1,
    "totalElements": 3,
    "totalCredit": 30,
    "totalDebit": 0
}
```

Eddie GET Request to retrieve the transactions for March 2023

```bash
http://localhost:8080/api/transactions/03/2023?page=0
{
    "content": [
        {
            "identityKey": "P-0123456789",
            "uniqueIdentifier": "83a5d938-vn6t-503g-p1dd-h3j5q3jrMlk3",
            "amount": 50,
            "amountCurrency": "JPY",
            "accountIban": "CH93-0000-0000-0000-0000-0",
            "valueDate": "2023-03-08T05:00:00.000+00:00",
            "description": "Online payment JPY"
        },
        {
            "identityKey": "P-0123456789",
            "uniqueIdentifier": "53t9r283-fg5l-092w-u2dd-g2h8i6liQsd76",
            "amount": 50,
            "amountCurrency": "JPY",
            "accountIban": "CH93-0000-0000-0000-0000-0",
            "valueDate": "2023-03-07T05:00:00.000+00:00",
            "description": "Online payment JPY"
        },
        {
            "identityKey": "P-0123456789",
            "uniqueIdentifier": "16f7j649-mn0p-819z-q8bb-e5r7y5leMng48",
            "amount": 50,
            "amountCurrency": "JPY",
            "accountIban": "CH93-0000-0000-0000-0000-0",
            "valueDate": "2023-03-06T05:00:00.000+00:00",
            "description": "Online payment JPY"
        },
        {
            "identityKey": "P-0123456789",
            "uniqueIdentifier": "29s2f584-is7k-622z-e4dd-b4g6t4pgNdk9",
            "amount": 150,
            "amountCurrency": "USD",
            "accountIban": "CH93-0000-0000-0000-0000-0",
            "valueDate": "2023-03-05T05:00:00.000+00:00",
            "description": "Online payment UDS"
        },
        {
            "identityKey": "P-0123456789",
            "uniqueIdentifier": "34c6d859-0b8t-394c-a1cc-f1d9e9lkMgd47",
            "amount": 150,
            "amountCurrency": "USD",
            "accountIban": "CH93-0000-0000-0000-0000-0",
            "valueDate": "2023-03-04T05:00:00.000+00:00",
            "description": "Online payment USD"
        }
    ],
    "page": 0,
    "size": 5,
    "totalPages": 2,
    "totalElements": 8,
    "totalCredit": 50,
    "totalDebit": 0
```
```bash
http://localhost:8080/api/transactions/03/2023?page=1
{
    "content": [
        {
            "identityKey": "P-0123456789",
            "uniqueIdentifier": "67a7p739-ns0f-811a-u1dd-f3e7q3qrTlp21",
            "amount": 150,
            "amountCurrency": "USD",
            "accountIban": "CH93-0000-0000-0000-0000-0",
            "valueDate": "2023-03-03T05:00:00.000+00:00",
            "description": "Online payment USD"
        },
        {
            "identityKey": "P-0123456789",
            "uniqueIdentifier": "92d43cde-4s2o-5123-1521-523lsd018",
            "amount": 100,
            "amountCurrency": "USD",
            "accountIban": "CH93-0000-0000-0000-0000-0",
            "valueDate": "2023-03-02T05:00:00.000+00:00",
            "description": "Online payment USD"
        },
        {
            "identityKey": "P-0123456789",
            "uniqueIdentifier": "89d3o179-blbc-465b-o9ee-e2d5f6ofEld46",
            "amount": 150,
            "amountCurrency": "CHF",
            "accountIban": "CH93-0000-0000-0000-0000-0",
            "valueDate": "2023-03-01T05:00:00.000+00:00",
            "description": "Online payment CHF"
        }
    ],
    "page": 1,
    "size": 5,
    "totalPages": 2,
    "totalElements": 8,
    "totalCredit": 30,
    "totalDebit": 0
}
}
```

# Deploy to Kubernetes

This part demonstrates how to deploy an ebanking portal with Kafka and Zookeeper to a Kubernetes cluster.
- A running Kubernetes cluster (e.g., Minikube, GKE, or EKS)
- kubectl installed and configured to access the cluster
  
<br>

#### Apply the Zookeeper, Kafka, and ebanking-portal manifest:
```bash
kubectl apply -f zookeeper.yaml
kubectl apply -f kafka.yaml
kubectl apply -f ebanking-portal.yaml
```

#### Check the status of the deployed resources:
```bash
kubectl get all

NAME                                   READY   STATUS    RESTARTS   AGE
pod/ebanking-portal-5cf9588848-bsxxf   1/1     Running   0          63s
pod/kafka1-0                           1/1     Running   0          4m6s
pod/zoo1-0                             1/1     Running   0          4m10s

NAME                      TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
service/ebanking-portal   NodePort    10.99.136.186    <none>        8080:30100/TCP   63s
service/kafka1            ClusterIP   10.109.169.127   <none>        19092/TCP        16m
service/kubernetes        ClusterIP   10.96.0.1        <none>        443/TCP          17m
service/zoo1              ClusterIP   10.111.125.217   <none>        2181/TCP         16m

NAME                              READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/ebanking-portal   1/1     1            1           63s

NAME                                         DESIRED   CURRENT   READY   AGE
replicaset.apps/ebanking-portal-5cf9588848   1         1         1       63s

NAME                      READY   AGE
statefulset.apps/kafka1   1/1     4m6s
statefulset.apps/zoo1     1/1     4m10s
```

#### Get the node details:
```bash
kubectl get node -o wide

NAME       STATUS   ROLES           AGE     VERSION   INTERNAL-IP    EXTERNAL-IP   OS-IMAGE             KERNEL-VERSION     CONTAINER-RUNTIME
minikube   Ready    control-plane   3h20m   v1.26.3   192.168.67.2   <none>        Ubuntu 20.04.5 LTS   5.15.49-linuxkit   docker://23.0.2
```

#### Access the ebanking portal using the INTERNAL-IP and NodePort:
```bash
http://192.168.67.2:30100/
```
