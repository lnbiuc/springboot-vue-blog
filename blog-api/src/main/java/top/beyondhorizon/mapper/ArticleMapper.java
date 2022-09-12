package top.beyondhorizon.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.model.dto.DataYearMonth;
import top.beyondhorizon.model.params.PageParams;

import java.util.List;

/**
 * ClassName: ArticleMapper
 * date: 2022/5/30 18:21
 *
 * @author ayanamirei
 */

@Repository
public interface ArticleMapper extends BaseMapper<Article>
{
    List<DataYearMonth> selectAllGroupByTime();

    List<Article> selectAllByMonth(@Param("date") DataYearMonth dataYearMonth);

    Integer countTotal();

    Integer likeAdd(String articleId);

    List<Article> selectArticleByTitle(String title);

    List<Article> selectDeletedArticleList();

    int recoverArticle(String articleId);
}
