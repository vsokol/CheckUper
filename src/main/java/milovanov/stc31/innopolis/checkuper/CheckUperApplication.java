package milovanov.stc31.innopolis.checkuper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CheckUperApplication {
    final private static Logger logger = LoggerFactory.getLogger(CheckUperApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CheckUperApplication.class, args);
    }

}
