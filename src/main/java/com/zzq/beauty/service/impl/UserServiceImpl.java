package com.zzq.beauty.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzq.beauty.mapper.UserMapper;
import com.zzq.beauty.model.User;
import com.zzq.beauty.service.UserService;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	public void insert(User user){
		userMapper.insert(user);
	}
	@Override
	public PageBean<List<Map<String,Object>>> getUserList(int pageNum, int pageSize, String username){
		PageHelper.startPage(pageNum,pageSize);
		Page<List<Map<String,Object>>> page =userMapper.getUserList(username);
		return new PageBean<List<Map<String,Object>>>(page.getPageNum(),page.getPageSize(),page.getTotal(),page.getPages(),page.getResult());
	}

	@Override
	public int isHaveUserName(String username) {
		return userMapper.isHaveUserName(username).intValue();
	}

	@Override
	public User getUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateByPrimaryKey(user);
	}

	@Override
	public void freezeOrUnfreeze(Integer userId) {
		User user =userMapper.selectByPrimaryKey(userId);
		userMapper.freezeOrUnfreeze(userId,user.getState()==0?1:0);
	}

	@Override
	public void updateUserSelective(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public Map<String, Object> singIn(String userName, String passWord) {
		return userMapper.singIn(userName,passWord);
	}
}
