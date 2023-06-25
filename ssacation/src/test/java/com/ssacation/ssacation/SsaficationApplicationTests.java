package com.ssacation.ssacation;

import com.ssacation.jpamodel.jpo.User;
import com.ssacation.jpamodel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SsaficationApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	void InsertDummies() {
		User user1 = User.builder()
					.id("ssafy@ssafy.com")
					.password("1234")
					.name("ssafy")
					.build();
		System.out.println(user1.getId());
		userRepository.save(user1);
	}
	@Test
	void contextLoads() {
	}


}


