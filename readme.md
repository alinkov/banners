## About
This is a simple rest project for work with banners

In this application used next libraries:
 + SpringBoot
 + SpringData
 + SpringMVC
 + H2
 + Maven
 + Lombok
 + Junit 
 ## 
 Rest services
 
  * GET [http://localhost:8080/banners?k=number](http://localhost:8080/banners?k=number) get number of banners
  * POST [http://localhost:8080/banners](http://localhost:8080/banners) save banner. Input parameter weight
  * DELETE [http://localhost:8080/banners/{id}](http://localhost:8080/banners/{id}) delete banner by id