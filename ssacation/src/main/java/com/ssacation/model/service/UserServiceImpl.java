package com.ssacation.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.ssacation.model.dao.UserDao;
import com.ssacation.model.dto.SearchCondition;
import com.ssacation.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	static final String clientId = "f21dc05c1cc5d570d69aa3c69b9f8a17";
	static final String redirectUri = "http://localhost:8080/auth/kakao/callback";
	
	//@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserDao getUserDao() {
		return this.userDao;
	}
	
	
	@Override
	@Transactional
	public int insert(User user) {
		return userDao.insert(user);
	}

	@Override
	@Transactional
	public int delete(String id) {
		return userDao.delete(id);
	}

	@Override
	@Transactional
	public int update(User user) {
		return userDao.update(user);
	}

	@Override
	public User searchById(String id) {
		return userDao.searchById(id);
	}

	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}

	@Override
	public List<User> searchByName(String name) {
		return userDao.searchByName(name);
	}

	@Override
	public List<User> searchByCondition(SearchCondition con) {
		return userDao.searchByCondition(con);
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
