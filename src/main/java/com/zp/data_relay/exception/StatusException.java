package com.zp.data_relay.exception;

import lombok.Data;

/**
 * @description: 消息自定义异常
 * @author:
 * @date: 2019/9/13 17:21
 */
@Data
public class StatusException extends Exception {

    public Long status;

    public StatusException(Long status) {
        status = status;
    }

    public StatusException(Long status, String message) {
        super(message);
        status = status;
    }

}
