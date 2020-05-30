# TestRailNativeApiProject
Just a sample investigation around Test Rail API usage with Testng project

#How to run (Result generation during the run)
1. Clone git project to you local machine
2. Import maven dependencies
3. Change property value for Resources ->testrail.properties file:

 base_url=<testrail url>
 user_name=<testrail user>
 password=<testrail password> or <testrail api key>
 projectId=<id of the testrail project>
 
 4. Run testng with help of maven "mvn clean test" or run testng file directly. 
 
 Note: Some of the tests are failing. That's ok. It was done in order to test different statuses. 
 
 #How to run (Result generation from testng-results.xml)
 
 1. You can run Main class directly or run from command line by passing testng-reports.xml directory
 
 #How it works
 
 - Custom testng listener class was created "TestRailListener" 
 This class contains events that call testrail api methods.
 
 #Known limitations 
 
 - Testng.xml should have only one enabled xml test. In other case each test xml
 will override cases names of the run. The problem is "onFinish" ITestListener event
 is triggered for each of the test xml.
 
 #Implemented use case
 
 1. Test listener gets test classes and creates Sections in TestRail if it does not exist.
 2. Test listener gets test methods for each class and creates cases in TestRail if it does not exist.
 3. Test listener creates run with cases
 4. Test listener gets tests from the run
 5. Test listener sets status to each test in test rail.
 
 ![test run list](https://github.com/dneprokos/TestRailNativeApiProject/blob/master/images/RunList.jpg?raw=true)
 
 ![test run details](https://github.com/dneprokos/TestRailNativeApiProject/blob/master/images/testRunDetails.jpg?raw=true)
   

