package com.nowcoder.community.service;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.util.SensitiveFilter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author HAN
 * @date 2022/4/11 12:36
 */
@Service
public class DiscussPostService {

    private static final Logger logger = LoggerFactory.getLogger(DiscussPostService.class);

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Value("${caffeine.posts.max-size}")
    private int maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private int expireSeconds;

    // Caffeine核心接口: Cache, LoadingCache, AsyncLoadingCache

    /**
     * 帖子列表缓存
     */
    private LoadingCache<String, List<DiscussPost>> postListCache;

    /**
     * 帖子总数缓存
     */
    private LoadingCache<Integer, Integer> postRowsCache;

    @PostConstruct
    public void init() {
        // 初始化帖子列表缓存
        postListCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<DiscussPost>>() {
                    @Nullable
                    @Override
                    public List<DiscussPost> load(String key) throws Exception {
                        if (key == null || key.length() == 0) {
                            throw new IllegalArgumentException("参数错误!");
                        }

                        String[] params = key.split(":");
                        if (params.length != 2) {
                            throw new IllegalArgumentException("参数错误!");
                        }

                        int offset = Integer.parseInt(params[0]);
                        int limit = Integer.parseInt(params[1]);

                        // 二级缓存: Redis -> mysql

                        logger.debug("load post list from DB.");
                        return discussPostMapper.selectDiscussPosts(0, offset, limit, 1);
                    }
                });
        // 初始化帖子总数缓存
        postRowsCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(Integer key) throws Exception {
                        if (key == null) {
                            throw new IllegalArgumentException("参数错误!");
                        }

                        logger.debug("load post rows from DB.");
                        return discussPostMapper.selectDiscussPostRows(key);
                    }
                });
    }

    /**
     * 查看某用户所有帖子
     *
     * @param userId    用户id
     * @param offset    页码
     * @param limit     每页数量
     * @param orderMode 排序模式
     * @return          帖子列表
     */
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit, int orderMode) {
        if (userId == 0 && orderMode == 1) {
            return postListCache.get(offset + ":" + limit);
        }

        logger.debug("load post list from DB.");
        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
    }

    /**
     * 帖子总行数
     *
     * @param userId    用户id
     * @return          行数
     */
    public int findDiscussPostRows(int userId) {
        if (userId == 0) {
            return postRowsCache.get(userId);
        }

        logger.debug("load post rows from DB.");
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    /**
     * 添加一个帖子
     *
     * @param post  帖子
     * @return      行数
     */
    public int addDiscussPost(DiscussPost post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 转义HTML标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    /**
     * 业务层根据id查询帖子
     *
     * @param id    id
     * @return      帖子
     */
    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    /**
     * 更新帖子评论数量
     *
     * @param id            帖子id
     * @param commentCount  新的数量
     * @return              更新行数
     */
    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    public int updateType(int id, int type) {
        return discussPostMapper.updateType(id, type);
    }

    public int updateStatus(int id, int status) {
        return discussPostMapper.updateStatus(id, status);
    }

    public int updateScore(int id, double score) {
        return discussPostMapper.updateScore(id, score);
    }

}
