package com.zzq.beauty.util;

import org.springframework.web.servlet.ModelAndView;

public class View {
    private  ModelAndView modelAndView=null;

    public View() {
        this.modelAndView = new ModelAndView();
    }

    public  ModelAndView add(String key,Object value){
        modelAndView.addObject(key,value);
        return modelAndView;
    }
    public ModelAndView redirect(String url){
        modelAndView.setViewName("redirect:"+url);
        return modelAndView;
    }
    public ModelAndView setViewName(String viewName){
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
