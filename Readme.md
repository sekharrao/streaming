# Streaming Application

## Instructions to run
Compile the application using the following maven command

```
 mvn clean install
```

Once the `jar` is created in target folder. Please execute it using 

```
mvn spring-boot:run
```


## API Testing

Use `postman` or any of the api tool to send the request to the listed `api endpoints`. 

1. `POST` on  `\registration` endpoint to create the account.
2. `Get` on `\registration` endpoint to view all registered accounts.
3. `POST` on `\payments` to pay for subscription.

## Sample request messages
Here is the sample json that can be used for 

### Account creation

```
{
	"username" : "abc",
	"password" : "Abc1234567",
	"email":"abc@test.com",
	"dob":"2000-10-24",
	"cardNumber":"5105105105105100"
}

```

### Payments

```
{
	"cardNumber": "5105105105105100",
	"amount": "101"
}

```

--
**NB:** *Refer API docs for usage and validation rules of above endpoints.*