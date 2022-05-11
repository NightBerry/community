package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author HAN
 * @date 2022/4/16 10:41
 */
@Mapper
@Deprecated
public interface LoginTicketMapper {

    /**
     * 插入登录凭证
     * useGeneratedKeys = true, 是否自动生成主键
     * keyProperty = "id"，生成的值注入给哪个属性
     *
     * @param loginTicket   登录凭证
     * @return              成功与否
     */
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * 查询登录凭证
     *
     * @param ticket    凭证
     * @return          凭证
     */
    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    /**
     * 修改凭证状态，例如退出应该失效
     *
     * @param ticket    凭证
     * @param status    状态
     * @return          更新的行
     */
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);

}
