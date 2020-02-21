call mvn clean
call mvn install
call docker build -t ionut/kafka-consumer .
call docker run -d ionut/kafka-consumer