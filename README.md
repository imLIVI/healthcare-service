## TestingHealthService-Mockito
### Description
1. Clone the remote repository of the <a href=https://github.com/neee/healthcare-service>service</a> or make it a fork (preferably) or download it to yourself as an archive;
2. Connect junit and **mockito** dependencies to the maven project (they need to be added to the pom ```pom.xml```);
3. Create a class for tests in the ```src/test/java``` folder (you can also create subfolders according to the package of the class you will be testing);
4. Create tests according to the task (for the ```MedicalServiceImpl``` service, you must create stubs (mock) in the form of ```PatientInfoFileRepository``` and ```SendAlertService```) at least 3 unit tests:
- Check the message output during the ```checkBloodPressure```
- Check the message output during the ```checkTemperature``` 
- Check that the messages are not displayed when the indicators are normal.
6. Submit the task for review.

### Realization
I used:

1. Project <a href=https://github.com/neee/healthcare-service>service</a>
2. JUnit - framework
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.9.1</version>
    <scope>test</scope>
</dependency>
```
3. Mockito
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>4.8.0</version>
    <scope>test</scope>
</dependency>
```
5. Maven
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>
        </plugin>
    </plugins>
</build>
```
