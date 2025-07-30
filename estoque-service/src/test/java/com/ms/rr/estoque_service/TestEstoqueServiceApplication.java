package com.ms.rr.estoque_service;

import org.springframework.boot.SpringApplication;

public class TestEstoqueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(EstoqueServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
