package com.reach.deepev.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.reach.deepev.auth.filters.CustomAuthenticationProvider;
import com.reach.deepev.auth.filters.JwtAuthenticationTokenFilter;
import com.reach.deepev.auth.service.AuthUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthUserDetailsService userDetailsService; // 自定义user

	@Autowired
	JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter; // JWT 拦截器

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 加入自定义的安全认证
		auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService, new BCryptPasswordEncoder()));
	}

	@Autowired // 注意这个方法是注入的
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 去掉 CSRF
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用
																											// JWT，关闭token
				.and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // 定义哪些URL需要被保护、哪些不需要被保护

				.antMatchers("/auth/login", "/api2doc/*", "/**", "/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js",
						"/**/*.map", "/**/*.html", "/**/*.less", "/**/*.woff", "/**/*.png", "/*.ico")
				.permitAll().anyRequest().authenticated().and().headers().frameOptions().disable();

		// 记住我
		http.rememberMe().rememberMeParameter("remember-me").userDetailsService(userDetailsService)
				.tokenValiditySeconds(1000);

//		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); // 无权访问 JSON 格式的数据
		http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // JWT Filter

	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/deepev/**");
//	}
}
