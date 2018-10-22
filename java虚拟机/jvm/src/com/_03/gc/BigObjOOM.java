package com._03.gc;

import sun.nio.cs.ext.JIS_X_0212_MS5022X;

/**
 * DEMO:
 *      对象优先分配到eden
 * jvm参数：
 *      -Xms20M   jvm初始化内存大小
 *      -Xmx20M   JVM最大堆内存大小
 *      -Xmn10M    jvm新生代大小
 *      -XX:+PrintGCDetails   gc时打印内存日志
 *      -XX:SurvivorRatio=8   设置eden区和sur区比例
 */

public class BigObjOOM
{
    public  static int _1M=1024*1024;

    public static void main(String[] args) {
        BigObjOOM.testAllocation();
    }
    public static void testAllocation() {
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1=new byte[_1M*2];
        allocation2=new byte[_1M*2];
        allocation3=new byte[_1M*3];
        allocation4=new byte[_1M*4];
    }
}
