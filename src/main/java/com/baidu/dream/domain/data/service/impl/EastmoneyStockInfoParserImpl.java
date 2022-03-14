package com.baidu.dream.domain.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.baidu.dream.base.constants.StockConsts;
import com.baidu.dream.base.util.StockUtil;
import com.baidu.dream.domain.data.entity.StockInfo;
import com.baidu.dream.domain.data.service.StockInfoParser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("eastmoneyStockInfoParser")
public class EastmoneyStockInfoParserImpl implements StockInfoParser {

    // http://quote.eastmoney.com/center/gridlist.html#kcb_board

    @Override
    public List<StockInfo> parseStockInfoList(String content) {
        char[] chArr = content.toCharArray();
        char[] newCharArr = new char[chArr.length];
        int i = 0;
        for (char ch : chArr) {
            if (ch == ' ') {
                continue;
            }
            if (ch == 'Ａ') {
                ch = 'A';
            } else if (ch == 'Ｂ') {
                ch = 'B';
            }
            newCharArr[i++] = ch;
        }

        StockResultVo stockResultVo = JSON.parseObject(new String(newCharArr, 0, i), StockResultVo.class);

        // {"f12":"000718","f13":0,"f14":"苏宁环球"}
        return stockResultVo.getData().getDiff().stream().map(v -> {
            String code = v.getF12();

            StockInfo stockInfo = new StockInfo();
            String exchange = v.getF13() == 0 ? StockUtil.getExchange(code) : StockConsts.Exchange.SH.getName();
            int type = StockUtil.getStockType(exchange, code);
            stockInfo.setExchange(exchange);
            stockInfo.setName(v.getF14());
            stockInfo.setCode(code);
            stockInfo.setExchange(exchange);
            stockInfo.setType(type);
            return stockInfo;
        }).collect(Collectors.toList());
    }

    public static class StockResultVo {

        private StockResultDataVo data;

        public StockResultDataVo getData() {
            return data;
        }

        public void setData(StockResultDataVo data) {
            this.data = data;
        }
    }

    public static class StockResultDataVo {

        private List<StockResultDiffVo> diff;

        public List<StockResultDiffVo> getDiff() {
            return diff;
        }

        public void setDiff(List<StockResultDiffVo> diff) {
            this.diff = diff;
        }

    }

    public static class StockResultDiffVo {

        private String f12;
        private int f13;
        private String f14;

        public String getF12() {
            return f12;
        }

        public void setF12(String f12) {
            this.f12 = f12;
        }

        public int getF13() {
            return f13;
        }

        public void setF13(int f13) {
            this.f13 = f13;
        }

        public String getF14() {
            return f14;
        }

        public void setF14(String f14) {
            this.f14 = f14;
        }
    }

}
