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

    int updateStatus(int id, int state);

    int updateHeaderUrl(int id, String headerUrl);

    int updatePassWord(int id, String passWord);

}
