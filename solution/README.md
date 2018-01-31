README
====
How to run the your solution...

IMPORTANT
====
To avoid unconcious bias, we aim to have your submission reviewed anonymously by one of our engineering team. Please try and avoid adding personal details to this document such as your name, or using pronouns that might indicate your gender.

# Implementation

## Requirements

 - Maven
 - PostgreSQL
 - Java

## Steps To Run Application

The solution is in solution folder. It is a maven module (Dropwizard framework). The easiest way to build and run application is to follow next steps:

 1. Change directory to engage module: `cd solution/engage`
 2. Run `/src/main/setup/db/postgresql/shell/create_database.sh` for Unix based systems (Linux, MacOS,...) and `/src/main/setup/db/postgresql/shell/create_database.bat` for Windows systems (change 3 line `path C:\Program Files\PostgreSQL\9.6\bin;.;` to your path). I tested only sh script on Ubuntu system. After database with user engage and password engage will be created (you will be probably be prompted to enter password for user engage, which is engage). There is also html documentation of database on `src/main/setup/db/design/engage.html`
 3. When we have database and data needed, we can build application fat jar with next command: `mvn clean install` 
 4. After that we can run our application with next command:`java -jar target/engage-0.1.0.jar server src/main/resources/engage.yml`, after which you can open [localhost:8080](http://localhost:8080) in your browser

## Technologies Used Some of the framework and libraries used:
 - Dropwizard
 - JDBI
 - PostgreSQL
 - Quartz Scheduler

## Notice

Each user can list only its own expenses. There is no authentication, but there is some form of authorization (created custom jersey injectable annotation which returns user. For now, it is hardcoded user, but model supports some session and authorization)

For task No 3, [fixer](http://fixer.io/) is used for exchange rate. There is notice on site that we should cache rate whenever possible. That is what we do. On application startup, we read rate and cache it in our system (custom cache) implemented. Then, when we need rate, we pull it out of the cache. There is also a job registered, which triggers on every 2 minutes (cron expression can be set in config file for trigger) and we read a new rate and put it in cache.

For task No 4, VAT is calculated on front. But, if we enter currency (EUR in this example), then the amount is calculated on backend (using exchange rate), and VAT is also recalculated on backend`
