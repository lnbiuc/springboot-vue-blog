package top.beyondhorizon.controller.admin;


import org.springframework.web.bind.annotation.*;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.EditParams;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.model.params.PublishParams;
import top.beyondhorizon.model.vo.ArticleVo;
import top.beyondhorizon.service.ArticleService;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ArticleAdminController
 * date: 2022/6/16 00:15
 *
 * @author ayanamirei
 */


@RestController
@RequestMapping("admin/articles")
public class ArticleAdminController {
    private final ArticleService articleService;
    
    public ArticleAdminController(ArticleService articleService) {
        this.articleService = articleService;
    }
    
    @LogAnnotation(module = "文章管理", operation = "发布一篇文章")
    @PostMapping("publish")
    public RetMsg publish(@RequestBody PublishParams publishParams, @RequestHeader String token) {
        return articleService.publishArticle(publishParams, token);
    }
    
    @LogAnnotation(module = "文章管理", operation = "自动保存")
    @PostMapping("save")
    public RetMsg save(@RequestParam String articleId, @RequestParam String content) {
        int i = articleService.save(articleId, content);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.FILING_EXISTS.getMsg());
    }
    
    @PostMapping("edit")
    @LogAnnotation(module = "文章管理", operation = "编辑文章")
    public RetMsg edit(@RequestBody EditParams editParams, @RequestHeader String token) {
        return articleService.edit(editParams, token);
    }
    
    @LogAnnotation(module = "文章管理", operation = "删除一篇文章")
    @PostMapping("delete")
    public RetMsg delete(@RequestParam String articleId, @RequestHeader String token) {
        return articleService.delete(articleId, token);
    }
    
    @LogAnnotation(module = "文章管理", operation = "分页获取文章")
    @PostMapping("get")
    public RetMsg get(@RequestBody PageParams params) {
        Map<String, Object> map = articleService.get(params);
        return RetMsg.success(map);
    }
    
    @LogAnnotation(module = "文章管理", operation = "设置置顶")
    @PostMapping("setTop")
    public RetMsg setTop(@RequestParam String articleId) {
        int i = articleService.setTop(articleId);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @LogAnnotation(module = "文章管理", operation = "获取最近编辑过的文章")
    @PostMapping("lastEdit")
    public RetMsg lastEdit() {
        ArticleVo articleVo = articleService.lastEdit();
        return RetMsg.success(articleVo);
    }
    
    @LogAnnotation(module = "文章管理", operation = "获取已经删除的文章")
    @PostMapping("deletedArticle")
    public RetMsg deletedArticle(@RequestBody PageParams params) {
        Map<String, Object> map = articleService.deletedArticle(params);
        return RetMsg.success(map);
    }
    
    @LogAnnotation(module = "文章管理", operation = "恢复删删除的文章")
    @PostMapping("recover")
    public RetMsg recover(@RequestParam String articleId) {
        int i = articleService.recoverArticle(articleId);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
}