package top.beyondhorizon.model.params;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: PublishArticle
 * date: 2022/6/4 02:38
 *
 * @author ayanamirei
 */

@Data
public class PublishParams implements Serializable
{
    private String articleId;

    private String title;

    private String introduction;

    private String content;

    private List<String> tag;

    private String filingName;

    private String bgImg;
}
