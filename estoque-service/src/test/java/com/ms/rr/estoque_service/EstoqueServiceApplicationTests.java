package com.ms.rr.estoque_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class EstoqueServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
