
# Pismo Banking Application

Application to create an account and perform transactions to Purchase, Withdraw, Credit and Debit amount.

## Run Application

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

### Create an account

```http
  POST /accounts
```
#### Request Body :

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `document_number` | `string` | **Required**. Document Number to create an account |

### Retrieve the account information

```http
  GET /accounts/{accountId}
```
#### Request Parameter :

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `account_id`      | `integer` | **Required**. Id of item to fetch |

### Create a transaction

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

Add resilience4j for applying rate limits on APIs
And more!

## Assumptions

1. Used MySql as DB.
2. Added an extra column Balance in Accounts table. (Had completed this before the confirmation. So, keeping it. :) )
3. Rejected withdrawal transactions in case the account balance is already 0 or goes below 0 for this transaction.
4. Added few exceptions -  AccountNotFound, OperationNotFound, InsufficientBalance, InappropriateAmount.
5. Transaction amount should be less than 10000

