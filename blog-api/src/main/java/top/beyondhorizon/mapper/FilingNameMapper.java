package top.beyondhorizon.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.entity.FilingName;

import java.util.List;

/**
 * ClassName: FilingName
 * date: 2022/6/10 00:09
 *
 * @author ayanamirei
 */

@Repository
public interface FilingNameMapper extends BaseMapper<FilingName>
{
    
    List<Article> selectArticleByFilingName(String filingName);
    
    
    List<Article> selectArticleByFilingId(Integer filingId);
}
