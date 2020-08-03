# Spring Boot Micro Services with Gradle

RESTful web services are the first step to developing great microservices. Spring Boot,
in combination with Spring Web MVC (also called Spring REST) makes it easy to develop RESTful web services.  
Here we will learn the basics of Microservices from scratch and will understand how to implement microservices using Spring Cloud.

### APIs used to get country details
> https://restcountries.eu/rest/v2/name/India - Get country details by name  
> https://restcountries.eu/rest/v2/name/japan - Get country details by name  
> https://restcountries.eu/rest/v2/currency/INR -  Get country details by currency

## You will learn
- You will be able to develop and design RESTful web services
- You will setup Centralized Microservice Configuration with **Spring Cloud Config Server**
- You will learn to register **Eureka Client** applications with **Eureka Server**
- You will learn how to implement Circuit breaker for fault tolerance using **Hystrix** 
- Consume Third party API using **RestTemplate** and **WebClient**
- **Load Balance** using **Ribbon** for RestTemplate and WebClient
- You will learn declarative web service HTTP client with the help of **Feign**
- You will be able to consume and communicate other RESTful web services using **Ribbon** and **Feign**
- You will understand how to monitor RESTful Services with **Spring Boot Actuator**
- You will understand the best practices in designing RESTful web services


## Prerequisites 
- Java (Java 8)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Gradle](https://docs.gradle.org/current/userguide/userguide.html)

## Tools
- Eclipse or IntelliJ IDEA (or any preferred IDE) with embedded Gradle
- Gradle (version >= 6.0)
- Postman (or any RESTful API testing tool)


## Modules / Sub-modules
| Name                                        | Port                 | Description  |
| ------------------------------------------- |:-------------       | ---------:|
| spring-boot-microservices-gradle(root)      | (Root/Parent module) |    |
| common-country-client                       | 8300, 8301,.. **(or dynamic random port)**  |    |
| common-model                                |                      |    |
| country-config-server                       | 8888 (**optional**)  |    |
| country-currency-webclient-client           | 8200, 8201,.. **(or dynamic random port)**  |    |
| country-details-restful-client              | 8100, 8101,.. **(or dynamic random port)**  |    |
| eureka-naming-server                        | 8761                 |    |
| ribbon-feign-client                         | 8000                 |    |
| zuul-api-country-details                    | 8090                 |    |
> You can dynamically run multiple instances for ``restful-client`` and ``webclient-client`` applications by changing ports as shown above  
> Each instance will get registered under ``Eureka Server`` and urls will be mapped respectively.


## Build with Gradle
_GOTO >_ **~/absolute-path-to-directory/spring-boot-microservices-gradle/**  
and try below command in terminal
> **```gradle clean build```** it will build all sub-modules from parent module  

_If in case face any issue while building modules because of test cases, then try build with disabling test cases_
> **```gradle clean build -x test```**

## Order to Run the Applications
1. **eureka-naming-server**
2. **country-config-server** (**optional**)
3. **country-currency-webclient-client**
4. **country-details-restful-client**
5. **common-country-client**
6. **ribbon-feign-client**
7. **zuul-api-country-details**
> Under each module you will find ``*Aplication.java`` class, run ``main()`` method from that class


## API Endpoints
| Name                                        | URL                                               | 
| ------------------------------------------- |:------------------------------------------------  | 
| eureka-naming-server                        | http://localhost:8761                             | 
| common-country-client                       | http://localhost:8300/india or **{random.port}**  | 
| country-config-server                       | http://localhost:8888                             | 
| country-currency-webclient-client           | http://localhost:8200 or **{random.port}**, http://localhost:8201 or **{random.port}**,...| 
| country-details-restful-client              | http://localhost:8100 or **{random.port}**, http://localhost:8101 or **{random.port}**,...| 
| ribbon-feign-client                         | http://localhost:8000                             | 
| zuul-api-country-details                    | http://localhost:8090/common-country-client/india | 
