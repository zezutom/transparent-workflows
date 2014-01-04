transparent-workflows
=====================

BDD &amp; Activiti to make workflows crystal clear and easy to maintain

Integration with TestNG
=======================
Please check the branch feature/testng. JUnit has been replaced with testNG. JBehave itself is independent from
the underlying test framework, so there is no change in how the project run - i.e. mvn clean package or mvn clean test.
In my opinion, maven is the best choice of how to run the project.

If you wish so, you can run the project using the testNG explicitly. However, you would need add all of the jar files
on the classpath yourself. Assuming there is a lib directory in the root of the project containing all the jars the project
depends on, just run:

java -cp "lib/*:target/test-classes:target/activiti-demo-1.0.jar" org.testng.TestNG src/main/resources/testng.xml


