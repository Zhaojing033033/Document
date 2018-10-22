package com.other;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        IHellow bind = (IHellow) new HellowProxy().bind();
        bind.sayBye();
        bind.sayHellow();
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles",true);//磁盘会生成代理来的字节文件（class文件）
    }

}

interface IHellow{
    void sayHellow();
    void sayBye();
}

class HellowProxy {

    Object object;

    public HellowProxy() {
        this.object = new Object();
    }

    public Object bind() {
        return Proxy.newProxyInstance(HellowProxy.class.getClassLoader(), new Class[]{IHellow.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.getName().equals("sayHellow")){
                    System.out.println("hellow world");
                }
                if(method.getName().equals("sayBye")){
                    System.out.println("good bye!");
                }
                return null;
            }
        });
    }

}
