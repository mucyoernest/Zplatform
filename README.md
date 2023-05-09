****ZPlatform****
ZPlatform is a user management system that allows the creation, update, and deletion of user accounts. Additionally, it provides password reset functionality and user verification.

Features
- Create, update, and delete user accounts
- Password reset using link functionality
- User verification through provided documents
- Profile picture upload
- Age (user must be >13), email, and password validation. 
- (Hint: A valid password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, must not contain any whitespace characters, and must be between 8 and 20 characters in length".


*Prerequisites*
To run ZPlatform, ensure you have the following installed:
  Java 8 or higher
  Maven
  A running instance of your preferred database system (e.g., MySQL, PostgreSQL)
  
**Setup and Configuration**
Clone the repository: git clone https://github.com/mucyoernest/Zplatform
Navigate to the project folder: cd zplatform
Update the src/main/resources/application.properties file with your database credentials and connection information.

If necessary, configure any API keys or credentials for third-party services (e.g., email service and Cloudinary).

**Build and Run**

**IntelliJ
  Open IntelliJ and click on "Open or Import" on the welcome screen.
  Navigate to the project folder and click "OK" to open the project.
  Wait for IntelliJ to load the project and download the required dependencies.
  
**Eclipse**
  Open Eclipse and go to "File" > "Import".
  Select "Existing Maven Projects" under the "Maven" folder and click "Next".
  Browse to the project folder, select it, and click "Finish" to import the project.
  Wait for Eclipse to load the project and download the required dependencies.
  Build and Run
  To build and run the project using your IDE:

**IntelliJ**
  Right-click on the project root folder and select "Maven" > "Reload project".
  Build the project by right-clicking on the project root folder and selecting "Maven" > "Compile".
  Run the application by right-clicking on the ZPlatformApplication.java file (located in the org.zcompany.zplatform package) and selecting "Run 'ZPlatformApplication'".
  
**Eclipse**
  Right-click on the project root folder and select "Maven" > "Update Project".
  Build the project by right-clicking on the project root folder and selecting "Run As" > "Maven build...". Enter clean install in the "Goals" field and click "Run".
  Run the application by right-clicking on the ZPlatformApplication.java file (located in the org.zcompany.zplatform package) and selecting "Run As" > "Java Application".
  
 ****Testing****
You can easily test with Swagger at http://localhost:8080/swagger-ui/index.html

Base URL: http://localhost:8080

Endpoints
User Management
1. Create a new user

Method: POST
Endpoint: /api/
Request Body: JSON object of User
Response for success:
200 OK: User created successfully. Returns the created User object.

Example JSON object:
{
       {
  "dateOfBirth": "1993-05-09",
  "documentImageUrl": "string",
  "email": "Jon@we.fg",
  "firstName": "Doe",
  "gender": "FEMALE",
  "id": 0,
  "lastName": "John",
  "maritalStatus": "DIVORCED",
  "nationality": "RW",
  "nidOrPassport": "string",
  "password": "string@123F",
  "passwordResetToken": "string",
  "passwordResetTokenExpiry": "2023-05-09T18:48:22.106Z",
  "profilePictureUrl": "string"
}
    }

2. Update user
Method: PUT
Endpoint: /api/users/{id}
Path Parameters: id (integer)

3. Delete user

Method: DELETE
Endpoint: /api/users/{id}
Path Parameters: id (integer)

4. Request verification user

Method: POST
Endpoint: /api/users/{id}/verify
Path Parameters: id (integer)
Query Parameters: nidOrPassport (string)
Request Body: Multipart form data containing documentImage (binary)

5. Approve or deny user verification request

Method: POST
Endpoint: /api/users/verify/{id}
Path Parameters: id (integer)
Query Parameters: response (boolean)

6. Upload profile picture

Method: POST
Endpoint: /api/users/{id}/upload-profile
Path Parameters: id (integer)
Request Body: Multipart form data containing image (binary)

Password reset
1. Send password reset link

Method: POST
Endpoint: /reset/send-reset-link
Query Parameters: email (string)

2. Update password

Method: POST
Endpoint: /reset/update-password
Query Parameters:
password (string)
passwordConfirm (string)
token (string)

Contributing
To contribute to the project, please submit issues and create pull requests following the guidelines provided in the CONTRIBUTING.md file.
