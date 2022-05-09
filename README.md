# TWITTER TIMELINE

## Starting 🚀

This project has the purpose of obtaining a timeline of a profile through the api exposed by twitter, in addition to obtaining additional information from a database (RDS)

# Requirements📋
- Gradle
- JDK 11
- IDE (Puede ser Intellij)

# Execution 🔧
To initialize the project in principle it must be compiled for this you can use the 'gradlew build' command or depending on the IDE this can be done automatically, after this to
You can run it with the 'gradlew bootrun' command or find the main.application method (applications - src - main - MainApplication) and run it from there depending on the IDE you are using.

# Running the tests⚙️
For the execution of the tests, the command 'gradlew build' must be previously executed, after this the 'gradlew clean build jacocoRootReport' is executed, with this a test report is obtained which will be found in the build folder - reports - jacocoRootReport - html - index.html

# Deployment 📦
For deployment at this time only the Dockerfile is configured, we would be missing the k8s files for deployment with Kubernetes.

# Built with🛠️
- Spring WebFlux
- Arquitecutra Limpia

# Authors ✒️
Development - Jhonatan Lasso

## Time of development🚀
For this microservice with development and unit tests approximately 6h since some connection configurations had to be investigated for the correct operation with webflux.

## Important!
The front-end must be run on port 4200 since it is only allowed by the back-end to consume it from that source





