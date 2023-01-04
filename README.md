# restaurant-app
This is restaurant web application with basic functional such as:
1. Reserve table.
2. See all products or by category.
3. Add product to shopping cart and edit that.
4. Make order.
## Endpoints
### For non-logged in
`POST` /login `JSON` {email, password}  
`POST` /register `JSON` {email, password, repeatPassword}
### USER
#### /categories
`GET` /   
#### /orders
`POST` /add `JSON` {productId, tableId, amount}  
`GET` /by-user  
#### /products
`GET` /  
`GET` /by-category/{id}  
#### /shopping-carts
`GET` /by-user  
`PUT` /shopping-cart-items/add/{id}  
`PUT` /clear  
`DELETE` /delete-item/{id}  
#### /shopping-cart-items
`PUT` /increase/{id}  
`PUT` /degrease/{id}  
`PUT` /set-amount/{id}?amount  
#### /tables
`GET` /  
`PUT` /reserve/{id}  
### ADMIN
#### /categories
`POST` /add `JSON` {name}  
`PUT` /update/{id} `JSON` {name}  
#### /orders  
`GET` /by-table/{id}  
`PUT` /done/{id}  
#### /products
`POST` /add `JSON` {name, price, weight, categoryId}  
`PUT` /update/{id}  
`DELETE` /delete/{id}  
#### /tables
`POST` /add `JSON`  
`PUT` /update  
#### /tables
`POST` /add `JSON` {name}  
`PUT` /update/{id} `JSON` {name}  
## Project structure
### Levels
`DAO` Data access layer - access to the database (Hibernate API)  
`Service` Application layer - main logic of the application  
`Controller` Presentation layer - communication with a user (Spring API)  
### Technologies
JAVA `jdk-11`  
HIBERNATE `5.4.27.Final`  
SPRING MVC/SECURITY `5.2.2.RELEASE`    
MAVEN `3.8.6`  
POSTGRESQL `15`  
TOMCAT `9.0.70`  
MOCKITO `4.8.0`  
POSTMAN  
