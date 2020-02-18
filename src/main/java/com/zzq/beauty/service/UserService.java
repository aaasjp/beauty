package com.zzq.beauty.service;

import com.zzq.beauty.model.User;
import com.zzq.beauty.util.PageBean;

import java.util.List;
import java.util.Map;

public interface UserService {
	/**
	 * 插入
	 * @param user
	 */
	void insert(User user);

	/**
	 * 查询账号以及账号关联的person
	 * @param pageNum
	 * @param pageSize
	 * @param username
	 * @return
	 */
	PageBean<List<Map<String,Object>>> getUserList(int pageNum, int pageSize, String username);

	/**
	 * 查询用户名是否存在
	 * @param username
	 * @return
	 */
	int isHaveUserName(String username);

	/**
	 * 根据主键查询User
	 * @param id
	 * @return
	 */
	User getUserById(Integer id);

	/**
	 * 更新User
	 * @param user
	 */
	void updateUser(User user);

	/**
	 * 空值不更新
	 */
	void updateUserSelective(User user);

	/**
	 * 冻结或者解冻账号
	 */
	void freezeOrUnfreeze(Integer userId);

	/**
	 * 登录
	 */
	Map<String,Object> singIn(String userName,String passWord);
}
