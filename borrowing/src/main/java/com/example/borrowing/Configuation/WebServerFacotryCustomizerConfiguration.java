package com.example.borrowing.Configuation;

import java.util.Random;
import javax.swing.Spring;
import org.apache.hc.core5.net.Ports;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
// @Configuration
// public class WebServerFacotryCustomizerConfiguration
//     implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
//     // @Value("${port.number.min:8080}")
//     // private Integer minPort;
//     // @Value("${port.number.max:8090}")
//     // private Integer maxPort;
//     // // Random Server Ports and Spring Cloud Service Discovery with Netflix Eureka
//     // @Override
//     // public void customize(ConfigurableServletWebServerFactory factory) {
//     //     int port = SocketUtils.findAvailableTcpPort(minPort, maxPort);
//     //     factory.setPort(port);
//     //     System.getProperties().put("server.port", port);
//     // }
// }
