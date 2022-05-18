package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.service.ElasticsearchService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HAN
 * @date 2022/5/18 16:45
 */
@Controller
public class SearchController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    /**
     * search?keyword=xxx
     *
     * @param keyword   关键字
     * @param page      分页
     * @param model     网页
     * @return          路径
     */
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(String keyword, Page page, Model model) {
        try {
            // 搜索帖子
            List<DiscussPost> discussPosts = elasticsearchService.searchDiscussPost(keyword, page.getCurrent() - 1, page.getLimit());
            List<Map<String, Object>> result = new ArrayList<>();
            if (discussPosts != null) {
                for (DiscussPost post : discussPosts) {
                    Map<String, Object> map = new HashMap<>(16);
                    // 帖子
                    map.put("post", post);
                    // 作者
                    map.put("user", userService.findUserById(post.getUserId()));
                    // 点赞数量
                    map.put("likeCount", likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()));
                    result.add(map);
                }
            }
            model.addAttribute("discussPosts", result);
            model.addAttribute("keyword", keyword);
            // 分页信息
            page.setPath("/search?keyword=" + keyword);
            page.setRows(discussPosts == null ? 0 : discussPosts.size());
            return "/site/search";
        } catch (IOException e) {
            logger.error("搜索失败: " + e.getMessage());
            return "redirect:/error";
        }
    }

}
