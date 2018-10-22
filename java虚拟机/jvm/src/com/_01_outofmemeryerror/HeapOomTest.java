package com._01_outofmemeryerror;

import java.util.ArrayList;

/**
 *
 * DEMO:
 *      java堆溢出
 *      如果线程请求的栈深度大于虚拟机所允许的最大深度，抛出StackOverFlowError
 *      如果虚拟机在扩展时无法申请到足够的内存空间，则抛出OutOfMemoryError
 *JVM参数：
 *      -Xms20m JVM初始分配的堆内存
 *      -Xmx20m JVM最大允许分配的堆内存
 *      -XX:+HeapDumpOnOutOfMemoryError 出现内存溢出时，dump当前内存堆的存储快照。以便事后分析
 *      -XX:HeapDumpPath=d:/heapdump.hpro f指定快照存储路径
 * 报错：
 *      Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class HeapOomTest {
    static class OOMObject{}
    public static void main(String[] args) {
        ArrayList<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }
}
