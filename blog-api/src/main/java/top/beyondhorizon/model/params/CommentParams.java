package top.beyondhorizon.model.params;


import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: CommentParams
 * date: 2022/6/4 01:24
 *
 * @author ayanamirei
 */

@Data
public class CommentParams implements Serializable
{
    private String articleId;
    private String content;
    private Integer parent;
    private String toUserId;
}
