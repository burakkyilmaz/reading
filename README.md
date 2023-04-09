# reading

The postman folder contains the collection and can be accessed via swagger.

1- Customer can be created via /customers<br>
2- Tokens can be purchased via /authenticate. (BeaberToken is defined as globalVeriable via Postman. It will be sufficient to log in to access the services.)<br>
OR<br>
1,2- Admin and customer user information is given below.<br>
3- Method Level Security is used. Admin and customer user information is given below.<br>
4- MongoDB and redis are also revived in terms of competency demonstration<br>



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
admin ---
{
    "username": "admin",
    "password": "Admin123+"
}<br>
customer ---
{  
   "username": "burak78",
  "password": "Burak78+",
}
<br>
Swagger:
----------------
http://localhost:8080/swagger-ui/index.html

Spring Boot: 
