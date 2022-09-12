package top.beyondhorizon.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.SearchParams;
import top.beyondhorizon.service.SearchService;

import java.util.List;

/**
 * ClassName: SearchController
 * date: 2022/7/2 03:08
 *
 * @author ayanamirei
 */


@RestController
@RequestMapping("search")
@Slf4j
public class SearchController {
    private final SearchService searchService;
    
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    
    @PostMapping("tagName")
    @LogAnnotation(module = "搜索", operation = "搜索标签名")
    public RetMsg searchByTag(@RequestParam("tagName") String tag) {
        if (tag.equals("")) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        List<Article> articles = searchService.searchArticleByTag(tag);
        return RetMsg.success(articles);
    }
    
    @PostMapping("tagId")
    @LogAnnotation(module = "搜索", operation = "搜索标签id")
    public RetMsg searchByTagId(@RequestParam("tagId") Integer tagId) {
        if (tagId == 0) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        SearchParams params = searchService.searchArticleByTagId(tagId);
        return RetMsg.success(params);
    }
    
    @PostMapping("filingName")
    @LogAnnotation(module = "搜索", operation = "搜索分类名")
    public RetMsg searchByFilingName(@RequestParam("filingName") String filingName) {
        if (filingName == null || filingName.equals("")) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        SearchParams params = searchService.searchArticleByFilingName(filingName);
        return RetMsg.success(params);
    }
    
    @PostMapping("filingId")
    @LogAnnotation(module = "搜索", operation = "搜索分类id")
    public RetMsg searchArticleByFilingId(@RequestParam("filingId") Integer filingId) {
        if (filingId == null || filingId == 0) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        SearchParams params = searchService.searchArticleByFilingId(filingId);
        return RetMsg.success(params);
    }
    
    @PostMapping("title")
    @LogAnnotation(module = "搜索", operation = "搜索标题")
    public RetMsg searchArticleByTitle(@RequestParam("title") String title) {
        if (title == null || title.equals("")) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        SearchParams params = searchService.searchArticleByTitle(title);
        return RetMsg.success(params);
    }
}
