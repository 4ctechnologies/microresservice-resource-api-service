package be.foreseegroup.micro.resourceservice.resourceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Kaj on 30/09/15.
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class ResourceApiServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceApiServiceApplication.class, args);
    }
}
