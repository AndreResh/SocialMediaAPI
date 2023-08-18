FROM adoptopenjdk/openjdk11
COPY build/libs/SocialMediaAPI-0.0.1-SNAPSHOT.jar SocialMediaAPI-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SocialMediaAPI-0.0.1-SNAPSHOT.jar"]