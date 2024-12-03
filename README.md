# JobMatch 

![JobMatchLogo](JobMatch/src/main/resources/static/images/logo-jobMatch-for-Github.png)

## 💡 Description

JobMatch is an application that can match Professionals and Companies which are looking for each other.
The application has two main parts:
1. Employer Portal – here, the companies can create a company profile and job ads – to show what they do (business sector) and what they want to search for. 
2. Professionals Portal – Individuals create a professional profile and job applications - to show what they can do (qualifications) and what they want and search for.

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

## 💻 Technologies
* ☕️ Java
* 🌱 Spring Boot 
* 🗄️ Hibernate
* 💾 MariaDB
* 🌐 REST API
* ☁️ Cloudinary
* 📧 Mailjet
* 📜 Swagger
* 📦 Gradle
* 🍃 Thymeleaf
  
## 🔧 Functionality

### 🏢💼🌐 Company Portal `/api/company-portal`

#### GET
| Resource           | Endpoint                                              | Description                                         |
|--------------------|------------------------------------------------------|-----------------------------------------------------|
| Companies          | `/companies`                                         | Get all Companies                                   |
| Companies          | `/companies/{id}`                                    | Get Company by ID                                   |
| Job Ads            | `/job-ads`                                           | Get all Job Ads                                     |
| Job Ads            | `/job-ads/{id}`                                      | Get Job Ad by ID                                    |
| Job Applications   | `/job-applications`                                  | Get all Job Applications                            |
| Job Applications   | `/job-applications/search`                          | Search Job Applications by location, salary, skill, keyword |
| Job Applications   | `/job-applications/{id}`                            | Get Job Application by ID                           |
| Job Applications   | `/job-applications/successful-matches`              | Get Successful Matched Job Applications            |
| Professionals      | `/professionals`                                     | Get all Professionals                               |
| Professionals      | `/professionals/search`                             | Search Professionals by username, name, email, keyword, location |
| Professionals      | `/professionals/{id}`                               | Get Professional by ID                              |



#### POST
| Resource           | Endpoint                                              | Description                                         |
|--------------------|------------------------------------------------------|-----------------------------------------------------|
| Companies          | `/companies`                                         | Register Company                                    |
| Companies          | `/companies/{id}/picture`                            | Update Picture of Company                          |
| Job Ads            | `/job-ads`                                           | Create Job Ad                                       |
| Job Ads            | `/job-ads/{jobAdId}/match-requests/{jobAppId}`       | Confirm Match with Job Application                 |
| Job Applications   | `/job-applications/{jobApplicationId}/match-request-by/{jobAdId}` | Create Job Ad Request Match with Job Application  |



#### PUT
| Resource           | Endpoint                                              | Description                                         |
|--------------------|------------------------------------------------------|-----------------------------------------------------|
| Companies          | `/companies/{id}`                                    | Update Company                                      |
| Job Ads            | `/job-ads/{id}`                                      | Update Job Ad                                       |



#### DELETE
| Resource           | Endpoint                                              | Description                                         |
|--------------------|------------------------------------------------------|-----------------------------------------------------|
| Companies          | `/companies/{id}`                                    | Delete Company                                      |
| Job Ads            | `/job-ads/{id}`                                      | Delete Job Ad                                       |

### 👨‍💼💻👩‍💼 Professional  `/api/professional-portal`

* `GET /professionals`  - Get all Professionals
* `GET /professionals/{id}`  - Get Professional by id
* `POST /professionals`  - Register Professional 
* `POST /professionals/{id}/picture`  - Update Picture of Professional
* `PUT /professionals/{id}`  - Update Professional
* `DELETE /professionals/{id}`  - Delete Professional


* `GET /job-applications`  - Get all Job Applications
* `GET /job-applications/{id}`  - Get Job Application by id
* `POST /job-applications`  - Create Job Application 
* `POST /job-applications/{jobAppId}/match-requests/{jobAdId}`  - Confirm Match with Job Ad
* `PUT /job-applications/{id}`  - Update Job Appication
* `DELETE /job-applications/{id}`  - Delete Job Application


* `GET /job-ads`  - Get all Job Ads
* `GET /job-ads/search`  - Search Job Ads by position title, salary, requirement, location
* `GET /job-ads/{id}`  - Get Job Ad by id
* `GET /job-ads/successful-matches`  - Get Successful Matched Job Ads
* `POST /job-ads/{jobAdId}/match-request-by/{jobAppId}`  - Create Job Application Request Match With Job Ad


* `GET /companies`  - Get all Companies
* `GET /companies/search`  - Search Companies by username, name, email, keyword, location
* `GET /companies/{id}`  - Get Company by id


