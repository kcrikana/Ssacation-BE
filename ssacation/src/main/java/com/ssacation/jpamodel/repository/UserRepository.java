package com.ssacation.jpamodel.repository;

import com.ssacation.jpamodel.jpo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // JpaRepository를 상속받음으로써 모든 작업이 끝
    // 자동으로 BEAN으로 등록해줍니다.
    // 그리고 왼쪽은 Entity, 오른쪽은 PK의 타입을 적습니다.
}
