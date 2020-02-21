package kafka.consumer;

import integration.CustomObjectDAO;
import integration.CustomObjectEntity;
import kafka.CustomObject;
import kafka.IKafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunConsumer {
    Logger l = Logger.getLogger(RunConsumer.class);

    @Autowired
    private CustomObjectDAO customObjectDAO;

    void runConsumer(Consumer<String, CustomObject> consumer) {
        int noMessageFound = 0;
        while (true) {
            ConsumerRecords<String, CustomObject> consumerRecords = consumer.poll(1000);
            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                    // If no message found count is reached to threshold exit loop.
                    break;
                else
                    continue;
            }
            //print each record.


            consumerRecords.forEach(record -> {
                l.info(consumer.toString());
                l.info(Thread.currentThread());
                l.info("Record Key " + record.key());
                CustomObject value = record.value();
                l.info("Record value " + value);
                CustomObjectEntity entity = CustomObjectEntity.builder()
                        .age(value.getAge())
                        .firstname(value.getFirstname())
                        .lastname(value.getLastname()).build();
                Integer insert = customObjectDAO.insert(entity);
                l.info(" INSERTED " + insert);
            });


            // commits the offset of record to broker.

            consumer.commitAsync(); // sent ack to kafka ( to remove message from kafka )
        }


        consumer.close();
    }
}
