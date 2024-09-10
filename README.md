# ABC Restaurant Web Application

This repository contains the Maven-based **ABC Restaurant Web Application**, which handles user authentication, order management, reservations, menu management, and more for restaurant operations.

## Technologies Used

- **Eclipse IDE**: Integrated development environment for Java development.
- **Maven**: Project management and dependency management tool.
- **Tomcat 9**: Servlet container to run the web application.
- **MySQL**: Database for storing customer, orders, and reservation data.
- **Bootstrap 5**: Frontend framework for building responsive UIs.
- **Font Awesome**: Custom fonts and icons.
- **JUnit 5**: Unit testing framework.
- **Mockito 5**: Mocking framework for testing.
- **SHA-256 Hashing**: For securely hashing passwords.
- **JavaMail API**: To send email notifications to customers regarding queries.
  
## Installation and Setup

To set up the project locally using **Eclipse IDE**, follow these steps:

1. **Clone the repository**:
   - Open Eclipse IDE.
   - Go to **File > Import > Git > Projects from Git > Clone URI**.
   - Enter the repository URL: `https://github.com/Nuked404/ABC-Restaurant.git`.
   - Click **Next** and complete the clone process.

2. **Import the Maven Project**:
   - After cloning, go to **File > Import > Maven > Existing Maven Projects**.
   - Browse to the location where the project was cloned, select the root folder, and click **Finish**.

3. **Set up MySQL Database**:
   - Create a MySQL database, e.g., ``abc_restaurant``.
   - Open the file `src\main\java\com\abc\util\DBConnection.java` in Eclipse.
   - Update the following properties with your MySQL credentials:
     ```properties
     private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/abc_restaurant";
     private static final String DB_USER = "root";
     private static final String DB_PASSWORD = "12345";
     ```

4. **Configure Tomcat in Eclipse**:
   - Go to **Window > Show View > Servers**.
   - Right-click in the **Servers** tab and select **New > Server**.
   - Choose **Apache Tomcat v9.0** and point to your Tomcat installation directory.
   - Add the project to the server and click **Finish**.

5. **Run the Application**:
   - Right-click on the project, select **Run As > Run on Server**.
   - The application should now be running at `http://localhost:8080`.

## Running Unit Tests

To run the unit tests using **JUnit 5** and **Mockito** in Eclipse:

1. **Right-click on the project folder** or individual test class (e.g., `LoginControllerTest`, `CartTest`).
2. Select **Run As > JUnit Test**.
3. Eclipse will display the test results in the **JUnit** view.

You can also run all tests via Maven:
```bash
mvn test
```


## Version Control and GitHub Integration

This project utilizes **Git** for version control and **GitHub** for repository management. Here's how version control practices are applied:

- **Multiple commits**: Frequent commits with detailed messages documenting the addition of features, bug fixes, and improvements.
- **Tags**: The project uses tags to mark key milestones, such as `v1.0` for the initial release and subsequent versions.
- **Releases**: The initial release was done after the project was finalized for deployment, and further releases will continue as new features are added.
- **Dependabot Alerts**: GitHub's Dependabot was integrated to detect security vulnerabilities. Various issues related to dependencies have been identified and resolved through this.

## Folder Structure

- **src/main/java/com/abc**: Contains the Java source code
  - `controller`: Servlets for customers.
  - `controller/admin`: Servlets for admins.
  - `service`: Business logic layer.
  - `dao`: Database interaction layer.
  - `enums`: Enums for application.
  - `filter`: Access control filter.
  - `model`: Entity classes representing database tables.
  - `util`: Database connection manager.
- **src/main/webapp/WEB-INF/view**: Contains views related to the project.
- **src/main/webapp/images**: Contains images related to the website.
- **src/main/webapp/includes**: Contains headers, footers, navbars etc.
- **src/main/webapp/**: Contains public .jsp pages.
- **src/test/java**: Contains unit tests written with **JUnit** and **Mockito**.
  
## Running Tests

To run tests:
- In **Eclipse**, right-click on the project or individual test class and select **Run As > JUnit Test**.
- Or use Maven from the terminal:
  ```bash
  mvn test
  ```

Test classes include:
- `LoginControllerTest`
- `CartTest`
- `ReservationServiceTest`
- `OrderDAOTest`

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---
