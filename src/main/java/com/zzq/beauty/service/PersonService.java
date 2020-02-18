package com.zzq.beauty.service;

import com.zzq.beauty.model.Person;
import com.zzq.beauty.util.PageBean;

import java.util.List;
import java.util.Map;

public interface PersonService {
    public void insert(Person person);

    Person getPersonById(Integer id);

    Person getPersonByUserId(Integer userId);

    /**
     * 更新
     *
     * @param person
     */
    void updatePerson(Person person);

    /**
     * 分页返回用户以及推荐人
     *
     * @param pageNum
     * @param pageSize
     * @param keyWord
     * @return
     */
    PageBean<List<Map<String, Object>>> getPersonAndReCommender(int pageNum, int pageSize, String keyWord);

    PageBean<List<Map<String, Object>>> getPersonAndReCommenderAndWhere(int pageNum, int pageSize, String keyWord, Integer outCareDay);

    /**
     * 返回所有业务员
     */
    List<Person> getSalesman();

    /**
     * 更新用户的同时更新业务员
     *
     * @param person
     */
    void updatePersonSelectiveAndbroker(Person person);

    void updatePersonSelective(Person person);

    /**
     * 获取一段时间内 新注册的用户
     *
     * @param startDate
     * @param endDate
     * @return
     */
    long getBetweenTimePerson(String startDate, String endDate);

    /**
     * 护理超时人数
     */
    long getCareOutTimeTimePerson(String startDate, String endDate);


}
