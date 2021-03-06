package com.wjy.ssm.business.mapper;


import com.wjy.ssm.business.domain.User;

public interface UserMapper {

    User selectByAccount(String account);


    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}