package com.zzq.beauty.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.StringUtils;
import com.zzq.beauty.constant.CommonConstant;
import com.zzq.beauty.mapper.CareRecordMapper;
import com.zzq.beauty.model.CareRecord;
import com.zzq.beauty.model.Person;
import com.zzq.beauty.service.CareRecordService;
import com.zzq.beauty.service.PersonService;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CareRecordServiceImpl implements CareRecordService {
    @Autowired
    CareRecordMapper careRecordMapper;
    @Autowired
    PersonService personService;

    @Override
    @Transactional
    public Integer insert(CareRecord record) {
        careRecordMapper.insert(record);
        Person person = personService.getPersonById(record.getPersonid());
        Person pp = new Person();
        pp.setId(person.getId());
        pp.setLastCareDate(record.getCreatedate());
        personService.updatePersonSelective(pp);
        return record.getId();
    }

    @Override
    public PageBean<List<Map<String, Object>>> list(Integer pageNum, Integer pageSize, String keyWord, String startDate, String endDate) {
        PageHelper.startPage(pageNum, pageSize);
        Page<List<Map<String, Object>>> page = careRecordMapper.list(keyWord, startDate, endDate);
        return new PageBean<List<Map<String, Object>>>(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), page.getResult());
    }

    @Override
    public long getBetweenTimeCount(String startDate, String endDate) {
        return careRecordMapper.getBetweenTimeCount(startDate, endDate);
    }

    @Override
    public String lastCareDate(Integer personId) {
        return careRecordMapper.lastCareDate(personId);
    }

    @Override
    public CareRecord selectByPrimaryKey(Integer id) {
        return careRecordMapper.selectByPrimaryKey(id);
    }

}
