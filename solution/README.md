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
 2. Run `/src/main/setup/db/postgresql/shell/create_database.sh` for Unix bas systems (Linux, MacOS,...) and `/src/main/setup/db/postgresql/shell/create_database.bat` for Windows systems (change 3 line `path C:\Program Files\PostgreSQL\9.6\bin;.;` to your path). I tested only sh script on Ubuntu system. After database with user engage and password engage will be created (you will be probably be prompted to enter password for user engage, which is engage). There is also html documentation of database on `src/main/setup/db/design/engage.html`
 3. When we have database and data needed, we can build application fat jar with next command: `mvn clean install` 
 4. After that we can run our application with next command:``
