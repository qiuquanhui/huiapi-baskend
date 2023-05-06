package com.quanhui.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * id请求用于发布，下线接口
 *
 * @author quanhui
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}