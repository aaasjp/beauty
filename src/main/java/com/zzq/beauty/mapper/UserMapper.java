package com.zzq.beauty.mapper;

import com.github.pagehelper.Page;
import com.zzq.beauty.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 获取所有管理元
     * @return
     */
    Page<List<Map<String,Object>>> getUserList(String username);

    /**
     * 判断当前账号是否已经存在
     */
    Long isHaveUserName(String userName);

    void freezeOrUnfreeze(@Param("userId") Integer userId,@Param("state") Integer state);


    @Select("SELECT `user`.id,`user`.username,person.`name`,`user`.state FROM `user`,person " +
            "WHERE person.userId=`user`.id AND `user`.username=#{userName} AND `user`.`password`=#{passWord} LIMIT 0,1")
    Map<String,Object> singIn(@Param("userName")String userName,@Param("passWord") String passWord);
}