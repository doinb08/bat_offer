package com.doinb.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    public static void main(String[] args) {
        List<String> addressList = Arrays.asList("湖北省武汉市洪山区", "湖北省恩施土家族苗族自治州恩施市",
            "北京市市辖区朝阳区", "内蒙古自治区兴安盟科尔沁右翼前旗", "西藏自治区日喀则地区日喀则市",
            "湖北省武汉市洪山区", "海南省三亚市吉阳区荔枝沟路", "香港特别行政区麻油地区",
            "海南省海口市老城高新技术产业示范区海南生态软件园A18幢三层201",
            "贵州省贵阳市南明区花果园E区福家乐超市","中国海南省省直辖县级行政单位中沙群岛的岛礁及其海域");
        //System.out.println(addressResolution("儋州市"));

//        String include = "贵州省贵阳市南明区花果园E区福家乐超市海南省儋州市";
//        System.out.println(include.contains("儋州"));

        long starTime = new Date().getTime();
//        System.out.println(compareProvince("68768", "北京市市辖区朝阳区"));
        long eTime = new Date().getTime();
        System.out.println("花费时间：" + (eTime - starTime));
    }


    /**
     * 国家注册数据库可能会返回地址简写如："Doctor_AreaName": "儋州市",
     *
     * @param originAddress 原始地址 兼容 "儋州市"，"三亚市"等写法
     * @param newAddress 新地址 兼容 "儋州市"，"三亚市"等写法
     * @return 返回 地址省份的比较结果 boolean
     */
    public static boolean compareProvince(String originAddress, String newAddress){
        if (newAddress.length() <= 3 || originAddress.length() <= 3){
            return originAddress.contains(newAddress);
        }

        List<Map<String, String>> originResolution = addressResolution(originAddress);
        List<Map<String, String>> newResolution = addressResolution(newAddress);
        String originProvince;
        String newProvince = "";
        if (originResolution.size() > 0){
            originProvince = originResolution.get(0).get("province");
        } else {
            return false;
        }
        if (newResolution.size() > 0){
            newProvince = newResolution.get(0).get("province");
        }
        return originProvince.equals(newProvince);
    }


    /**
     * 解析地址 要求是规范地址
     *
     * @param address 地址字符串
     * @return map
     */
    public static List<Map<String, String>> addressResolution(String address) {
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String province = null, city = null, county = null, town = null, village = null;
        List<Map<String, String>> table = new CopyOnWriteArrayList<>();
        Map<String, String> row = null;
        while (m.find()) {
            row = new ConcurrentHashMap<>();
            province = m.group("province");
            row.put("province", province == null ? "" : province.trim());
            city = m.group("city");
            row.put("city", city == null ? "" : city.trim());
            county = m.group("county");
            row.put("county", county == null ? "" : county.trim());
            town = m.group("town");
            row.put("town", town == null ? "" : town.trim());
            village = m.group("village");
            row.put("village", village == null ? "" : village.trim());
            table.add(row);
        }
        return table;
    }

}
