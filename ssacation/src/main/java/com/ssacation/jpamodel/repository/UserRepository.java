package com.ssacation.jpamodel.repository;

import com.ssacation.jpamodel.jpo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}

