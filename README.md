# reading

The postman folder contains the collection and can be accessed via swagger.

1- Customer can be created via /customers
2- Tokens can be purchased via /authenticate. (BeaberToken is defined as globalVeriable via Postman. It will be sufficient to log in to access the services.)
OR
1,2- Admin and customer user information is given below.
3- Method Level Security is used. Admin and customer user information is given below.
4- MongoDB and redis are also revived in terms of competency demonstration



H2 Database:
-----------------
http://localhost:8080/h2-console

MongoDB: 
-----------------
uri mongodb://localhost:27017
db:reading

Redis:
----------------
hosT:localhost
port:6379
prefix:reading

JWT (JSON Web Tokens): 
----------------
admin:
{
    "username": "admin",
    "password": "Admin123+"
}
customer:
{
    
    "username": "burak78",
  "password": "Burak78+",
}
/
Swagger:
----------------
http://localhost:8080/swagger-ui/index.html

Spring Boot: 
