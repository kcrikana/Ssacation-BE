package com.ssacation;

import com.ssacation.jpamodel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SsacationApplication {
	public static void main(String[] args) {
		SpringApplication.run(SsacationApplication.class, args);
	}
}
