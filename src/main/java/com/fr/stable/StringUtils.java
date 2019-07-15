package com.fr.stable;

/**
 * @author richie
 * @version 1.0
 * Created by richie on 2019-05-07
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(String str) {

        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
