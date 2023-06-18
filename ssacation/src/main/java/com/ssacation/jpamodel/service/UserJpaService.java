package com.ssacation.jpamodel.service;

import com.ssacation.jpamodel.jpo.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface UserJpaService {

    List<User> findAll();

    Optional<User> findById(String userId);

    void deleteById(String userId);

    User save(User user);

    void updateById(String userId, User updateUser);

    String getAccessToken(String code);

    HashMap<String, Object> getUserInfo(String access_Token);
}
