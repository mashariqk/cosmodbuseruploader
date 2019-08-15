package com.data.load.cosmoUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@IntegrationComponentScan
public class CosmoUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosmoUserApplication.class, args);
    }

}
