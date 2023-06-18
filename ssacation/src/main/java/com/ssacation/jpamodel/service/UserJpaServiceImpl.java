package com.ssacation.jpamodel.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssacation.jpamodel.jpo.User;
import com.ssacation.jpamodel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserJpaServiceImpl implements UserJpaService{
    @Autowired
    private UserRepository userRepository;

    static final String clientId = "f21dc05c1cc5d570d69aa3c69b9f8a17";
    static final String redirectUri = "http://localhost:8080/auth/kakao/callback";

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(u -> users.add(u));
        return users;
    }

    @Override
    public Optional<User> findById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user;
    }

    @Override
    public void deleteById(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void updateById(String userId, User updateUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            user.get().setPassword(updateUser.getPassword());
            user.get().setName(updateUser.getName());
            userRepository.save(user.get());
        }
    }

    @Override
    public String getAccessToken(String code) {
        String access_token = "";
        String refresh_token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+clientId);
            sb.append("&redirect_uri="+redirectUri);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null) result += line;
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : "+ access_token);
            System.out.println("refresh_token : "+refresh_token);


        } catch(IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }

    // 토큰으로 유저 정보 얻기
    @Override
    public HashMap<String, Object> getUserInfo(String access_Token) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null) result += line;
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("id").getAsString();

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            System.out.println(nickname);
            if(kakao_account.getAsJsonObject().get("email") != null) {
                String email = kakao_account.getAsJsonObject().get("email").getAsString();
                userInfo.put("email", email);
            }
            userInfo.put("nickname", nickname);
            userInfo.put("id", id);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }
}
