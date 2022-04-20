package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HAN
 * @date 2022/4/10 10:55
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户id查询用户信息
     *
     * @param id    用户id
     * @return      返回用户信息
     */
    User selectById(int id);

    /**
     * 根据用户名查询用户信息
     *
     * @param userName  用户名
     * @return          用户信息
     */
    User selectByName(String userName);

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return      同户信息
     */
    User selectByEmail(String email);

    /**
     * 插入用户信息，返回插入行号
     *
     * @param user  用户信息
     * @return      行号
     */
    int insertUser(User user);

    /**
     * 更新用户状态
     *
     * @param id        用户id
     * @param status    新的状态
     * @return          更新行数
     */
    int updateStatus(int id, int status);

    /**
     * 更新用户头像路径】
     *
     * @param id        用户id
     * @param headerUrl 头像路径
     * @return          更新行数
     */
    int updateHeaderUrl(int id, String headerUrl);

    /**
     * 更新用户密码
     *
     * @param id        用户id
     * @param passWord  新密码
     * @return          更新行数
     */
    int updatePassWord(int id, String passWord);

}
