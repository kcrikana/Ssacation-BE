package com.ssacation.jpamodel.jpo;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@ToString   // toString 어노테이션
@Getter     // getter, setter 메서드 선언 -> lombok이 있어야 사용가능
@Setter
@Builder    /* 객체를 정의하고 그 객체를 생성할 때 보통 생성자를 통해 생성하는 패턴
                조건: NoArgsConstructor와 AllArgsConstructorrk 같이 선언 되어야함*/
@AllArgsConstructor     // 모든 속성이 들어간 생성자 생성 어노테이션
@NoArgsConstructor      // 기본 생성자 생성 어노테이션
@Entity     // 테이블 개체 선언
@Table(name = "users")      // 테이블 이름
public class User {

    @Id             // PK지정
    private String id;

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String studygroup;

    @Column(nullable = false)
    private String baekId;


}
