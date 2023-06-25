package com.ssacation.jpamodel.controller;


import com.ssacation.jpamodel.jpo.User;
import com.ssacation.jpamodel.service.UserJpaServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserRestJpaController {
    static final String clientId = "f21dc05c1cc5d570d69aa3c69b9f8a17";
    static final String redirectUri = "http://localhost:8080/auth/kakao/callback";

    @Autowired
    UserJpaServiceImpl ujs;

    @GetMapping("/user")
    @ApiOperation(value = "등록된 모든 사용자 정보를 반환한다.", response = User.class)
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = ujs.findAll();
            if (users != null && users.size() > 0) {
                return new ResponseEntity<List<User>>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "{id}에 해당하는 사용자 정보를 반환한다.", response = User.class)
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        Optional<User> user = ujs.findById(userId);
        if(user==null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
    }



    @PostMapping("/user")
    @ApiOperation(value = "사용자 정보를 삽입한다.", response = Integer.class)
    public ResponseEntity<?> save(@RequestBody User user) {
        return new ResponseEntity<User>(ujs.save(user), HttpStatus.OK);
    }

    @PutMapping("/user")
    @ApiOperation(value = "사용자 정보를 수정한다.", response = Integer.class)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        ujs.updateById(user.getId(), user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @ApiOperation(value = "{id} 에 해당하는 사용자 정보를 삭제한다.", response = Integer.class)
    public ResponseEntity<?> delete(@PathVariable String userId) {
        ujs.deleteById(userId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/auth/kakao/callback")	// 데이터를 리턴해주는 컨트롤러 함수
    public @ResponseBody ResponseEntity<?> kakaoCallback(String code) {
        System.out.println(code);
        String access_Token = ujs.getAccessToken(code);
        System.out.println(access_Token);
        HashMap<String, Object> userInfo = ujs.getUserInfo(access_Token);

        System.out.println(userInfo);
        Optional<User> user = ujs.findById((String)userInfo.get("email"));

        if(user != null) return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
        else return new ResponseEntity<Optional<User>>(user,HttpStatus.NO_CONTENT);
    }
}
