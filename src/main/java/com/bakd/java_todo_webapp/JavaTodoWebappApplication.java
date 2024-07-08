package com.bakd.java_todo_webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JavaTodoWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTodoWebappApplication.class, args);
	}

}
