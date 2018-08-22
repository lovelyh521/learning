/**  
 * Project Name:vrms-project  
 * File Name:SwaggerConfig.java  
 * Package Name:com.chinamobile.cmss.vrms.config  
 * Date:2018年8月8日下午5:19:08  
 * Copyright (c) 2018, www.cmsoft.10086.cn All Rights Reserved.  
 *  
*/  
  
package com.xiejieyi.demo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**  
 * ClassName:SwaggerConfig <br/>  
 * Date:     2018年8月8日 下午5:19:08 <br/>  
 * @author   HeTianrui  
 * @version    
 * @since    JDK 1.8 
 * @see        
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Demo工程swagger接口文档")
                .apiInfo(new ApiInfoBuilder().title("Demo工程swagger接口文档")
                        .contact(new Contact("谢洁意", "http://10.154.4.3:18085/swagger-ui.html", "hetianrui@cmss" +
                                ".chinamobile.com")).version("1.0").build())
                .select().apis(RequestHandlerSelectors.basePackage("com.chinamobile.cmss.vrms.demo.controller")).paths
                        (PathSelectors.any()).build();
    }
}
  
