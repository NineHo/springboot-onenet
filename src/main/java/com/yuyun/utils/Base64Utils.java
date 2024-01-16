package com.yuyun.utils;

import java.util.Base64;

import java.io.IOException;

/**
 * Base64工具类
 *
 * @author hyh
 */
public class Base64Utils {

    /**
     * Base64解码
     *
     * @param bytes Base64加密的字节码
     * @return String
     * @throws IOException IOException
     */
    public static String Base64Decode(byte[] bytes)
            throws IOException {
        byte[] bs = Base64.getDecoder().decode(bytes);
        return new String(bs, "UTF-8");
    }

}
