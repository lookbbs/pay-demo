package com.ydf.pay;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuandongfei
 * @date 2019/6/3
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("appid1", "");
        map.put("appid2", "2");
        map.put("appid3", "3");
        map.put("appid4", "4");
        map.put("trade_type", "APP");


        map.forEach((k, v) -> {
            if(StringUtils.isBlank(v)){
                map.remove(k);
            }
        });
        System.out.println(map);
    }
}
