package top.beyondhorizon.model.params;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: EditParams
 * date: 2022/6/8 22:18
 *
 * @author ayanamirei
 */


@Data
public class EditParams implements Serializable
{
    private String articleId;
    private String title;
    private String introduction;
    private String content;
    private List<String> tag;
    private String filingName;
    private String bgImg;
}
