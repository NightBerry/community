package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用元注解定义自己的和注解
 * Target - 当前注解的作用范围
 * Retention - 当前注解作用时间
 *
 * @author HAN
 * @date 2022/4/19 11:47
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {



}
