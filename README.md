## Coin Search Engine

### Starting the application

#### When using Docker (recommended)
The application is available as a Docker Image. For purpose of this assessment also the database (with the data) is available as a Docker Image. Simply use the Dockercompose file to start up the application.
```
docker swarm init
docker stack deploy -c docker-compose.yml [name]
```


#### When first building from source

Before using the application the first time, the application has to be built.

Required (version numbers are the number the application was built with):
* maven (3.5.2)
* java version "1.8.0_152"
* Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
* mongodb (3.6.3)

Build the application with `mvn clean package`.
Start the Mongo daemon and setup the database. 
```
mongoimport --db coin --collection assessment --jsonArray --drop --file ./address_records/address_records.json
```

Now run the application with `java -jar ./target/CoinSearchEngine-1.0.war`.

### Using the application
The application is available at port 8080 via any internet browser (https://localhost:8080). Note that the certificate will not be trusted, as this is a 'home made' certificate which should be used during development.

### Content of the assessment
See _assessment.md_ for the assessment
