package com.reach.deepev.user.mapper;

import com.reach.deepev.user.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    List<SysRole> selectAll();

    int updateByPrimaryKey(SysRole record);

    List<SysRole> getRolesByUserId(Integer userId);
}