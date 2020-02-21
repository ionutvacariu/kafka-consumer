 FROM openjdk:12-alpine

 COPY target/kafka-consumer*-jar-with-dependencies.jar /kafka-consumer.jar

 CMD ["java","-jar","/kafka-consumer.jar"]