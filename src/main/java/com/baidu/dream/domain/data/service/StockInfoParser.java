package com.baidu.dream.domain.data.service;


import com.baidu.dream.domain.data.entity.StockInfo;

import java.util.List;

public interface StockInfoParser {

    List<StockInfo> parseStockInfoList(String content);

}
