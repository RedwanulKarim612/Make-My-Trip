# Make-My-Trip
Make My Trip is a platform where a company can add trips between two cities and a customer can buy tickets for those trips. It has three types of users:
<ul>
  <li>Customer </li>
  <li>Company </li>
  <li>Admin </li>
</ul>

## Used Technologies
<ul>
  <li> Spring Boot
  <li> Spring Security
  <li> Oracle 19c
  <li> Thymeleaf
  <li> itextpdf
  
</ul>


## Features and Functionalities:
<ul>
  <li>
    <b>Customer:</b>
    <ul>
      <li> Login or register as new customer </li>
      <li> Add money to wallet by verifying transactions </li>
      <li> Search for trips between two cities </li>
      <li> Book tickets and download tickets as PDF </li>
      <li> View previously booked tickets</li>
      <li> Edit personal information</li>
    </ul> 
  </li>
  <li>
    <b>Company:</b>
    <ul>
      <li>Login using credentials</li>
      <li>Add new vehicles</li>
      <li>Add trips using vehicles owned by the company</li>
      <li>View details of models, locations, cities and countries</li>
      <li>Search above mentioned items</li>
      <li>Change credentials</li>
    </ul>  
  </li>
  <li>
    <b>Admin:</b>
    <ul>
      <li>Login using credentials</li>
      <li>Add credentials for new companies</li>  
      <li>Add transaction ids(later verified by customers)</li>  
      <li>Add information of basic entities like city, country, location, model, customers etc </li>
      <li>Edit information of the above mentioned items</li>
    </ul>
  </li>
</ul>

## Instructions:

## How to set up:
  <ul>
    <li>Clone repository or download zip file. Run on terminal:
      
```
git clone https://github.com/RedwanulKarim612/Make-My-Trip.git
``` 
  
  <li> Download Oracle 19c. Create a user c##xyz with password xyz. Run in SQL-PLUS
    
```
create user c##xyz identified by xyz;
grant dba to c##xyz;
```
    
   <li> To create tables and populate them run commands from <a href="Dump Files/MMT_DUMP">MMT_DUMP</a>.  
     
    
</ul>


## How to use:
<ul>
  <li>  Navigate to the root directory. Run the server from command prompt: 
    
```
java -jar target/MakeMyTrip-0.0.1-SNAPSHOT.jar
```
  <li> Connect to localhost:8080 and login using valid credentials
</ul>
