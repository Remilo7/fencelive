# FenceLive
Spring MVC webapp helping in organizing fencing competitions.

## Features
 - Bootstrap based front-end
 - Data stored in MySQL database
 - Fencers database
   - Adding fencers manually one by one
   - Import fencers from .xlsx file
 - Classification lists
   - Import lists from .xlsx files
 - Organizing a tournament
   - Creating new tournaments
   - Managing tournaments
   - Displaying fencers allowed to compete
     - Confirming fencers presence
   - Group fights stage
     - Generating groups 
     - Saving group fights results
     - Generating classification list after group stage
   - Elimination fights stage
     - Generating elimination fights tableaues 
     - Saving tableau fights results
     - Generating final classification list
 
      
## Running the application locally

You need to have installed Java and Maven. 
Enter the directory where you downloaded the code and run:
```
mvn package
```
And then 
```
java -jar target/dependency/webapp-runner.jar target/*.war
```
After few seconds, the web application is available on http://localhost:8080/

You can also check it on https://fencelive.herokuapp.com/, however, it may not work properly due to the free database plan being used. 

