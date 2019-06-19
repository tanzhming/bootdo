package com.bootdo.common.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @desc api版本管理自定义注解
 * @com xcwlkj.com
 * @Author tanzhiming(Jruoning) 2019/6/19 13:44
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
    int value();
}
