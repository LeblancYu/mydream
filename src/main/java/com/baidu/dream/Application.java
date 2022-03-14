package com.baidu.dream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @ClassName Application
 * @Description 启动程序
 * @Author wangjiangyu
 * @Date 2022/3/13 14:27
 */
@EnableCaching
@ServletComponentScan
@SpringBootApplication
public class Application {


    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }



}
