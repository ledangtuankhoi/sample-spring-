package com.example.employee.Constant;

import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class AppContants {

    @Value("${springdoc.api-docs.path}")
    private String apiDocPath;

    @Value("${service.url}")
    private String serviceUrl;

    @Autowired
    private DiscoveryClient discoveryClient;

    public String getApiGatewayUrl() {
        // Lấy danh sách các instance của ứng dụng từ Eureka
        List<ServiceInstance> instances = discoveryClient.getInstances(
            "api-gateway"
        ); // Thay "app-name" bằng tên ứng dụng của bạn

        // Lấy URL từ instance đầu tiên (nếu có)
        // String serverUrl = instances.isEmpty()
        //     ? "http://localhost:8080" // Giá trị mặc định nếu không tìm thấy
        //     : instances.get(0).getUri().toString();

        String serverUrl = "http://localhost:" + instances.get(0).getPort();
        System.out.println("instancessdfasdfasdf");
        System.out.println(instances);
        return serverUrl;
    }

    public String getApiDocPath() {
        return apiDocPath;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }
}
