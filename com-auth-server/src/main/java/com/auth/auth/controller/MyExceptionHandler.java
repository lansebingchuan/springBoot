package com.auth.auth.controller;

import com.common.common.dto.R;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R ErrorHandler(Exception e) {
        if(e instanceof BindException){
            BindException bindException = (BindException)e;
            FieldError fieldError = bindException.getFieldErrors().get(0);
            return R.error(fieldError.getDefaultMessage());
        }
        return R.error();
    }
}