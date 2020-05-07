package com.doinb.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ReactiveWebUtils {

    private static final String unknown = "unknown";

    /**
     * 获取IP地址
     *
     * @param request HttpServletRequest
     * @return IP
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteHost();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 0) {
            String[] ips = ip.split(",");
            if (ips.length > 0) {
                ip = ips[0];
            }
        }
        return ip;
    }

    /**
     * 获取IP地址
     *
     * @param request ServerHttpRequest
     * @return IP
     */
    public static String getRemoteAddress(ServerHttpRequest request) {
        Map<String, String> headers = request.getHeaders().toSingleValueMap();
        String ip = headers.get("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.get("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.get("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.get("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.get("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.get("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 0) {
            String[] ips = ip.split(",");
            if (ips.length > 0) {
                ip = ips[0];
            }
        }
        return ip;
    }

    /**
     * 获取IP地址
     *
     * @param exchange ServerWebExchange
     * @return IP
     */
    public static String getRemoteAddress(ServerWebExchange exchange) {
        return getRemoteAddress(exchange.getRequest());
    }
}
