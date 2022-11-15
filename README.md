# cinema-app
Hello dear potential users of this wonderful Spring-based application =) 

I am glad to introduce you its functionality. This is a simulator of cinema service. Depending on your role, you can perform different actions. You can register as a usual User(email validation and repeat password is implemented). Don't worry about safety of your password, it is not directly saved to DB, we save only it's encrypted version. Then your shopping cart will be created automatically and you will be able to add some movie sessions there(you can find available ones based on movieId, which you want to see and the day, when you want to see it). When ready, you can complete your order and it will be saved to your history(like purchase), your shopping cart will become empty so. To create and manage all that movie sessions, movies, cinema halls your need to have admin role(you can't simply register as Admin, DataInitializer will create roles in your DB and will insert one admin-person, when you will run the app).

While creating the app I used 3-tier structure:
DAO - this tier is responsible only for insertion and extraction data from database.
Service - this tier is responsible for opereting this data, realization of our program’s logic.
Controller - this tier is responsible for communicating with user. It can handle the users’ inputs and show the needed information from them.

The app is based on SOLID principles:
1. Each class have only one responsibility.
2. It is open for extension, but closed for modification.
3. Objects of a superclass are replaceable with objects of its subclasses.
4. Large interfaces are split into smaller ones, where it was reasonable.
5. High-level and low-level modules depends on abstractions.
Different modern Spring modules were used, such as Spring Core, Spring Web MVC, Spring ORM, Spring context, Spring Security.

If you want to use this app locally(IntelliJ IDEA Ultimate is needed) on your device you need to set up tomcat: Run your project in Intelegi IDEA -> edit configuration -> add new configuration -> tomcat -> local -> fix -> your project name_war_exploded. And I recomend you to put aplication context: "/" just for your comfort. 
If you are working directly in your browser you can only send HHTP.Get requests, so if you want to check Post, Put, Delete requests I can recomed you to use https://web.postman.co to feel the full functionality of this app. Enjoy =)  
