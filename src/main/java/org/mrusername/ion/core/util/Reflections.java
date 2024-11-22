package org.mrusername.ion.core.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Reflections {

    public static Class<?>[] argsToParams(Object... arguments) {
        return Arrays.stream(arguments).map(Object::getClass).toArray(Class<?>[]::new);
    }
    public static <T> Class<T> forName(String name) {
        try {
            return (Class<T>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T newInstance(Class<T> clazz,Object... arguments) {
        try {
            return clazz.getDeclaredConstructor(argsToParams(arguments)).newInstance(arguments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Field getFieldInstance(Object instance,String name) {
        try{
            return instance.getClass().getDeclaredField(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public  static <T> T getFieldValue(Object instance,Field field) {
        try{
            if(Modifier.isStatic(field.getModifiers())){
                return (T) field.get(null);
            } else {
                return (T) field.get(instance);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public  static <T> T getFieldValue(Object instance,String name) {
        try{
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);

            return getFieldValue(instance,field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Method getMethodInstance(Object instance, String name, Class<?>[] types) {
        try{
            return instance.getClass().getDeclaredMethod(name,types);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T getMethodReturnValue(Object instance, String name, Object[] types) {
        try{
            Method method = instance.getClass().getDeclaredMethod(name,argsToParams(types));
            method.setAccessible(true);
            return (T) method.invoke(instance,types);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
