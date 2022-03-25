/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @Date 2022/03/25
 */
public class Utils {

    /**
     * 打印指定字符指定次数
     * @param number
     * @param target
     */
    public static void printString(int number,String target){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < number; i++) {
            stringBuffer.append(target);
        }
        System.out.println("\t" + stringBuffer);
    }

}
