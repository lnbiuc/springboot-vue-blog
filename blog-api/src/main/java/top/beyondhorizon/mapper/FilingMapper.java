package top.beyondhorizon.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.entity.Filing;
import top.beyondhorizon.entity.FilingName;

import java.util.List;

/**
 * ClassName: Filing
 * date: 2022/6/10 00:08
 *
 * @author ayanamirei
 */


@Repository
public interface FilingMapper extends BaseMapper<Filing>
{

    List<Integer> selectFilingId();

    FilingName getFilingNameByArticleId(@Param("articleId") String articleId);

    List<Article> selectUnFilingArticle();
}
