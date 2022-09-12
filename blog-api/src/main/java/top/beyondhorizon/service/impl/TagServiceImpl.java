package top.beyondhorizon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.model.params.TagAndCount;
import top.beyondhorizon.model.params.TagArticlesParams;
import top.beyondhorizon.entity.Tag;
import top.beyondhorizon.entity.TagName;
import top.beyondhorizon.model.vo.ArticleVo;
import top.beyondhorizon.model.vo.TagVo;
import top.beyondhorizon.mapper.TagMapper;
import top.beyondhorizon.mapper.TagNameMapper;
import top.beyondhorizon.service.ArticleService;
import top.beyondhorizon.service.TagService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: TagServiceImpl
 * date: 2022/5/30 21:30
 *
 * @author ayanamirei
 */


@Service
@Slf4j
public class TagServiceImpl implements TagService {
    
    private final TagMapper tagMapper;
    
    private final TagNameMapper tagNameMapper;
    
    private final ArticleService articleService;
    
    public TagServiceImpl(TagMapper tagMapper, TagNameMapper tagNameMapper, ArticleService articleService) {
        this.tagMapper = tagMapper;
        this.tagNameMapper = tagNameMapper;
        this.articleService = articleService;
    }
    
    @Override
    public List<TagVo> getAllTag() {
        List<TagName> tagNames = tagNameMapper.selectInUseTagName();
        List<TagVo> tagVos;
        tagVos = copyList(tagNames);
        return tagVos;
    }
    
    private List<TagVo> copyList(List<TagName> tagNames) {
        List<TagVo> tagVos = new ArrayList<>();
        for (TagName tagName : tagNames) {
            tagVos.add(copy(tagName));
        }
        return tagVos;
    }
    
    private TagVo copy(TagName tagName) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tagName, tagVo);
        
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_id", tagName.getTagId());
        queryWrapper.select("article_id");
        Integer count = Math.toIntExact(tagMapper.selectCount(queryWrapper));
        
        tagVo.setCount(count);
        return tagVo;
    }
    
    @Override
    public int addTag(String tagName) {
        LambdaQueryWrapper<TagName> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TagName::getTagName, tagName);
        TagName dbTag = tagNameMapper.selectOne(queryWrapper);
        if (dbTag != null) {
            return 2;
        }
        
        return tagNameMapper.insert(new TagName(null, tagName));
    }
    
    @Override
    public Map<String, Object> getTag(PageParams params) {
        Page<TagName> page = new Page<>(params.getPageNumber(), params.getPageSize());
        Page<TagName> tagNamePages = tagNameMapper.selectPage(page, null);
        List<TagName> tagNames = tagNamePages.getRecords();
        List<TagAndCount> tagAndCounts = new ArrayList<>();
        for (TagName tagName : tagNames) {
            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Tag::getTagId, tagName.getTagId());
            Integer count = Math.toIntExact(tagMapper.selectCount(queryWrapper));
            tagAndCounts.add(new TagAndCount(tagName.getTagId(), tagName.getTagName(), count));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("paramsList", tagAndCounts);
        map.put("pageSize", page.getSize());
        map.put("currentPage", page.getCurrent());
        map.put("total", page.getTotal());
        return map;
    }
    
    @Override
    public int delete(String tagId) {
        LambdaQueryWrapper<TagName> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TagName::getTagId, tagId);
        int delete = tagNameMapper.delete(queryWrapper);
        LambdaQueryWrapper<Tag> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Tag::getTagId, tagId);
        int delete1 = tagMapper.delete(queryWrapper1);
        if (delete == 1 || delete1 == 1) {
            return 1;
        } else {
            return 0;
        }
    }
    
    @Override
    public int edit(String tagId, String tagName) {
        LambdaQueryWrapper<TagName> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TagName::getTagId, tagId);
        TagName tagName1 = new TagName(null, tagName.trim());
        return tagNameMapper.update(tagName1, queryWrapper);
    }
    
    @Override
    public List<TagName> getTagByArticleId(String articleId) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getArticleId, articleId);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        List<TagName> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            String tagId = tag.getTagId();
            LambdaQueryWrapper<TagName> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(TagName::getTagId, tagId);
            TagName tagName = tagNameMapper.selectOne(queryWrapper1);
            tagNames.add(tagName);
        }
        return tagNames;
    }
}
