package com.doinb;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.alibaba.fastjson.JSON;
import com.doinb.reflect.TestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
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
        WorkTest workTest = new WorkTest();
        Map<String, TestService<Object>> cancelHandleServiceMap1 = workTest.cancelHandleServiceMap;

        System.out.println("cancelHandleServiceMap1" + cancelHandleServiceMap1);


//        DateTime endTime = DateUtil.endOfDay(DateUtil.parse("20220720", DatePattern.PURE_DATE_PATTERN));
//
//        DateTime lastEnd = DateUtil.parse("20220720", DatePattern.PURE_DATE_PATTERN);
//
//        // 需满足：填写的结束日期≤当前批次生效结束日期
//        Date endExpiry = new Date();
//
//        String format = DateUtil.format(endExpiry, DatePattern.PURE_DATE_PATTERN);
//
//        DateTime endExpiryDateTime = DateUtil.parse(format, DatePattern.PURE_DATE_PATTERN);
//
//        if (lastEnd.isAfter(endExpiryDateTime)) {
//            System.out.println("OK");
//        }
//
//        String url = "https://e1xossfilehdd.blob.core.chinacloudapi.cn/fileserver01/2022/07/21/4a2b7184-8201-4d8d-a760-19718e2ba586.xlsx";
//        List<List<Object>> lists = readerExcel(url);
//        for (int i = 1; i < lists.size(); i++) {
//            List<Object> objects = lists.get(i);
//            System.out.println("j0=" + objects.get(0));
//            System.out.println("j1=" + objects.get(1));
//        }

//
//        Integer integer = Convert.toInt(null);
//        System.out.println(integer);


        String[] split = StrUtil.split("220-213123", "-");
        String sss = Arrays.stream(split).limit(1).findFirst().orElse("");
        System.out.println("sss " + sss);
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


    private static void sblit() {
        HashMap<String, String> attributeMap = MapUtil.newHashMap();
        String attribute = ";bizCode:tmall.general.refund;disputeRequest:1;leavesCat:50013228;7d:1;apply_reason_text:订单信息拍错（规格/尺码/颜色等）;itemBuyAmount:1;interceptItemListResult:[{\"subBizOrderId\"#3B2728605998612085556,\"logisticInterceptEnum\"#3B\"INTERCEPT_NOT_APPLY\"}];seller_batch:true;clj_zero_second_refund:1;sku:5027264902039|颜色分类#3B584213-11/主图款#3A尺码#3B128;sgr:0;bgmtc:2022-06-29 12#3B44#3B42;agreeSource:2h;shop_name:胜道官方outlets店;ttid:201200@taobao_iphone_10.12.20;sync:0;rp3:1;disputeTradeStatus:4;sars:skip;isVirtual:0;EXmrf:6618;enfunddetail:1;pay_lock:system;tod:86400000;newRefund:rp2;opRole:daemon;newUltron:3;products:timeoutrefund^|autorefund^;apply_init_refund_fee:6618;apply_text_id:500021;userCredit:6;sdkCode:ali.china.tmall;b2c:1;interceptStatus:0;twoHoursAutoRefund:1;rootCat:50011699;tos:3;ol_tf:6618;part_refund:0;newAutoRefund:500元;autoAgree:1;payMode:alipay;appName:rtee;fps:4;workflowName:refund;seller_audit:0;itemPrice:16900;toSellerFee:0;";

        String[] split = StrUtil.split(attribute, ";");
        for (String s : split) {
//            System.out.println("split data =  " + s);
            String[] split2 = StrUtil.split(s, ":");
            if (split2.length > 0) {
                attributeMap.put(split2[0], split2[1]);
            }
        }

        if (attributeMap.containsKey("newAutoRefund")) {
            System.out.println("newAutoRefund=" + attributeMap.get("newAutoRefund"));
        }

        System.out.println("attributeMap=" + JSON.toJSONString(attributeMap));
        ;


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
