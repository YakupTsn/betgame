package com.spottoto.bet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties
public class BetApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+03:00"));
		Locale.setDefault(Locale.ENGLISH);

		SpringApplication.run(BetApplication.class, args);
	}


	@Bean
	public ModelMapper getModelMapper(){
		return  new ModelMapper();
	}


}
