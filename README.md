
# Pismo Banking Application

Application for Customer to create an Account and perform transaction to Purchase, Withdraw, Credit and Debit amount.


## Installation

Start application

```bash
sh start.sh
```

## Run Locally

Clone the project

```bash
  git clone https://github.com/monika-meshram/pismo-banking.git
```

Go to the project directory

```bash
  cd pismo-banking
```

Start the Application

```bash
  sh start.sh
```

Stop the Application

```bash
  sh stop.sh
```


## Postman Collection

[Download](https://github.com/monika-meshram/pismo-banking/blob/main/src/main/resources/postman/PismoBankingAPIs.postman_collection.json)


## Documentation

[Swagger to try it out](http://localhost:8080/swagger-ui/index.html#/)


## API Reference :

## Create an account

```http
  POST /accounts
```
#### Request Body :

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `document_number` | `string` | **Required**. Document Number to create an account |

## Retrieve the account information

```http
  GET /accounts/{accountId}
```
#### Request Parameter :

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `account_id`      | `integer` | **Required**. Id of item to fetch |

## Create an transaction

```http
  POST /transactions
```
#### Request Body :

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `account_id` | `integer` | **Required**. Account ID for the transaction
`operation_type_id` | `integer` | **Required**. Operation Type ID of the operation to be carried out for this transaction |
`amount` | `BigDecimal` | **Required**. Amount for transaction |


## Optimizations

Add resilience4j


