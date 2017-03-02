package com.qi.newselect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by dongqi on 2017/3/1.
 */

public class TestProxy {


    public void main() {
        Cls c = new Cls();
        InvocationHandlerTest inv = new InvocationHandlerTest();
        Itf i = (Itf)inv.bind(c);
        i.printMe();
        i.printSth("something");

    }

    //定义接口

    interface Itf{
         void printMe();
         void printSth(String me);
    }

//实现接口

    class Cls implements Itf{
        public void printMe() {
            System.out.println("I'm Cls!");
        }

        public void printSth(String str) {
            System.out.println(str);
        }
    }

//实现代理

    class InvocationHandlerTest implements InvocationHandler {

        private Object target;

        Object bind(Object i) {
            target = i;
            Object warpedItf;
            warpedItf = Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    i.getClass().getInterfaces(), this);
            /*
            　　　　Proxy.newProxyInstance会生成一个  代理类的对象，
                  利用加载器（第一个参数）加载一个该方法自身生成的代理类到虚拟机中，
                  【关于这个加载器是不是必须用系统的加载器我没有测试，但是用系统的加载器不会出错，
                  建议还是不要自定义加载器放在这里。】
                  这个代理类实现了一个接口数组（第二个参数）里面所有的接口的方法，
                  并且按照第三个InvocationHandler参数的规则去实现这些方法。
                  这个例子因为把Proxy放在其自身要使用的InvocationHandler里面所以直接传递this进去就行。

            　　*/

            return warpedItf;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            System.out.println("before method excute!");
            method.invoke(target, args);
            System.out.println("after method excute!");
            return null;
        }
    }
}
