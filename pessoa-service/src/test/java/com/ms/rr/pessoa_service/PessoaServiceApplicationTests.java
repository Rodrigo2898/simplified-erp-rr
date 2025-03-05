package com.ms.rr.pessoa_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PessoaServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	public static void main(String[] args) {
		SpringApplication.from(PessoaServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
