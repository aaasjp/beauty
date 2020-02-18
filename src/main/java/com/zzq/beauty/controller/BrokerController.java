package com.zzq.beauty.controller;

import com.zzq.beauty.service.BrokerService;
import com.zzq.beauty.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/broker")
public class BrokerController {
    @Autowired
    private BrokerService brokerService;
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                             @RequestParam(value = "user",defaultValue = "",required = false) String user,
                             @RequestParam(value = "puller",defaultValue = "",required = false) String puller
    ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        modelAndView.addObject("puller",puller);
        PageBean<List<Map<String,Object>>> pageBean =brokerService.getAllPuller("%"+user+"%","%"+puller+"%",pageNum,10);
        modelAndView.addObject("list",pageBean.getList());
        modelAndView.addObject("page",pageBean);
        modelAndView.setViewName("/broker/brokerList");
        return modelAndView;
    }
}
