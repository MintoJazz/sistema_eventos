package util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Reflexao {
    @SuppressWarnings("unchecked") public static <T> Class<T> getGenericTypeArgument(Class<?> clazz, int index) {
        Type superclass = clazz.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) return (Class<T>) ((ParameterizedType) superclass).getActualTypeArguments()[index];
        
        throw new IllegalArgumentException("A classe " + clazz.getName() + " não tem parâmetros genéricos diretos.");
    }
}