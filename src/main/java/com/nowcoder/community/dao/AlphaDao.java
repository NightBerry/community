package com.nowcoder.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author HAN
 * @date 2022/4/26 15:54
 */
@Mapper
public interface AlphaDao {

    /**
     * 测试查询
     *
     * @return  查询结果
     */
    String select();

}
