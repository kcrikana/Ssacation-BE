package com.ssacation.model.dao;

import java.util.List;

import com.ssacation.model.dto.SearchCondition;
import com.ssacation.model.dto.User;

public interface UserDao {
	
	int insert(User user);
	
	int delete(String id);
	
	int update(User user);
	
	User searchById(String id);
	
	List<User> selectAll();
	
	List<User> searchByName(String name);

	List<User> searchByCondition(SearchCondition con);
}
