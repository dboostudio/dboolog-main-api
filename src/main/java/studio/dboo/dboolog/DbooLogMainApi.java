package studio.dboo.dboolog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DbooLogMainApi {

    public static void main(String[] args) {
        SpringApplication.run(DbooLogMainApi.class, args);
    }

}
