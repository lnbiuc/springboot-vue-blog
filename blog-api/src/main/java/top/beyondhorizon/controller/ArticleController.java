package top.beyondhorizon.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageInfoParams;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.model.params.TimeLineParams;
import top.beyondhorizon.model.vo.ArticleVo;
import top.beyondhorizon.service.ArticleService;
import top.beyondhorizon.service.impl.ArticleServiceImpl;

import java.util.List;

/**
 * ClassName: ArticleController
 * date: 2022/5/30 18:52
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("articles")
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    
    public ArticleController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }
    
    @LogAnnotation(module = "文章", operation = "获取文章预览")
    @GetMapping("title")
    public RetMsg articleTitle(@RequestParam(value = "pageNumber") Integer pageNumber,
                               @RequestParam(value = "pageSize") Integer pageSize) {
        PageParams pageParams = new PageParams();
        pageParams.setPageNumber(pageNumber);
        pageParams.setPageSize(pageSize);
        PageInfoParams articleList = articleService.getLastUpdateArticle(pageParams, true, true, false);
        return RetMsg.success(articleList);
    }
    
    @LogAnnotation(module = "文章", operation = "获取指定文章")
    @RequestMapping("views/{id}")
    public RetMsg views(@PathVariable("id") String articleId) {
        ArticleVo articleById = articleService.getArticleById(articleId);
        if (articleById == null) {
            return RetMsg.error("请求的文章存在");
        }
        return RetMsg.success(articleById);
    }
    
    
    @LogAnnotation(module = "文章", operation = "获取归档信息")
    @RequestMapping("time")
    public RetMsg time() {
        List<TimeLineParams> res = articleService.time();
        return RetMsg.success(res);
    }
    
    /*
    弃用
     */
    @LogAnnotation(module = "文章", operation = "点赞")
    @PostMapping("like")
    public RetMsg like(@RequestParam String articleId) {
        int like = articleService.like(articleId);
        if (like > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
}
