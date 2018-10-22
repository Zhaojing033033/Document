package com._01_outofmemeryerror;

import java.util.ArrayList;

/**
 * DEMO:
 *      方法区或运行时常量池溢出
 * vm参数：
 *      -XX:permSize=10M
 *      -XX:MaxPermSize=10M
 *说明：此代码在jdk1.6版本中会报错.jdk1.7之后则不会报错
 *
 *
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        ArrayList<String> objects = new ArrayList<String>();
        int i=0;
        while (true){
            System.out.println(i);
            objects.add(String.valueOf(i++).intern());
        }
    }
}
