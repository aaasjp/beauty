package com.zzq.beauty.controller;

import com.zzq.beauty.model.Person;
import com.zzq.beauty.model.User;
import com.zzq.beauty.rest.MyRestResponse;
import com.zzq.beauty.service.PersonService;
import com.zzq.beauty.service.UserService;
import com.zzq.beauty.util.PageBean;
import com.zzq.beauty.util.RestCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;

    private User user;
    private Person person;

    @RequestMapping("/adminList")
    public ModelAndView adminList(
            @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
            @RequestParam(value = "keyWord",defaultValue = "",required = false) String keyWord
    ){

        ModelAndView modelAndView = new ModelAndView();
        PageBean<List<Map<String,Object>>> page =userService.getUserList(pageNum,10,"%"+keyWord+"%");
        modelAndView.addObject("list",page.getList());
        modelAndView.addObject("page",page);
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.setViewName("admin/adminList");
        return modelAndView;
    }
    @RequestMapping("/addAdmin")
    public ModelAndView addAdmin(@RequestParam(value = "id",defaultValue = "0",required =false) Integer id){
        ModelAndView modelAndView = new ModelAndView();
        if(id!=0){//更新
            person=personService.getPersonById(id);
            user=userService.getUserById(person.getUserid());
            modelAndView.addObject("person",person);
            modelAndView.addObject("user",user);
        }
        modelAndView.setViewName("admin/addAdmin");
        return modelAndView;
    }
    @RequestMapping("/saveAdmin")
    public @ResponseBody
    MyRestResponse saveAdmin(@ModelAttribute User user, @ModelAttribute  Person person,
                             @RequestParam(value = "personId",required = false) Integer personId,
                             @RequestParam(value = "userId",required = false) Integer userId
                             ){
        if(personId==null&&userId==null){//新增
            if(userService.isHaveUserName(user.getUsername())>0){
                return new MyRestResponse(RestCode._201.getCode(),RestCode._201.getMessage());
            }
            user.setCreatedate(new Date());
            user.setState(0);
            userService.insert(user);
            person.setUserid(user.getId());
            person.setType(0);
            person.setCreatedate(new Date());
            personService.insert(person);
            return new MyRestResponse(200,"成功");
        }else{//修改
            person.setUpdatedate(new Date());
            person.setId(personId);
            user.setId(userId);
            //user.setCreatedate(new Date());
            //personService.updatePersonSelectiveAndbroker(person);
            personService.updatePersonSelective(person);
            userService.updateUserSelective(user);
            return new MyRestResponse(RestCode._300.getCode(),RestCode._300.getMessage());
        }

    }
    @RequestMapping("/freezeOrUnfreeze")
    public @ResponseBody MyRestResponse freezeOrUnfreeze(@RequestParam(value = "userId") Integer userId){
        userService.freezeOrUnfreeze(userId);
        return new MyRestResponse(RestCode._200.getCode(),"更新成功");
    }
   /* @RequestMapping("/freezeOrUnfreeze")
    public ModelAndView freezeOrUnfreeze(@RequestParam(value = "userId",required = false) Integer userId){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("redirect:/admin/adminList");
        return modelAndView;// "redirect:/admin/adminList";
    }*/
}
