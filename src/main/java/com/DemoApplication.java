package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import com.ijpay.config.StartupRunner;
import com.ijpay.config.TaskRunner;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.order.dao")
@EnableJpaRepositories(basePackages = "com.order.repository")
@EntityScan(basePackages = "com.order.entity")
public class DemoApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoApplication.class);
    }

    @Bean
    public StartupRunner startupRunner(){
         return new StartupRunner();
     }

    @Bean
    public TaskRunner taskRunner(){
         return new TaskRunner();
     }
}
