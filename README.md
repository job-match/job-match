# JobMatch 

![JobMatchLogo](JobMatch/src/main/resources/static/images/logo-jobMatch-for-Github)

## 💡 Description

JobMatch is an application that can match Professionals and Companies which are looking for each other.
The application has two main parts:
Employer Portal – here, the companies can create a company profile and job ads – to show what they do (business sector) and what they want to search for. 
Professionals Portal – Individuals create a professional profile and job applications - to show what they can do (qualifications) and what they want and search for.

## 🚀 Set and Start up project

1. 🔍 Check `application.properties` and set your personal database `url`, `username` and `password`. 
You can find it in `/src/main/resources/application.properties`;
2. 🔍 Check `build.gradle` and review database dependency.
You can find it in `JobMatch/build.gradle`;
3. 🔍 Check `HibernateConfig.java` and set your `Datasource Driver` and `Hibernate Properties`;
4. ⚙️ Set connection with database and use `create.sql` and `insert.sql` to create forum database and fill it with data.
You can find them in `JobMatch/db`.
5. 🔐 Basic Authentication is implemented. In Http Headers set Key: `Authorization` and Value: `username password` - check `insert_data.sql` for valid username and password.
6. ☁️ Storage of profile photos is implemented with [Cloudinary](https://cloudinary.com/) To set up cloudinary follow the steps:
   - Register in Cloudinary
   - After login check your personal API Keys: Cloud Name, API Secret, API Key.
   - Go to `/src/main/resources/application.properties` and set up your personal Cloud Name, and API Key;
   - To set up API Secret you need to create Environment Variable because it is hidden in `application.properties`.
   - Go to navigation bar in IntelliJ IDEA and follow the path: `Run -> Edit Configuration -> Select SpringBoot -> Select ForumManagementSystemAplication -> Modify Options -> Select Environment variables` and create environment variable with `Name: CLOUDINARY_API_SECRET` and `Value: Your personal API Secret`.
  
## 📊 Database relations
You can find it in `JobMatch/db`.

## 📜 Swagger
Start the project and go to [Swagger Docs](http://localhost:8080/swagger-ui/index.html)
