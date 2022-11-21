package com.doinb.web.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    public static final int CODE_SUCCESS = 200;

    private static final int CODE_NO_LOGIN = 300;

    public static final int CODE_UNAUTHORIZED = 401;

    private static final int CODE_FAIL = 5050;

    private static final int CODE_ERROR = 500;

    public static final int CODE_FAILURE = 1010;

    public static final int CODE_FAILURE_NULL = 1011;

    private int code;

    private String message;

    private T data;

    public BaseResponse() {
        this.setData(null);
        this.setCode(CODE_SUCCESS);
        this.setMessage("success");
    }

    public BaseResponse(T data) {
        this.setData(data);
        this.setCode(CODE_SUCCESS);
        this.setMessage("success");
    }

    public BaseResponse(int code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public static <T> BaseResponse<T> failure() {
        return new BaseResponse<T>(CODE_FAILURE, "failure", null);
    }

    public static <T> BaseResponse<T> failure(String message) {
        return new BaseResponse<T>(CODE_FAILURE, message, null);
    }

    public static <T> BaseResponse<T> failure(String message, T data) {
        return new BaseResponse<T>(CODE_FAILURE, message, data);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<T>(CODE_SUCCESS, "success", null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(CODE_SUCCESS, "success", data);
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<T>(CODE_SUCCESS, message, data);
    }

    public static <T> BaseResponse<T> error() {
        return new BaseResponse<T>(CODE_ERROR, "fail", null);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<T>(CODE_ERROR, message, null);
    }

    public static <T> BaseResponse<T> error(T data) {
        return new BaseResponse<T>(CODE_ERROR, "fail", data);
    }

    public static <T> BaseResponse<T> error(String message, T data) {
        return new BaseResponse<T>(CODE_ERROR, message, data);
    }

    public static <T> BaseResponse<T> badRequest() {
        return new BaseResponse<T>(CODE_FAIL, "no identifier arguments", null);
    }

    public static <T> BaseResponse<T> badRequest(String message) {
        return new BaseResponse<T>(CODE_FAIL, message, null);
    }

    public static <T> BaseResponse<T> badRequest(int code, String message, T data) {
        return new BaseResponse<T>(code, message, data);
    }

    public static <T> BaseResponse<T> badRequest(int code, String message) {
        return new BaseResponse<T>(code, message, null);
    }

    public static <T> BaseResponse<T> badRequest(String message, T data) {
        return new BaseResponse<T>(CODE_FAIL, message, data);
    }

    public static <T> BaseResponse<T> noLogin(String message) {
        return new BaseResponse<T>(CODE_NO_LOGIN, message, null);
    }

}
