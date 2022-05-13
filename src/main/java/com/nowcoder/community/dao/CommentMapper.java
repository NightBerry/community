package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author HAN
 * @date 2022/4/30 11:54
 */
@Mapper
public interface CommentMapper {

    /**
     * 查询评论信息
     *
     * @param entityType    实体类型
     * @param entityId      实体id
     * @param offset        第几页
     * @param limit         每页显示行数限制
     * @return              评论列表
     */
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    /**
     * 获取特定评论总数
     *
     * @param entityType    指定查询类型
     * @param entityId      指定查询id
     * @return              数量
     */
    int selectCountByEntity(int entityType, int entityId);

    /**
     * 插入评论
     *
     * @param comment   待插入评论
     * @return          更新数目
     */
    int insertComment(Comment comment);

    /**
     * 根据id查找评论
     *
     * @param id    id
     * @return      评论
     */
    Comment selectCommentById(int id);

}
