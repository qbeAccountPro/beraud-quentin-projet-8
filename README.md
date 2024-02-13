# beraud-quentin-projet-8
## App TourGuide
TourGuide is a student project simulating the implementation of a tourism-dedicated application. It provides users with information about local tourist attractions and discounts on hotel stays and tickets to shows.

### What you need to install 
> Java 17  
> Spring Boot 3.X  
> JUnit 5  

### How to have gpsUtil, rewardCentral and tripPricer dependencies available ?

> Run : 
- cd TourGuide
- mvn install:install-file -Dfile=libs/gpsUtil.jar -DgroupId=gpsUtil -DartifactId=gpsUtil -Dversion=1.0.0 -Dpackaging=jar  
- mvn install:install-file -Dfile=libs/RewardCentral.jar -DgroupId=rewardCentral -DartifactId=rewardCentral -Dversion=1.0.0 -Dpackaging=jar  
- mvn install:install-file -Dfile=libs/TripPricer.jar -DgroupId=tripPricer -DartifactId=tripPricer -Dversion=1.0.0 -Dpackaging=jar