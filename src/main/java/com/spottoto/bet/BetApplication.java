package com.spottoto.bet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetApplication.class, args);
	}


	@Bean
	public ModelMapper getModelMapper(){
		return  new ModelMapper();
	}


}
