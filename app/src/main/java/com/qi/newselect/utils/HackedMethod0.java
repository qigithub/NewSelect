package com.qi.newselect.utils;

import java.lang.reflect.Method;

/**
 * Created by dongqi on 2017/1/10.
 */

public class HackedMethod0<T, R> {
    private Class<T> type;
    private String name;
    private Method method;

    /**
     *
     * @param type
     *            Declaring class of method.
     * @param name
     *            method name.
     */
    public HackedMethod0(Class<T> type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     *
     * @return Java reflection {@link Method}.
     */
    public Method getMethod() {
        if (method == null) {
            try {
                method = type.getDeclaredMethod(name);
                method.setAccessible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        }
        return method;
    }

    /**
     * Invokes method.
     *
     * @param object
     *            instance to invoke method.
     * @return result of method invocation.
     */
    @SuppressWarnings("unchecked")
    public R invoke(T object) {
        try {
            return (R) getMethod().invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
