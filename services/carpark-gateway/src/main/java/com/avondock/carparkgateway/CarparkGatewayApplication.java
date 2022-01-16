package com.avondock.carparkgateway;

import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarparkGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarparkGatewayApplication.class, args);
    }

    @Autowired
    public void configureXStreamSecurity(XStream xStream) {
        xStream.allowTypesByWildcard(new String[]{"com.avondock.carpark.cqrs.coreapi.**"});
    }

}
