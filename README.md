fantasy_football_site

#### Java version 1.8

###start mysql
````
sudo /usr/local/mysql/support-files/mysql.server start
````
###Run spring boot application
````
mvn clean install spring-boot:run -Dspring.profiles.active=local -DdatabaseURL=jdbc:mysql://localhost:3306/football -DdatabaseUser= -DdatabasePass= -Dsecurity.jwt.token.secret-key=
````

## Register user: POST: /api/auth/signup

```json 
{
    "name": "Brett",
    "userName": "Brett",
    "password": "password123",
    "email": "brettmarcin@gmail.com",
    "roles" : [{
    	"roleName" : "STANDARD_USER",
    	"description" : "none"
    }]
}
```

## Login user: POST: /api/auth/signin

```json 
{
	"usernameOrEmail": "Brett",
	"password": "password123"
}
```

## Get User: GET: /api/getUser
* IMPORTANT the signin api will give you a token. If you are using postman
 go to the Authorization tab and under TYPE: select Bearer Token and enter 
 the token in the Token: field *