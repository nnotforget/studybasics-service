package com.study.basice.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.basice.constant.HttpStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = -2485537153095465531L;
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "返回内容")
    private String msg;
    @ApiModelProperty(value = "数据对象")
    private T data;

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> RestResult<T> success() {
        return RestResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> RestResult<T> success(T data) {
        return RestResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T> RestResult<T> success(String msg) {
        return RestResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> RestResult<T> success(String msg, T data) {
        return new RestResult<T>(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static <T> RestResult<T> error() {
        return RestResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> RestResult<T> error(String msg) {
        return new RestResult<T>(HttpStatus.ERROR, msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> RestResult<T> error(String msg, T data) {
        return new RestResult<T>(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static <T> RestResult<T> error(int code, String msg) {
        return new RestResult<T>(code, msg, null);
    }


    @JsonIgnore
    @JSONField(serialize = false)
    public Optional<T> getDataForOptional() {
        if (!code.equals(200)) {
            return Optional.empty();
        }

        return Optional.of(data);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public T getDataAndCheck() throws Exception {
        if (!code.equals(200)) {
            String msg = "";

            if (StringUtils.isNotBlank(this.msg)) {
                msg = "：" + this.msg;
            }
            throw new Exception("接口调用异常" + msg);
        }
        return data;
    }
}