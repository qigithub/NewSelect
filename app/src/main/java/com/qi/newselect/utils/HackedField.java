package com.qi.newselect.utils;

import java.lang.reflect.Field;

/**
 * Created by dongqi on 2017/1/10.
 */

public class HackedField<T, FT> {
    private Class<T> type;
    private String name;
    private Field field;

    private Field getField() {
        if (field == null) {
            try {
                field = type.getDeclaredField(name);
                field.setAccessible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return field;
    }

    /**
     *
     * @param type
     *            Type.
     * @param name
     *            Field name.
     */
    public HackedField(Class<T> type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     *
     * @param obj
     *            field owner.
     * @return field value.
     * @since 2.0.0
     */
    @SuppressWarnings("unchecked")
    public FT get(Object obj) {
        try {
            return (FT) getField().get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sets a new field value.
     *
     * @param obj
     *            owner to set field.
     * @param value
     *            new value for field.
     * @return new field value.
     */
    public FT set(Object obj, FT value) {
        try {
            getField().set(obj, value);
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
