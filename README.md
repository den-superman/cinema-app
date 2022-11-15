# cinema-app
Hello dear potential users of this wonderful application =) 
I am glad to introduce you its functionality. This is a simulator of cinema service. Depending on your role, you can perform different actions. You can register as a usual User(email validation and repeat password is implemented). Don't worry about safety of your password, it is not directly saved to DB, we save only it's encrypted version. Then your shopping cart will be created automatically and you will be able to add some movie sessions there(you can find available ones based on movieId, which you want to see and the day, when you want to see it). When ready, you can complete your order and it will be saved to your history(like purchase), your shopping cart will become empty so. To create and manage all that movie sessions, movies, cinema halls your need to have admin role(you can't simply register as Admin, DataInitializer will create roles in your DB and will insert one admin-person, when you will run the app).
While creating the app I used 3-tier structure:

DAO - this tier is responsible only for insertion and extraction data from database.
Service - this tier is responsible for opereting this data, realization of our program’s logic.
Controller - this tier is responsible for communicating with user. It can handle the users’ inputs and show the needed information from them.
Also, the app is based on SOLID principles:

Each class have only one responsibility.
It is open for extension, but closed for modification.
Objects of a superclass are replaceable with objects of its subclasses.
Large interfaces are split into smaller ones, where it was reasonable.
High-level and low-level modules depends on abstractions.

If you want to use this app locally(IntelliJ IDEA Ultimate is needed) on your device you need to set up tomcat: Run your project in Intelegi IDEA -> edit configuration -> add new configuration -> tomcat -> local -> fix -> your project name_war_exploded. And I recomend you to put aplication context: "/" just for your comfort. I hope my app will be useful for you!