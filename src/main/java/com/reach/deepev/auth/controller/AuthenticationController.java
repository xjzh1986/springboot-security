package com.reach.deepev.auth.controller;

import java.util.ArrayList;
import java.util.List;

import com.reach.deepev.user.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.reach.deepev.auth.entity.UserEntity;
import com.reach.deepev.auth.utils.JwtTokenUtil;
import com.reach.deepev.user.service.UserService;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;

import net.sf.json.JSONObject;

@Api2Doc(name = "登录模块")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	UserService userService;

	@ApiComment("登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public UserEntity login(@RequestBody JSONObject user) {
		UserEntity userEntity = new UserEntity();
		try {
			String username = user.getString("username");
			String password = user.getString("password");
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = JwtTokenUtil.generateToken(username);
			SysUser sysUser = userService.getUserByUserName(username);
			userEntity.setId(sysUser.getUserId());
			userEntity.setStatus(0);
			userEntity.setUsername(username);
			userEntity.setToken(token);
			List<String> roles = new ArrayList<String>();
			for (GrantedAuthority author : authentication.getAuthorities()) {
				roles.add(author.getAuthority());
			}
			userEntity.setRoles(roles);
		} catch (Exception e) {
			userEntity.setStatus(1);
			userEntity.setMsg(e.getMessage());
		}
		return userEntity;
	}

	@ApiComment("刷新token")
	@RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
	@ResponseBody
	public String refreshToken(@RequestBody JSONObject tokenO) {
		String token = tokenO.getString("token");
		return JwtTokenUtil.refreshToken(token);
	}
}