package com.elastisys.javaapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Repeat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class JavaAppApplicationTests {

	@Test
	void contextLoads() {
	try {
			Thread.sleep(60000);
		 } catch (InterruptedException e) {
		 	throw new RuntimeException(e);
		 }
	}

	@Test
	@RepeatedTest(10)
	public void testApplication() {
		JavaAppApplication javaApp = new JavaAppApplication();
		String result = javaApp.sanitize();
		assertEquals(result, "{\"key1\":\"value1\",\"type\":\"Booking\",\"sid\":\"A435211\",\"region\":\"ASIA\",\"fetchFromFile\":\"false\",\"service\":\"true\",\"isEom\":\"true\"}");
	}

}
