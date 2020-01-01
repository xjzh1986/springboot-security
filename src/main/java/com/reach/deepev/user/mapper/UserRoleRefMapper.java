package com.reach.deepev.user.mapper;

import com.reach.deepev.user.entity.UserRoleRef;

import java.util.List;

public interface UserRoleRefMapper {
    int deleteByPrimaryKey(Integer userRoleId);

    int insert(UserRoleRef record);

    UserRoleRef selectByPrimaryKey(Integer userRoleId);

    List<UserRoleRef> selectAll();

    int updateByPrimaryKey(UserRoleRef record);


}