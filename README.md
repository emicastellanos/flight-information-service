# flight-information-service

A microservice that has the responsibility of retrieving all flight information through an external service and storing it in a Redis database, so that it
has a cache system to manage all flight information and use it in other microservices.
Note that the external service should only have one endpoint that retrieves all flight information based on the given tail number. The tail number is the identification of an aircraft and an aircraft can perform several
flights in one day. It is the reason why the external service will return a list of all the flight information giving the tail number.

For this is necessary to have an endpoint, for example: /v1/flight-information/EC-MYT/653
(/v1/flight-information/{tail-number}/{flight-number}) that retrieves the corresponding flight information as a JSON

## Installation

For using this application you need docker, docker-compose and maven. 

#### Docker Compose

```sh
# We’ll check the current release and if necessary, update it in the command below:
$ sudo curl -L https://github.com/docker/compose/releases/download/1.27.4/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose

# Next we’ll set the permissions
$ sudo chmod +x /usr/local/bin/docker-compose

#Then we’ll verify that the installation was successful by checking the version:
$ docker-compose --version
```

This will print out the version we installed:

```sh
docker-compose version 1.27.0, build 980ec85b
```

## Usage

```sh
# Compile and package the spring boot application
$ mvn clean install

# Build the docker-compose
$ docker-compose build

# run it
$ docker-compose up
```

This application comes with swagger facilities so you can access 
http://localhost:8080/swagger-ui.html 
and query from there or simple try with
http://localhost:8080/v1/flight-information/EC-MYT/653
http://localhost:8080/swagger-ui.html 
and query from there or simple try with 




