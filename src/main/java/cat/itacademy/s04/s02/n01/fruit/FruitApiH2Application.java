package cat.itacademy.s04.s02.n01.fruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "cat.itacademy.s04.s02.n01.fruit.repository.sql")
@EnableMongoRepositories(basePackages = "cat.itacademy.s04.s02.n01.fruit.repository.mongo")
public class FruitApiH2Application {

    public static void main(String[] args) {
        SpringApplication.run(FruitApiH2Application.class, args);
    }
}