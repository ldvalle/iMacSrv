package edesur.com.iMacSrv;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "edesur.com.iMacSrv")
@EnableAutoConfiguration
public class IMacSrvApplication {

	public static void main(String[] args) {
        SpringApplication.run(IMacSrvApplication.class, args);
	}

}
