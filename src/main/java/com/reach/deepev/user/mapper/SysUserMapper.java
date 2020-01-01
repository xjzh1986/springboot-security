package com.reach.deepev.user.mapper;

import com.reach.deepev.user.entity.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(Integer userId);

    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);

    SysUser getSysUserByName(String userName);
}