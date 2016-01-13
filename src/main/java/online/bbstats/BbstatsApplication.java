package online.bbstats;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpEncodingProperties;
import org.springframework.boot.context.web.OrderedCharacterEncodingFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
 
@SpringBootApplication
public class BbstatsApplication extends SpringBootServletInitializer {
	@Value("${spring.datasource.driverClassName}")
	private String databaseDriverClassName;
	 
	@Value("${spring.datasource.url}")
	private String datasourceUrl;
	 
	@Value("${spring.datasource.username}")
	private String databaseUsername;
	 
	@Value("${spring.datasource.password}")
	private String databasePassword;
	
	@Autowired
	private HttpEncodingProperties httpEncodingProperties;
	@Bean
	public OrderedCharacterEncodingFilter characterEncodingFilter() {
	  OrderedCharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
	  filter.setEncoding(this.httpEncodingProperties.getCharset().name());
	  filter.setForceEncoding(this.httpEncodingProperties.isForce());
	  filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
	  return filter;
	}

 
    public static void main(String[] args) {
        SpringApplication.run(BbstatsApplication.class, args);
    }
    
    @Bean
    public DataSource datasource() {
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(databaseDriverClassName);
        ds.setUrl(datasourceUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);
     
        return ds;
    }
    
   
 
}