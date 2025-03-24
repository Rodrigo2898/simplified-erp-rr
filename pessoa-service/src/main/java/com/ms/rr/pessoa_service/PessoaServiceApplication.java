package com.ms.rr.pessoa_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PessoaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PessoaServiceApplication.class, args);
	}

}
