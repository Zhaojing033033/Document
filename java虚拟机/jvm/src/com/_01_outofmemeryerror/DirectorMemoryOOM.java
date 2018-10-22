package com._01_outofmemeryerror;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * DEMO:
 *      本机直接内存溢出
 * vm参数
 *      -Xmx20M JVM最大允许分配的堆内存
 *      -XX:MaxDirectMemorySize=10M 直接内存大小。若不指定，则默认与堆最大容量（ -Xmx20M）一样
 * 报错：
 *      Exception in thread "main" java.lang.OutOfMemoryError
 * 说明：
 *      直接内存溢出的一个特点是，oom之后，dump文件很小
 */
public class DirectorMemoryOOM {
    public static int _1M=1024*1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get(null);
        while (true){
            unsafe.allocateMemory(_1M);
        }
    }
}
