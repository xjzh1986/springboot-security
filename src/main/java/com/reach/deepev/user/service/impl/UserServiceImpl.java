package com.reach.deepev.user.service.impl;

import com.reach.deepev.user.entity.SysUser;
import com.reach.deepev.user.mapper.SysUserMapper;
import com.reach.deepev.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl implements UserService {

	@Resource
	private SysUserMapper sysUserMapper;

	public SysUser getUserByUserName(String username){
		SysUser sysUser = sysUserMapper.getSysUserByName(username);
		return sysUser;
	}


}
