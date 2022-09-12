package top.beyondhorizon.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: ArticleFilingVo
 * date: 2022/6/10 01:03
 *
 * @author ayanamirei
 */

@Data
public class ArticleFilingVo implements Serializable
{
    private String title;

    private String articleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date releaseTime;

    private String introduction;

    private String bgImg;
}
