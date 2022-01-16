package com.avondock.carparkquery;

import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarparkQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarparkQueryApplication.class, args);
	}

	@Autowired
	public void configureXStreamSecurity(XStream xStream) {
		xStream.allowTypesByWildcard(new String[]{"com.avondock.carpark.cqrs.coreapi.**"});
	}

}
