package com._02.finalize;

/**
 * 对象什么时候应该被回收：
 *      一个对象没有任何地方引用他，则该对象应该被回收了
 * 如何判定有没有对象引用；
 *      引用计算算法：
 *          每个对象都有一个引用计数器。每有一个地方引用它，计数器加一。每有一个引用失效，计数器减一。当计数器为0.则判定回收。
 *          缺点：无法判定，两个对象互相引用的的情况
 *      可达性分析算法：
 *          通过计算一个对象到GC Roots对象是否可达，不可达，则判定回收。
 *          GC Roots对象：1虚拟机栈引用的对象，2类静态熟悉引用的对象，3类常量引用的对象均是GC Roots对象
 * 对象回收过程：
 *       一个对象，需要进行两个标记才可能被gc掉。
 *       对象被可达性分析判断为不可达，那么它将被第一次标记，并且进行筛选。筛选条件是：
 *       覆盖了finalize（）方法，并且finalize方法未执行（一个对象finalize方法执行一次），
 *       其满足这个要求的对象，将被放在F-QUEUE的队列中，由虚拟机在稍后出创建一个低优先级的线程来触发finalize方法
 *       这次执行finalize方法，对象可以自救（只要将自己this赋值给某个类变量）。
 *       如若finalize方法中自救失败，则将被回收，自救成功，则仍可存活。
 * 方法区回收：
 *      主要回收：废弃常量和无用的类
 *      回收条件：1该类所有实例被回收；2加载该类的ClassLoader被回收；
 *                3该类的java.lang.Class对象没有任何地方引用，无法在任何地方通过反射引用该类的方法
 *      相关jvm参数：
 *          -Xnoclassgc  控制是否对类进行回收
 *          -verbese:class -XX:+TraceClassLOading  -XX:+TraceClassUnLoading 查看类加载和卸载信息
 *        vm参数使用情景:
 *        在大量使用反射、动态代理、CGLib等ByteCode框架、动态生成JSP以及OSGi这类频繁定义ClassLOader的场景
 *
 */
public class GCSaveTest {
    public static GCSaveTest obj=null;
    public void isAlive(){
        System.out.println("I am alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method exected :)");
        GCSaveTest.obj=this;
    }

    public static void main(String[] args) throws InterruptedException {
        obj=new GCSaveTest();
        obj=null;
        System.gc();
        //因为finalize方法优先级很低，所以暂停0.5秒
        Thread.sleep(1000);
        if (obj != null) {
            obj.isAlive();
        }else{
            System.out.println("no,I am died :(");
        }

        //下面代码与上面，完全一样，但是自救失败了
        obj=null;
        System.gc();
        Thread.sleep(1000);
        if (obj != null) {
            obj.isAlive();
        }else{
            System.out.println("no,I am died :(");
        }
    }
}
