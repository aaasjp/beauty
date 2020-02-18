package com.zzq.beauty.exceptionHandler;

import com.zzq.beauty.exception.RestException;
import com.zzq.beauty.rest.MyRestResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(RestException.class)
    public @ResponseBody
    MyRestResponse restExceptionHandler(RestException e) {
        /*e.printStackTrace();*/
        /*if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }*/
        return new MyRestResponse<Object>(e.getCode(), e.getMessage(), e.getData());
    }
}
