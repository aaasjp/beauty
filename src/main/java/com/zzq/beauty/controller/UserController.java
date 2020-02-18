package com.zzq.beauty.controller;

import com.zzq.beauty.model.Person;
import com.zzq.beauty.rest.MyRestResponse;
import com.zzq.beauty.service.BrokerService;
import com.zzq.beauty.service.PersonService;
import com.zzq.beauty.util.PageBean;
import com.zzq.beauty.util.RestCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController{
	@Autowired
	private PersonService personService;
	@Autowired
    private BrokerService brokerService;

    private ModelAndView modelAndView;

    @RequestMapping("/userList")
	public ModelAndView userList(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                                 @RequestParam(value = "keyWord",defaultValue = "",required = false) String  keyWord,
                                 @RequestParam(value = "outCare",defaultValue = "0",required=false) Integer outCare
                                 ){
	    modelAndView = new ModelAndView();
        PageBean<List<Map<String,Object>>>page=null;
        if(outCare==0){
            page= personService.getPersonAndReCommender(pageNum,10,"%"+keyWord+"%");
        }else{
            page= personService.getPersonAndReCommenderAndWhere(pageNum,10,"%"+keyWord+"%",outCare);
        }
        modelAndView.addObject("list",page.getList());
        modelAndView.addObject("page",page);
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.addObject("outCare",outCare);
        modelAndView.setViewName("/user/userList");
        return modelAndView;
    }
    @RequestMapping("/addUser")
    public ModelAndView addUser(
                                @RequestParam(value = "id",required = false) Integer personId
    ){
	    modelAndView = new ModelAndView();
	    if(personId==null||personId==0){//add

        }else{//edit
            Person person =personService.getPersonById(personId);
            modelAndView.addObject("person",person);
            //modelAndView.addObject("salesManId",person.getUserid());
        }

	    //modelAndView.addObject("salesMans",personService.getSalesman());
	    modelAndView.setViewName("/user/addUser");
        return modelAndView;
    }
    @RequestMapping("/saveUser")
    @Transactional
    public @ResponseBody MyRestResponse saveUser(@RequestParam(value = "user_Id",defaultValue = "0",required = false) Integer user_Id,
                            @ModelAttribute("person") Person person,
                            @RequestParam(value = "personId",defaultValue = "0",required = false) Integer personId
    ){
        if(personId==null||personId==0){//save
            person.setType(1);
            person.setUserid(user_Id);
            person.setCreatedate(new Date());
            personService.insert(person);
            return new MyRestResponse(RestCode._200.getCode(),"添加成功！");
        }else{   //update
            person.setId(personId);
            person.setUserid(user_Id);
            person.setUpdatedate(new Date());
            personService.updatePersonSelectiveAndbroker(person);
            return new MyRestResponse(RestCode._300.getCode(),RestCode._300.getMessage());
        }

    }

}
