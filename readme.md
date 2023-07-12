### Weather Shopper Black-box Tests

Weather shopper system tests consist of functional tests of [Weather Shopper Website](https://weathershopper.pythonanywhere.com/)

### Stack of technologies
1. [Java 11](https://www.oracle.com/java/)
2. [Maven](https://maven.apache.org/) - command line test executor
3. [Junit5](https://testng.org/) - test executor
4. [Allure](http://allure.qatools.ru/) - reports generator
5. [Selenium Web Driver](https://www.selenium.dev/) - User Interface automation
6. [Rest Assured](http://rest-assured.io/) - library for testing RESTful APIs
7. [AssertJ](https://assertj.github.io/doc/) - java library for assertions
8. [Docker](https://www.docker.com/products/docker-desktop/) - Docker for containerization

### Required installation
1. [Java 11 or higher](https://openjdk.java.net/install/index.html)
2. [Allure commandline for generation test report](https://docs.qameta.io/allure/#_installing_a_commandline)
3. [Maven for running tests from command line](https://maven.apache.org/install.html)

### IntelliJ IDEA settings
Project Structure settings:
1. Open File --> New --> Project from Version control --> Git
2. Clone project from the repository by URL "https://github.com/bpriyatham1/weather-shopper-tests.git"
3. Open File --> Project Structure --> Project
4. Select project SDK java 11 or higher
5. Select project language level 11 (local variable syntax for lambda parameters)
6. Open File --> Project Structure --> Modules
7. Select language level 11

### Test Execution

### Run Docker command before starting test execution
To start the docker compose file run below command:
```
docker-compose up
```
command to verify docker images
```
docker ps
```
#### Run test from IDE (IntelliJ)
Click on play button nearby the desired test from intellij
Test will be running with default settings.

#### Run tests from command line
```
mvn clean test
```

### Run smoke tests from command line with default settings
```
mvn clean -DtestTags=healthcheck test --fail-at-end
```
### Run regression tests from command line with default settings
```
mvn clean -DtestTags=regression test --fail-at-end
```
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml

### Run specific test
```
mvn clean -Dtest=RegressionTest#performWeatherShopperTaskTest test
```

### Run Docker command after completing the test execution
To stop the docker compose file run below command  after test execution:
```
docker-compose down
```


### Selenium grid url
```
https://localhost:4444/
```

### Allure test report generation
This is already enough to see the Allure report in one command:
```
allure serve /home/path/to/project/target/allure-results
```
If report generated successfully, report page should be opened automatically in Web Browser:

##### Allure Report Example ::
![Allure Report](src/test/resources/img/AllureReport.png?raw=true "Allure Report Example")
##### Results can be viewed in graphical view ::

![Allure Graph](src/test/resources/img/AllureGraph.png?raw=true "Results can be viewed in graphical view")
##### Allure Timeline results can be viewed in Timeline View ::

![Allure TimeLine](src/test/resources/img/AllureTimeline.png?raw=true "Allure Timeline results can be viewed in Timeline View")
##### Allure Behaviour results can be viewed in Behaviour View ::

![Allure Behaviours](src/test/resources/img/AllureBehaviours.png?raw=true "Allure Behaviour results can be viewed in Behaviour View")
##### Rest Assured detailed logs can be viewed in test body ::

![Allure PackageView](src/test/resources/img/AllurePackageView.png?raw=true "Allure results can be viewed in form of Java Packages defined in project")
##### Allure Results can be linked to Jira UserStory or TestCase as below ::

![Allure Report link to Jira](src/test/resources/img/JiraConnection.png?raw=true "Allure Results can be linked to Jira UserStory or TestCase as below")
##### Results can be filtered by Pass,Fail,Broken,Ignored statuses ::

![Allure Report with Test Status filters](src/test/resources/img/AllureFilterByStatus.png?raw=true "Results can be filtered by Pass,Fail,Broken,Ignored statuses")
###### Allure Report Additional Features ::
* Histories and Retries Tabs are also very informative.
* Report also can be downloaded offline with very detailed information by clicking on download button in Allure report.
* Screenshots on failure can be attached for Front-End UserInterface Tests.
* Additional Logs and attachments can be added explicitly apart from failures where ever required. 

### SCM

- Repository: https://github.com/bpriyatham1/weather-shopper-tests.git

### Release process
- Build pipeline: "# weather-shopper-tests" 
