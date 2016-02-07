package online.bbstats.config;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import online.bbstats.date.USLocalDateFormatter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


	
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
  

  @Override 
  public void addFormatters(FormatterRegistry registry) {
      registry.addFormatterForFieldType(LocalDate.class, new USLocalDateFormatter());
  }
  
  @Bean
  public LocaleResolver localeResolver() {
      return new SessionLocaleResolver();
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
      localeChangeInterceptor.setParamName("lang");
      return localeChangeInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(localeChangeInterceptor());
  }
  
  @Bean
  public SpringSecurityDialect securityDialect() {
      return new SpringSecurityDialect();
  }
  
  
}
