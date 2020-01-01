package com.reach.deepev.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.reach.deepev.auth.service.AuthUserDetailsService;
import com.reach.deepev.auth.utils.JwtTokenUtil;

/**
 * @author: zzx
 * @date: 2018/10/15 17:30
 * @description: 确保在一次请求只通过一次filter，而不需要重复执行
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	@Autowired
	AuthUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!request.getMethod().equals("OPTIONS")) {
			response.setHeader("access-control-allow-origin", "*");
			response.setHeader("access-control-allow-methods", "*");
		}
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String authToken = authHeader.substring("Bearer ".length());
			String username = JwtTokenUtil.getUsernameFromToken(authToken);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (userDetails != null) {
					if (JwtTokenUtil.validateToken(authToken, userDetails)) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}
