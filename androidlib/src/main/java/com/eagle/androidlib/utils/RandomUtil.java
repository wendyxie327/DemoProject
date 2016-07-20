package com.eagle.androidlib.utils;

/**
 * Created by Administrator on 2016/4/21 0021.
 */
public class RandomUtil {

    /**
     * 取当前时间的毫秒后八位，作为八位随机码
     * @return
     */
    public static int currentTimeMillisRandom(){
        return (int) (System.currentTimeMillis()%100000000);
    }
}
