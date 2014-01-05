Integration with TestNG
=======================
Please check the branch feature/testng, where JUnit has been replaced with testNG. JBehave itself is independent from
the underlying test framework, so there is no change in how the tests are run - i.e. ```mvn test``` or ```mvn integration-test```. Here is how you define TestNG suite XML files, in ```pom.xml```:
```
<plugins>
...
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.16</version>
    <configuration>
      <suiteXmlFiles>
        <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
        <!-- Feel free to add more files in here -->
      </suiteXmlFiles>
    </configuration>
  </plugin>
...
</plugins>
```

In my opinion, Maven is the best choice of how to run the project.

If you wish so, you can run the project using the testNG explicitly. However, you would need add all of the jar files
on the classpath yourself. Assuming there is a lib directory in the root of the project containing all the jars the project
depends on, just run:

```java -cp "lib/*:target/test-classes:target/activiti-demo-1.0.jar" org.testng.TestNG src/test/resources/testng.xml```


