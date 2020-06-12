package com.doinb.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * IpRegion IP转区域工具类 使用交给Bean管理
 *
 * @author doinb
 */
@Slf4j
@Component
public class IpRegionUtils {
    DbConfig config = null;
    DbSearcher searcher = null;

    public String getCityByIP(String ip) throws JSONException {
        if(ip.equals("0:0:0:0:0:0:0:1")){
            return "本地";
        }
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + ip + "&co=&resource_id=6006&t=1433920989928&ie=utf8&oe=utf-8&format=json");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            JSONObject jsStr = JSONObject.parseObject(result.toString());
            JSONArray jsData = (JSONArray) jsStr.get("data");
            JSONObject data = (JSONObject) jsData.get(0);
            return (String) data.get("location");
        } catch (IOException e) {
            return "读取失败";
        }
    }

    /**
     * 初始化IP库
     */
    @PostConstruct
    public void init() {
        try {
            // 因为jar无法读取文件,复制创建临时文件 需要编译后启动才能成功读取data/ip2region.db
            String tmpDir = System.getProperty("user.dir") + File.separator + "temp";
            String dbPath = tmpDir + File.separator + "ip2region.db";
            log.info("init ip region db path [{}]", dbPath);
            File file = new File(dbPath);
            InputStream in = IpRegionUtils.class.getClassLoader().getResourceAsStream("data/ip2region.db");
            FileUtils.copyInputStreamToFile(in, file);
            config = new DbConfig();
            searcher = new DbSearcher(config, dbPath);
            log.info("bean [{}]", config);
            log.info("bean [{}]", searcher);
        } catch (Exception e) {
            log.error("init ip region error:{}", e);
        }
    }


    /**
     * 解析IP
     *
     * @param ip
     * @return
     */
    public String getRegion(String ip) {
        try {
            //db
            if (searcher == null) {
                log.error("DbSearcher is null");
                return null;
            }
            long startTime = System.currentTimeMillis();
            //查询算法
            int algorithm = DbSearcher.MEMORY_ALGORITYM;
            //B-tree
            //DbSearcher.BINARY_ALGORITHM //Binary
            //DbSearcher.MEMORY_ALGORITYM //Memory
            //define the method
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }

            if (!Util.isIpAddress(ip)) {
                log.warn("warning: Invalid ip address");
            }
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            String result = dataBlock.getRegion();
            long endTime = System.currentTimeMillis();
            // log.info("region use time[{}ms] result[{}]", endTime - startTime, result);

            return result;
        } catch (Exception e) {
            log.error("getRegion error:", e);
        }
        return null;
    }
}
