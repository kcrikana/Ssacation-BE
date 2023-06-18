package com.ssacation.ssafication;

import com.ssacation.jpamodel.jpo.User;
import com.ssacation.jpamodel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class SsaficationApplicationTests {

	@Autowired
	UserRepository userRepository;


	@Test
	void contextLoads() {
	}

}
