package com.doinb.reflect;

import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

/**
 * @author dd
 * @description 测试反射类型
 * @createTime 2022/07/28
 */
public class BaseDaoImpl<T> {

    private Class clazz;

    public final String nameSpace;

    public BaseDaoImpl() {

        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();

        this.clazz = (Class) pt.getActualTypeArguments()[0];

        System.out.println("Type为： " + clazz.getSimpleName());

        if (this.getClass().getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class actualTypeArgument = (Class) genericSuperclass.getActualTypeArguments()[0];
            this.nameSpace = actualTypeArgument.getSimpleName();
        } else {
            this.nameSpace = ((Class)((ParameterizedType)this.getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
        }
        System.out.println("nameSpace" + nameSpace);
    }

    public void save() {
        System.out.println("Type为： " + clazz.getSimpleName());
    }
}
