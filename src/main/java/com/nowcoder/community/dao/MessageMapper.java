package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author HAN
 * @date 2022/5/2 9:11
 */
@Mapper
public interface MessageMapper {

    /**
     * 查询当前用户的会话列表,针对每个会话只返回一条最新的私信
     *
     * @param userId    用户
     * @param offset    从第几行开始显示
     * @param limit     显示多少
     * @return          会话列表
     */
    List<Message> selectConversations(int userId, int offset, int limit);

    /**
     * 查询当前用户的会话数量
     *
     * @param userId    用户id
     * @return          会话数量
     */
    int selectConversationCount(int userId);

    /**
     * 查询某个会话所包含的私信列表
     *
     * @param conversationId    会话id
     * @param offset            从几行开始显示
     * @param limit             显示多少
     * @return                  私信列表
     */
    List<Message> selectLetters(String conversationId, int offset, int limit);

    /**
     * 查询某个会话所包含的私信数量
     *
     * @param conversationId    会话id
     * @return                  私信数量
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询未读私信的数量
     *
      * @param userId           用户id
     * @param conversationId    会话id
     * @return                  未读私信数
     */
    int selectLetterUnreadCount(int userId, String conversationId);

    /**
     * 新增消息
     *
     * @param message   消息
     * @return          增加行数
     */
    int insertMessage(Message message);

    /**
     * 修改消息的状态
     *
     * @param ids
     * @param status    新状态
     * @return          更新行数
     */
    int updateStatus(List<Integer> ids, int status);

}
