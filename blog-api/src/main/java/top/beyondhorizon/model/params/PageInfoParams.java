package top.beyondhorizon.model.params;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.beyondhorizon.model.vo.ArticleVo;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: PagaInfoParams
 * date: 2022/6/6 22:06
 *
 * @author ayanamirei
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoParams implements Serializable
{
    private List<ArticleVo> articleVos;
    /*
    一共有多少条数据
     */
    private Integer total;
    private Integer pageSize;
    private Integer pageNumber;
    /*
    一共有多少页
     */
    private Integer currentPage;
}
