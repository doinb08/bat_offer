package com.doinb;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.doinb.reflect.TestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author d
 * @description TODO
 * @createTime 2022/06/09
 */
public class WorkTest {

    @Autowired
    Map<String, TestService<Object>> cancelHandleServiceMap = new ConcurrentHashMap<>(3);


    public static void main(String[] args) {
        try {
            System.out.println("输入参数");
            System.out.println("请求失败");
            throw new RuntimeException("异常了");
        } finally {
            System.out.println("执行finally");
        }
    }


    /**
     * 使用hutool工具
     *
     * @param fileUrl 文件路径
     * @return 数据内容
     */
    public static List<List<Object>> readerExcel(String fileUrl) {
        List<List<Object>> readAll = CollUtil.newArrayList();
        try {
            URL url = new URL(fileUrl);
            InputStream inputStream = url.openConnection().getInputStream();
            ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(inputStream);
            readAll = reader.read();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
//        BigExcelWriter bigWriter = ExcelUtil.getBigWriter("", "");
//        ExcelWriter write = bigWriter.write(CollUtil.newArrayList());


        return readAll;
    }

    /**
     * 已存在的货号
     */
    private static final LRUCache<String, String> duplicateCodeLocalCache = new LRUCache<>(10000);

    private static boolean ifFirstExists(String materialCode) {
        if (duplicateCodeLocalCache.containsKey(materialCode)) {
            if (StrUtil.equals(duplicateCodeLocalCache.get(materialCode), materialCode)) {
                // 重复货号只处理一次
                duplicateCodeLocalCache.remove(materialCode);
                return true;
            }
        }
        return false;
    }

}
