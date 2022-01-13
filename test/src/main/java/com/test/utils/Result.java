package com.test.utils;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Copyright (C), 2019-2020, 中冶赛迪重庆信息技术有限公司
 * Description： 返回结构体
 *
 * @author CISDI
 * @date 2020-12-16
 */
@Data
@ToString
public class Result<T> {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 跟踪ID
     */
    private String traceId;

    /**
     * 导致错误的可能原因
     */
    private String possibleReason;

    /**
     * 建议采取的解决措施
     */
    private String suggestMeasure;

    /**
     * 数据对象
     */
    private T data;

    public Result() {
    }

    public Result(String code, String message, String traceId, String possibleReason, String suggestMeasure, T data) {
        this.code = code;
        this.message = message;
        this.traceId = traceId;
        this.possibleReason = possibleReason;
        this.suggestMeasure = suggestMeasure;
        this.data = data;
    }

    /**
     * 请求成功
     *
     * @param data 返回的数据
     * @param <T>  数据对象
     * @return 请求结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>("0", "请求成功", null, null, null, data);
    }

    /**
     * 请求失败
     * <p>
     * 不要直接使用该方法, 应该在异常映射中使用
     * </p>
     *
     * @param traceId        通过日志追踪处理链
     * @param code           错误码
     * @param message        错误信息
     * @param suggestMeasure 解决方案
     * @param <T>            数据对象
     * @return 请求结果
     */
    public static <T extends Serializable> Result<T> failure(String traceId, String code, String message, String possibleReason, String suggestMeasure) {
        return new Result<>(code, message, traceId, possibleReason, suggestMeasure, null);
    }

}

