package com.zzq.beauty.aspect;

import com.zzq.beauty.util.SpringContextUtils;
import org.apache.ibatis.session.SqlSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

@Aspect
@Component
public class WebLogAspect {
    private Logger logger =LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(public * com.zzq.beauty.controller.*.*(..))")
    public void webLog(){}
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){

        // 接收到请求，记录请求内容
      //  logger.info("WebLogAspect.doBefore()");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        StringBuilder sb=new StringBuilder();
        sb.append("URL:");
        sb.append(request.getRequestURL().toString());
        sb.append("    IP:");
        sb.append(request.getRemoteAddr());
        //获取所有参数方法一：
        sb.append("    Parameters:    ");
        Enumeration<String> enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            sb.append(paraName);
            sb.append("=");
            sb.append(request.getParameter(paraName)).append("      ");
        }
        sb.append("操作人  ");

        HashMap map = (HashMap) request.getSession().getAttribute("user");
        if(map!=null){
            sb.append("id:");
            sb.append(map.get("id").toString());
            sb.append("   name:");
            sb.append(map.get("name").toString());
        }
        sb.append("\n");

        logger.info(sb.toString());
    }

    @AfterReturning("webLog()")
    public void  doAfterReturning(JoinPoint joinPoint){
        // 处理完请求，返回内容
       // logger.info("WebLogAspect.doAfterReturning()");
    }

}
