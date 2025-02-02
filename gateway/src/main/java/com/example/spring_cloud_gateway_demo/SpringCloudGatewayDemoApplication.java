package com.example.spring_cloud_gateway_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
// @ComponentScan(basePackages = {"com.example.spring_cloud_gateway_demo"})
@EnableWebFlux
public class SpringCloudGatewayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayDemoApplication.class, args);
        //     try (AnnotationConfigApplicationContext context
        //     = new AnnotationConfigApplicationContext(SpringSecurity6Application.class)) {
        //       context.getBean(NettyContext.class).onClose().block();
        //   }
    }
}
