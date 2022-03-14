package com.baidu.dream.domain.data.service.impl;

import com.baidu.dream.base.util.StockUtil;
import com.baidu.dream.domain.data.entity.StockInfo;
import com.baidu.dream.domain.data.service.StockInfoParser;
import com.baidu.dream.domain.data.service.StockInfoService;
import org.apache.commons.codec.binary.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName  StockInfoServiceImpl
 * @Description 股票信息实现类
 * @Author wangjiangyu
 * @Date 2022/3/14 22:45
 */
public class StockInfoServiceImpl implements StockInfoService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoParser stockInfoParser;



    @Override
    public List<StockInfo> getStockList() {
        ArrayList<StockInfo> list = new ArrayList<>();
        list.addAll(getStockList("m:0+t:6,m:0+t:13,m:0+t:81+s:2048,m:1+t:2,m:1+t:23,b:MK0021,b:MK0022,b:MK0023,b:MK0024"));
        return list;
    }

    private List<StockInfo> getStockList(String fs) {
        /*String content = HttpUtil.sendGet(httpClient, "http://20.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=10000000&np=1&fid=f3&fields=f12,f13,f14&fs=" + fs);
        if (content != null) {
            List<StockInfo> list = stockInfoParser.parseStockInfoList(content);
            list = list.stream().filter(v -> v.getExchange() != null).collect(Collectors.toList());
            list.forEach(stockInfo -> stockInfo.setAbbreviation(StockUtil.getPinyin(stockInfo.getName())));
            return list;
        }
        return Collections.emptyList();*/
        String content = restTemplate.getForObject("http://20.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=10000000&np=1&fid=f3&fields=f12,f13,f14&fs=", String.class);

        if(!StringUtil.isBlank(content)){
            List<StockInfo> list = stockInfoParser.parseStockInfoList(content);
            list.forEach(stockInfo -> stockInfo.setAbbreviation(StockUtil.getPinyin(stockInfo.getName())));
            return list;
        }

        return Collections.emptyList();
    }
}