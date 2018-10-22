package com._01_outofmemeryerror;

/**
 * DEMO：
 *      虚拟机栈内存溢出
 *      对象数量达到最大堆的容量内存限制，会产生内存溢出异常
 * vm参数：
 *      -Xss128k：设置每个线程的堆栈大小
 * 报错：
 *      Exception in thread "main" java.lang.StackOverflowError
 */
public class VMStackSOF {
    private int stackLength=1;
    public void stackLeak(){
        System.out.println("stack length:"+stackLength);
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        VMStackSOF vmStackSOF = new VMStackSOF();
        try {
            vmStackSOF.stackLeak();
        } catch (Exception e) {
            System.out.println("stack length:"+vmStackSOF.stackLength);
            throw  e;
        }
    }
}
