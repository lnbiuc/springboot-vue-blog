package top.beyondhorizon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.mapper.ArticleMapper;
import top.beyondhorizon.mapper.TagNameMapper;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.CountParams;
import top.beyondhorizon.service.CountService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ayanamirei
 * @ProjectName: MyBlog
 * @ClassName: CountServiceImpl
 * @create 2022-06-24 22:48
 */

@Service
@Slf4j
public class CountServiceImpl implements CountService {
    private final ArticleMapper articleMapper;
    
    private final TagNameMapper tagNameMapper;
    
    
    public CountServiceImpl(ArticleMapper articleMapper, TagNameMapper tagNameMapper) {
        this.articleMapper = articleMapper;
        this.tagNameMapper = tagNameMapper;
    }
    
    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException calendar 对日期进行时间操作
     *                        getTimeInMillis() 获取日期的毫秒显示形式
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    
    @Override
    public CountParams count() {
        CountParams params = new CountParams();
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Article::getDeleted, true);
        Integer totalBlogs = Math.toIntExact(articleMapper.selectCount(queryWrapper));
        Integer time = getTimeCount();
        Integer totalTags = Math.toIntExact(tagNameMapper.selectCount(null));
        Integer views = articleMapper.countTotal();
        params.setTotalBlogs(totalBlogs);
        params.setExistTime(time);
        params.setTotalTags(totalTags);
        params.setTotalViews(views);
        return params;
    }
    
    private Integer getTimeCount() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = new Date();
        Date smdate = null;
        try {
            smdate = s.parse("2022-05-06");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            return daysBetween(smdate, bdate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
