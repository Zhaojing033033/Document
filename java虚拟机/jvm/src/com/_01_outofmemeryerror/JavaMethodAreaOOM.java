package com._01_outofmemeryerror;

/**
 * DEMO:
 *      方法区或运行时常量池溢出
 * vm参数：
 *      -XX:permSize=10M
 *      -XX:MaxPermSize=10M
 * 报错：
 *
 *说明：使用enhancer在方法去不断生成新的类，填满方法区。可报错
 *
 *
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {


    }


}
