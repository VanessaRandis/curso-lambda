# Welcome to my new Project. 
It is about how to use AWS as "Serveless"!
I have been studying about this powerfull tools and I will learn much more during time.
As a teacher I see greats opportunities to improve my skills to be a better developer. 

Follow some information about how to do the configuration in this project. Enjoy it!


# Dependencies 
- JAVA 17
- Maven
- Lombok
- AWS LAMBDA
- JACKSON
- S3

- It is necessary to do the login at aws.amazon.com, after go to Lambda and do the configuration.


# Steps to Run at AWS/LAMBDA 
- First: Create Function
- Second: Insert the Function Name
- Third: Select the language of the project, select Enable Function URL
- Forth: Select the create function
- Fifth: Change the Handler to the same that is on project folder.
- Sixth: Insert into the "UPLOAD FROM" the package that you have created at Maven package
    
# Steps to do the configuration S3
- First: Create a Bucket at AWS S3 and do the configuration.
- Second: Add the S3 dependency at the project
- Third: I have done the validation into the class responsible to save the Json
at your project, so have to check if the try/catch is working as expected.





