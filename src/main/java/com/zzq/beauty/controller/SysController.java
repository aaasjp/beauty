package com.zzq.beauty.controller;

import com.zzq.beauty.bean.Sys;
import com.zzq.beauty.service.SysService;
import com.zzq.beauty.util.PropUtil;
import com.zzq.beauty.util.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * 系统参数
 */
@Controller
@RequestMapping("/sys")
public class SysController {
    @Autowired
    private SysService sysService;
    @RequestMapping("/sys")
    public ModelAndView sysPage() throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        String inventoryWarning= sysService.select("inventoryWarning").getValue();
        String dontCareByDay= sysService.select("dontCareByDay").getValue();
        String indexStatistics= sysService.select("indexStatistics").getValue();
        modelAndView.addObject("inventoryWarning",inventoryWarning);
        modelAndView.addObject("dontCareByDay",dontCareByDay);//
        modelAndView.addObject("indexStatistics",indexStatistics);
        modelAndView.setViewName("/sys/sys");
        return  modelAndView;
    }
    @RequestMapping("/update")
    public ModelAndView update(@ModelAttribute Sys sys){
        com.zzq.beauty.model.Sys sysMode=sysService.select("inventoryWarning");
        sysMode.setValue(sys.getInventoryWarning());
        sysMode.setUpdateDate(new Date());
        sysService.update(sysMode);

        sysMode=sysService.select("indexStatistics");
        sysMode.setValue(sys.getIndexStatistics());
        sysMode.setUpdateDate(new Date());
        sysService.update(sysMode);

        sysMode=sysService.select("dontCareByDay");
        sysMode.setValue(sys.getDontCareByDay());
        sysMode.setUpdateDate(new Date());
        sysService.update(sysMode);
        /*HashMap<String,String> map = new HashMap<String,String>();
        map.put("inventoryWarning",sys.getInventoryWarning());
        map.put("dontCareByDay",sys.getDontCareByDay());
        map.put("indexStatistics",sys.getIndexStatistics());
        PropUtil.writePropertiesList("D:\\conf.properties",map);*/
        return new View().redirect("/sys/sys");
    }
}
