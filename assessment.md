### COIN Developer Assessment

#### Assessment 

This assessment provides valuable input in order to help us decide whether a candidate has sufficient technical and problem solving skills to join the COIN DevOps team.


#### Assignment

The aim is to build a simple web application consisting of a single-page frontend App, a backend JSON API and a database which allows a user to search individuals and companies.
 
The query mechanism must be such that only a single input field must be provided (e.g. google) which then searches in all relevant fields (`companyName`, `firstName`, `lastName`, `address`, etc).

The results and amount of records found must be shown under the search input field.
 
When clicking on a found record, all information of the selected individual or company must be displayed.

Test data is provided in the enclosed zipped `address_records.json.zip` file. It contains person and company json records mixed together.

Company:
```
{
  "id": 14,
  "companyName": "Voonder",
  "address": "6211 Fieldstone Plaza",
  "phoneNumber":"4146506829"
}
```

Person:
```
{
  "id": 1,
  "firstName": "Magdalene",
  "lastName": "Whitehair",
  "address": "1160 Mesta Circle",
  "gender": "Female",
  "phoneNumber": "6002504059"
}
```

Please notify us via email when you start (see contacts below).


#### Assignment Constraints

The assignment has the following constraints:
 
- The frontend must be a single page app. The technology choice for the frontend is up to the candidate, e.g. Angular, React, Ember, Elm, JQuery etc.

- The frontend must communicate via REST (or websockets) with the backend application to search and retrieve the found information.

- The backend must be created with a JVM language preferably Java, Kotlin or Scala and using a popular framework like Spring, Play, etc. Extra points will be given for an asynchronous implementation using Futures or Streams, but this is not mandatory.

- The backend must be secured with proper up to date techniques. The choice on how to do this is up to the candidate.
 
- The frontend call must be validated by the backend using the security measures the candidate chose.

- The choice for a database is up to the candidate. Choose what fits best, e.g. MySQL, Postgres, MongoDB, ElasticSearch, etc.
 
- The database must be available as a docker container.

- The whole application must be buildable using a state of the art build tool such as maven, gradle, sbt, npm, yarn, etc.


#### Assessment Criteria

For the assessment we use the following criteria:

- We consider the backend to be the most important part of the application. Make sure it reflects good coding practices, such as mentioned here: 
[http://java-design-patterns.com/principles/](http://java-design-patterns.com/principles/).  

- The frontend is less important. We only expect it to be fully functional but it can be visually simple.

- We expect all code to be tested with the test framework of your choice. Should achieve at least 70% coverage and all tests must pass.


#### Deliverables

We expect the following deliverables:

- The application needs to be packaged in a docker container.

- A Readme needs to be provided that explains how to start the application (and database) using docker.

- The source code needs to be provided in a git repository (e.g. github or gitlab).


### Presentation

Once completed, you will be given the opportunity to present your solution in an informal setting and  should include the following:

- Explain briefly how you went about designing and building it.

- Justify the architecture and tooling decisions you made, pros and cons.

- Explain what you found easy, and what was difficult.

- Tell us which problems you confronted and how you overcame them.

- Demo your solution and run tests.

- Be prepared to answer questions.


### Important

Please note that it is not our intention to expect you to be able to implement a perfect solution.

In the real world, we must fight time constraints and tight schedules, and cannot enjoy the luxury of completing every single customer wish in time.

Choose carefully which tasks are the most important for the customer and ensure that those tasks that you are able to complete will provide the most business value.


### Contacts

While you are working on this assignments, please feel free to contact us with questions.

At COIN we value open communication and appreciate you telling us how you are doing along the way.

Here are the contacts:

- Urs Peter <upeter@xebia.com> for technical support

- Kiffin Gish <k.gish@coin.nl> tel 06 57 55 28 71 for all other support or when Urs is not available.

### Finally

Good luck!
