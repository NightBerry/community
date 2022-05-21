package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HAN
 * @date 2022/4/11 10:25
 */
@Mapper
public interface DiscussPostMapper {

    /**
     * 查询帖子
     *
     * @param userId    后期可查询用户自己发的帖子
     * @param offset    从第几行开始显示帖子
     * @param limit     显示多少帖子，每页的限制
     * @return          帖子集合
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    /**
     * 查询数据库中帖子总数
     * Param注解用于给参数取别名，如果方法只有一个参数，并且在动态SQL中使用，则必须加别名
     *
     * @param userId    后期后期可查询用户自己发的帖子总数
     * @return          贴子总数
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * 插入一条帖子
     *
     * @param discussPost   要插入的帖子
     * @return              更新的行数
     */
    int insertDiscussPost(DiscussPost discussPost);

    /**
     * 根据帖子id查询整条帖子信息
     *
     * @param id    id
     * @return      帖子信息
     */
    DiscussPost selectDiscussPostById(int id);

    /**
     * 更新评论数
     *
     * @param id            帖子id
     * @param commentCount  评论数量
     * @return              更新的行数
     */
    int updateCommentCount(int id, int commentCount);

    /**
     * 更新帖子类型
     *
     * @param id    帖子id
     * @param type  新类型
     * @return      更新数量
     */
    int updateType(int id, int type);

    /**
     * 更新帖子状态
     *
     * @param id        帖子id
     * @param status    新状态
     * @return          更新行数
     */
    int updateStatus(int id, int status);

    /**
     * 更新帖子权重
     *
     * @param id        帖子id
     * @param score     新权重
     * @return          更新行数
     */
    int updateScore(int id, double score);

}
