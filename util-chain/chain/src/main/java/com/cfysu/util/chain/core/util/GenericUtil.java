package com.cfysu.util.chain.core.util;

import org.springframework.core.ResolvableType;

/**
 * @Author canglong
 * @Date 2020/6/4
 */
public class GenericUtil {

    public static ResolvableType resolveDeclaredGenericType(Class<?> concreteClazz, Class<?> parentClazz) {
        ResolvableType resolvableType = ResolvableType.forClass(concreteClazz).as(parentClazz);
        if (resolvableType == null || !resolvableType.hasGenerics()) {
            return null;
        }
        return resolvableType.getGeneric();
    }

    public static String resolveDeclaredGenericTypeName(Class<?> concreteClazz, Class<?> parentClazz){
        ResolvableType resolvableType = resolveDeclaredGenericType(concreteClazz, parentClazz);
        if(resolvableType == null || resolvableType.getType() == null){
            return null;
        }
        return resolvableType.getType().getTypeName();
    }
}
