package com.qi.newselect.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by dongqi on 2017/1/10.
 */

public class HackedClass<C> {
    protected Class<C> mClass;

    public HackedClass(final Class<C> clazz) {
        mClass = clazz;
    }

//    public HackedField<C, Object> staticField(final String name) throws HackDeclaration.HackAssertionException {
//        return new HackedField<C, Object>(mClass, name, Modifier.STATIC);
//    }
//
//    public HackedField<C, Object> field(final String name) throws HackDeclaration.HackAssertionException {
//        return new HackedField<C, Object>(mClass, name, 0);
//    }
//
//    public HackedMethod staticMethod(final String name, final Class<?>... arg_types) throws HackDeclaration.HackAssertionException {
//        return new HackedMethod(mClass, name, arg_types, Modifier.STATIC);
//    }
//
//    public HackedMethod method(final String name, final Class<?>... arg_types) throws HackDeclaration.HackAssertionException {
//        return new HackedMethod(mClass, name, arg_types, 0);
//    }
//
//    public HackedConstructor constructor(final Class<?>... arg_types) throws HackDeclaration.HackAssertionException {
//        return new HackedConstructor(mClass, arg_types);
//    }

    public Class<C> getmClass() {
        return mClass;
    }

}
