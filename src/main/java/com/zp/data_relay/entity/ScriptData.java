/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.zp.data_relay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * web shell数据传输
 *
 * @author zmzhou
 * @version 1.0
 * @title WebSSHData
 * @date 2021/2/23 20:57
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScriptData implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2326528171211907216L;
    /**
     * 操作类型
     */
//    private String op;
    private Object data;
//    private String toSessionId;

    private Header header;


    //@Slf4j
    @Data
    //@AllArgsConstructor
    //@NoArgsConstructor
    public class Header {
        private String op;
        private String toSessionId;
    }
}


