package com.zzq.beauty.conf;

import com.zzq.beauty.interceptor.WebInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConf implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns("/**") addPathPatterns拦截所有方法  excludePathPatterns("/user/test")白名单
        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/static/**").
                excludePathPatterns("/").
                excludePathPatterns("/singIn").excludePathPatterns("/out");
    }

    /**
     * 解决静态资源无法访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
    }
}
