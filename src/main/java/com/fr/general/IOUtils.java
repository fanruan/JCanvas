package com.fr.general;

import com.fr.log.FineLoggerFactory;
import com.fr.stable.ArrayUtils;
import com.fr.stable.StringUtils;
import com.google.common.io.BaseEncoding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author richie
 * @version 10.0
 * Created by richie on 2019-05-21
 */
public class IOUtils {

    private static final int TW_MB = 32 * 1024;

    public static String readResourceAsString(String path) {
        InputStream in = IOUtils.class.getResourceAsStream(path);
        try {
            return inputStream2String(in);
        } catch (UnsupportedEncodingException e) {
            return StringUtils.EMPTY;
        }
    }

    public static String inputStream2String(InputStream is) throws UnsupportedEncodingException {
        return new String(inputStream2Bytes(is), "utf-8");
    }

    public static byte[] inputStream2Bytes(InputStream in) {
        if (in == null) {
            return new byte[0];
        }
        byte[] temp = new byte[TW_MB];
        ByteArrayOutputStream bi = new ByteArrayOutputStream();
        try {
            int count;
            while ((count = in.read(temp)) > 0) {
                byte[] b4Add;
                if (temp.length == count) {
                    b4Add = temp;
                } else {
                    b4Add = ArrayUtils.subarray(temp, 0, count);
                }
                bi.write(b4Add);
            }
        } catch (IOException e) {
            FineLoggerFactory.getLogger().error(e.getMessage(), e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                FineLoggerFactory.getLogger().error(e.getMessage(), e);
            }
        }
        return bi.toByteArray();
    }

    public static byte[] base64Decode(String imageData) {
       return BaseEncoding.base64().decode(imageData);
    }
}
