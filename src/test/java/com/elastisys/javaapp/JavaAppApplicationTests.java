package com.elastisys.javaapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JavaAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@BeforeAll
	public static void init() throws InterruptedException {
		Thread.sleep(30000);
	}

	@Test
	public void testApplication() {
		JavaAppApplication javaApp = new JavaAppApplication();
		String result = javaApp.sanitize();
		assertEquals(result, "{\"key1\":\"value1\",\"type\":\"Booking\",\"sid\":\"A435211\",\"region\":\"ASIA\",\"fetchFromFile\":\"false\",\"service\":\"true\",\"isEom\":\"true\"}");
	}

}
