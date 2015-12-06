package online.bbstats;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class BbstatsApplication {
	
	@Value("${spring.datasource.driverClassName}")
	private String databaseDriverClassName;
	 
	@Value("${spring.datasource.url}")
	private String datasourceUrl;
	 
	@Value("${spring.datasource.username}")
	private String databaseUsername;
	 
	@Value("${spring.datasource.password}")
	private String databasePassword;
 
    public static void main(String[] args) {
        SpringApplication.run(BbstatsApplication.class, args);
    }
 
}