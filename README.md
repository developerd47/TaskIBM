# TaskIBM


#How to run this project

There are two branch
 1. Front-end - consist frontend code
 2. Backend - consist backend code
 
 #Backend
 
 To Run on Docker
 1) mvn clean package (generate jar file)
 2) docker build -t myapp .
 3) docker run -p 8080:8080 myapp
 
 
 #Frontend
 
 To Run on Docker
 1) Pull Frontend
 2) docker build -t myfront .
 3) docker run -p 90:80 myfront
