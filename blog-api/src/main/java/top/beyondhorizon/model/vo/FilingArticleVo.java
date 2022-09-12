package top.beyondhorizon.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: FilingVo
 * date: 2022/6/10 01:01
 *
 * @author ayanamirei
 */

@Data
@AllArgsConstructor
public class FilingArticleVo implements Serializable
{
    private String filingName;
    
    private Integer filingId;
    
    private List<ArticleFilingVo> articles;
}
