package top.beyondhorizon.model.params;


import lombok.AllArgsConstructor;
import lombok.Data;
import top.beyondhorizon.model.dto.DataYearMonth;
import top.beyondhorizon.model.vo.ArticleVo;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: TimeLineParams
 * date: 2022/6/15 16:36
 *
 * @author ayanamirei
 */

@Data
@AllArgsConstructor
public class TimeLineParams implements Serializable
{
    private DataYearMonth timeStrings;
    private List<ArticleVo> articleVos;
}
