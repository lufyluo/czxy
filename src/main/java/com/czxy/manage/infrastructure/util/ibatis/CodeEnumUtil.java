package com.czxy.manage.infrastructure.util.ibatis;


import com.czxy.manage.model.enums.BaseEnum;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-8-16 下午3:00
 */
public class CodeEnumUtil {
    public static <E extends Enum<?> & BaseEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getValue() == code) {
                return e;
            }
        }
        return null;
    }
}
