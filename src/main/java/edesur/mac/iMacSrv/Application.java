package edesur.mac.iMacSrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication(scanBasePackages={"edesur.mac.iMacSrv"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
