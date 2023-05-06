package com.quanhui.project.model.dto.post;

import lombok.Data;

import java.io.Serializable;

/**
 * 点赞 / 取消点赞请求
 *
 * @author quanhui
 */
@Data
public class PostDoThumbRequest implements Serializable {

    /**
     * 帖子 id
     */
    private long postId;

    private static final long serialVersionUID = 1L;
}