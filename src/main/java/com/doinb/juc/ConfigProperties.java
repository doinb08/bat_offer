package com.doinb.juc;

import java.util.ResourceBundle;

/**
 * @author doinb
 * init 初始化环境信息 -- 未启用
 */
public class ConfigProperties {

    /**
     * 读取配置信息
     */
    //@JSONField(serialize = false, deserialize = false)
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("configuration");


    /**
     * 获取默认configuration.properties文件中的值，或默认值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value
     */
    // @JSONField(serialize = false, deserialize = false)
    public static String getConfigValueOrDefaultValue(String key, String defaultValue) {
        return resourceBundle.containsKey(key) ? resourceBundle.getString(key) : defaultValue;
    }

}
