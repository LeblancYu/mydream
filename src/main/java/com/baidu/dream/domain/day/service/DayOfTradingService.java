package com.baidu.dream.domain.day.service;

import java.util.Date;

/**
 * @ClassName DayOfTradingServiceImpl
 * @Description  交易日、交易时间处理
 * @Author wangjiangyu
 * @Date 2022/3/14 21:57
 */
public interface DayOfTradingService {


    /**
     * 获取节假日并缓存
     */
    void getHoliday();

    /**
     * 判断是否为交易日
     */
    boolean isBusinessDate(Date date);

    /**
     * 判断是否为交易时间
     */
    boolean isBusinessTime(Date date);










}
