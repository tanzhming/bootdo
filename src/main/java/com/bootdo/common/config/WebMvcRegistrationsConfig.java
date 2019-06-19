package com.bootdo.common.config;

import com.bootdo.common.version.CustomRequestMappingHandlerMapping;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @desc
 * @com xcwlkj.com
 * @Author tanzhiming(Jruoning) 2019/6/19 13:55
 */
@SpringBootConfiguration
public class WebMvcRegistrationsConfig implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new CustomRequestMappingHandlerMapping();
    }
}
