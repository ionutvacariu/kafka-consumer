package kafka.consumer;


import integration.CustomObjectDAO;
import kafka.IKafkaConstants;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {CustomObjectDAO.class,RunConsumer.class})
public class App extends SpringBootServletInitializer {

    @Autowired
    private RunConsumer runConsumer;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

    }

    @Bean
    public void configureLog() {
        BasicConfigurator.configure();
    }

    @Bean
    public void startConsumer() {
        runConsumer.runConsumer(ConsumerCreator.createConsumer(IKafkaConstants.GROUP_ID_CONFIG));
    }

}