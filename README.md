# Bankin test

Test project for bankin service

There are two developed services :

- Retrieves all translation with round from bankin service
- Retrieves all translation with round for a period from bankin service

## How to call services

Just do a post request in json with specific argument according to the calling service.

For the first service :

- url: /API/Round/All
- json:
```
{
  "client_id": "string",
  "client_secret": "string",
  "email": "string email",
  "password": "string"
}
```

For the second service
- url: /API/Round/Period
- json:
```
{
  "client_id": "string",
  "client_secret": "string",
  "email": "string email",
  "password": "string",
  "start": "date string yyyy-MM-dd",
  "end": "date string yyyy-MM-dd"
}
```

Otherwise to test service, you can call the test with :
```
sbt test
```

You can go to next round with specify the "next" attribut in your json object.

Next attribut value is givent by the request if they are more records.
Use "nextTransactions" and "previousTransaction".
