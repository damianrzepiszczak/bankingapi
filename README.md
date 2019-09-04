# Banking API

Basic banking REST API.

**Table of Contents**
* [Assumptions](#Assumptions)
* [Endpoints](#Endpoints)
***

## Assumptions
* One Client can have only one account
* Account is created automatically when user is registered
* Authorization is based on JWT, should be passed in Authorization header on request where is that required
* API is using h2 in-memory storage
* Application has three layers, controller, services and repositories
* Currency is Polish złoty

## Endpoints

You can run app with command: mvn spring-boot:run. API runs on localhost with 8080 port. 

Possible operations:
* POST /client/register - registers new client and automatically creates account

   Body content in JSON format:
   {
   	"firstName": "Damian",
   	"lastName": "Rzepiszczak",
   	"login":"damian",
   	"password":"example",
   	"confrimPassword":"example",
   	"dayOfBirth":"2019-09-03",
   	"acceptedPersonalData": true
   }
   
   all fields are required !
   
   Response in JSON format:
   {
       "clientId": 2,
       "login": "damian",
       "jwtToken": "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6Mn0.bzQYjXicKzAJN-PKiTRJwLBSrX8Lm9kLFv12JZoroYvGBL9h7ZAm-4jvv5iBmzcNfWQtIs9owtDXo9ll7OoDjg",
       "accountNumber": "4b2b602b-2814-4fbd-b15e-7d8e19940bc3",
       "accountId": 2
   }

* POST /client/login - login client

    Content-Type: application/x-www-form-urlencoded: <br>
    login:example <br>
    password:example
    
    Response in JSON format:
    {
        "clientId": 1,
        "login": "example",
        "jwtToken": "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MX0.Slgu_OO6omSNPaEuo5iOztCQ9P6jJt96IcfgcQ5vmDwJ6FmxbF7Rc9JswMKEdm_7Gw36UfmKWpNo2_P73ic-iw",
        "accountNumber": "f44cbf16-d542-48a3-920d-710a03566a1d",
        "accountId": 1
    }
    
* POST /account/{id}/deposit

    Authorization header with JWT token
    
    Content-Type: application/x-www-form-urlencoded: <br>
    amount: 20    
    
    Response in JSON format:
    {
        "id": 1,
        "number": "8d6ca31e-b874-4f6f-852e-ff034cfe4fa1",
        "balance": 20.0
    }
    
* POST /account/{id}/withdraw

    Authorization header with JWT token
    
    Content-Type: application/x-www-form-urlencoded: <br>
    amount: 10    
    
    Response in JSON format:
    {
        "id": 1,
        "number": "8d6ca31e-b874-4f6f-852e-ff034cfe4fa1",
        "balance": 10.0
    }
    
* POST /account/{id}/transfer

    Authorization header with JWT token
    
    Content-Type: application/x-www-form-urlencoded: <br>
    accountToId: 2 <br>
    amount: 20    
    
    Response in JSON format:
    {
        "id": 1,
        "number": "8d6ca31e-b874-4f6f-852e-ff034cfe4fa1",
        "balance": 10.0
    }            