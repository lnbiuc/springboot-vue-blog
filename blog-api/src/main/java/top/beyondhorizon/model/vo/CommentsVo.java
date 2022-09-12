package top.beyondhorizon.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName: CommentsVo
 * date: 2022/6/4 00:29
 *
 * @author ayanamirei
 */

@Data
public class CommentsVo implements Serializable
{
    private Integer id;
    private UserVo author;
    private String content;
    private List<CommentsVo> children;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createDate;
    private Integer level;
    private UserVo toUser;
}
