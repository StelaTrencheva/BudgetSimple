# BudgetSimple - Spring Boot and React JS Web Application 
Semester 4 Individual Project
## 1. Project idea:
 BudgetSimple is a budget application meant to be a convenient way of tracking spending and that simplifies
people’s needs while collecting everything in one place. The application can be used individually and in a
group. Every person can make a registration and login with their credentials. In the application, people can
create wallets for different purposes. Every wallet can have a budget and in this way, a user can track how
he/she is spending money compared to the budget. In a wallet, people can add transactions holding
information of their purchases. Multiple people can use one wallet. This can happen, when a person
generate a code for the wallet and share it with others. Then using the code people can join the wallet and
share one budget, add transactions, etc. In a wallet with multiple people, the amounts of the transactions
can be divided through the members (equally or not) and in this way each member can see how much
he/she owes to the others and vice versa. In a wallet, people can see statistics about how much they have
spent per periods, categories, per person and in total. Also, depending on the previous months, people
can see estimations of how much they might spent in future months. Owner of a wallet can restrict other
members so in this way they will not be allowed to do some things like setting a budget. Admins of the
application can add bonuses that users can unlock when fulfilling different tasks and admins can track
which bonuses are unlocked the most so they can make reports and assumptions on which things are the
most used and enjoyed. Users are able to rate the application and share their ideas and opinion. In case
of a problem while using the application or a question, people can contact customer support users using
the live chat and they can get an answer there if a customer support employee is available.

## 2. Goal Project
 The goal of the project is to create an efficient way that will ease people who want to track their spending
while allowing them to access all needed features to track and analyze spending in one place, collecting
features from other applications and adding even more. The project is going to be in the form of a website,
which can be used on all devices, which makes it simple and comfortable.

## 3. Problem description
 The biggest challenge that the client is facing is having one application that collects their favorite budget
tools from multiple applications. They have issues with tracking their spending in an easy way, and
having different spending accounts with multiple people.

##
The folder of the project consists of three main parts:
- Backend which is in the root folder. There is also the docker compose file for the creation of the docker containers.
- Frontend which is in the folder "budgetsimple_front_end" and the main files are in "react-hooks-crud". There is also a Dockerfile for th e creation of the frontend docker image.
- All the documentation from sprint 1-6 is in folder "documentation". The main files are in the folder itself and other files like videos of the finished user stories are in the folders of the different sprints.

In order to start the project open cmd in root folder and execute these commands one by one: </br>
cd .\budgetsimple_front_end\react-hooks-crud\ </br>
docker build -t budgetsimplefrontend </br>
cd .. </br>
cd .. </br>
docker pull mysql:latest </br>
docker compose up </br>

*Keep in mind that the database is empty and you need to create a new user in order to see the other functionalities
