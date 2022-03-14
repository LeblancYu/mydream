package com.baidu.dream.domain.day.service.impl;

import com.baidu.dream.domain.day.service.DayOfTradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName DayOfTradingServiceImpl
 * @Description  交易日、交易时间处理
 * @Author wangjiangyu
 * @Date 2022/3/14 21:57
 */
@Service
//@Component
public class DayOfTradingServiceImpl implements DayOfTradingService {

    /**
     * RestTemplate是Spring提供的用于访问Rest服务的客户端
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 节假日三方接口地址
     */
    private static String HOLIDAY_TOOL_API_URL;

    /**
     * 获取配置文件中节假日三方接口地址
     * @param holidayToolUrl
     */
    @Value("${holiday.tool.url}")
    public void setHolidayToolUrl(String holidayToolUrl){
        HOLIDAY_TOOL_API_URL = holidayToolUrl;
    }

    /**
     * 模拟测试开关  OFF关闭  ON开启
     */
    @Value("${mock.test.turn}")
    private String MockTest;



    @Override
    public void getHoliday() {
        //获取当前年
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Map<String, Integer> dateInfo = restTemplate.getForObject(HOLIDAY_TOOL_API_URL, Map.class);
        List<LocalDate> holidayDateList = dateInfo.entrySet().stream().filter(entry -> entry.getValue() != 0).map(entry -> {
            LocalDate date = LocalDate.parse(year + entry.getKey(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            return date;
        }).collect(Collectors.toList());
        //TODO 缓存

    }

    /**
     * 判断是否交易日
     */
    @Override
    public boolean isBusinessDate(Date date) {
        //判断模拟测试开关是否开启
        boolean isMock = "ON".equals(MockTest);
        if (isMock) {
            return true;
        }

        if (date == null) {
            date = new Date();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);

        // 如果为 周六或者周日 直接返回非交易日
        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
            return false;
        }
        // 获取缓存中的节假日 TODO
        return true;
    }


    /**
     * 判断是否为交易时间
     */
    @Override
    public boolean isBusinessTime(Date date) {
        //判断模拟测试开关是否开启
        boolean isMock = "ON".equals(MockTest);
        if (isMock) {
            return true;
        }

        if (date == null) {
            date = new Date();
        }

        boolean isBusinessDate = isBusinessDate(date);
        if (!isBusinessDate) {
            return false;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour < 9 || hour == 12 || hour > 14) {
            return false;
        }

        int minute = c.get(Calendar.MINUTE);

        return !(hour == 9 && minute < 30 || hour == 11 && minute > 30);
    }











}
