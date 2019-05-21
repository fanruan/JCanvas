package com.fr.stable;

import com.google.common.base.Objects;

/**
 * @author richie
 * @version 11.0
 * Created by richie on 2019-05-07
 */
public class AssistUtils {

    /**
     * 判断两个对象是否相等
     *
     * @param source 对象1
     * @param des    对象2
     * @return 如果两个对象相等，则返回true，否则返回false
     */
    public static boolean equals(Object source, Object des) {

        if (source == null && des == null) {
            return true;
        }
        if (source == null) {
            return false;
        }
        if (des == null) {
            return false;
        }
        return source.equals(des);
    }

    /**
     * 返回多个属性合并计算的哈希值
     *
     * @param args 对象列表
     * @return 哈希值
     */
    public static int hashCode(Object... args) {

        return Objects.hashCode(args);
    }
}
