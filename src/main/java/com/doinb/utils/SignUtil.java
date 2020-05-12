package com.doinb.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Arrays;

public class SignUtil {

    private static final Logger log = LoggerFactory.getLogger(SignUtil.class);

    public static void validateParams(String nonce, String sign, String timeStamp, String clientId) {
        try {
            Assert.notNull(nonce, "签名验证失败:NONCE不能为空");
            Assert.notNull(sign, "签名验证失败:SING不能为空");
            Assert.notNull(timeStamp, "签名验证失败:TIMESTAMP不能为空");
            Assert.notNull(clientId, "签名验证失败:CLIENT_ID不能为空");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * @Description: 获取签名字符串
     */
    public static String getSign(String nonce, String secret, String timeStamp) {
        StringBuilder sb = new StringBuilder();
        // 拼接字符串
        sb.append("nonce").append(nonce).append(secret).append(timeStamp);
        // 字符排序
        char[] sortChars = sb.toString().toCharArray();
        Arrays.sort(sortChars);
        String encodeStr = String.valueOf(sortChars);
        // 获取md5值 	<groupId>commons-codec</groupId>
        String signStr = DigestUtils.md5Hex(encodeStr).toLowerCase();
        return signStr;
    }

    public static void main(String[] args) {
        String sbf = "ihdfegpkjalbc";
        char[] sortChars = sbf.toCharArray();
        Arrays.parallelSort(sortChars);
        System.out.println(sortChars);

    }
}
