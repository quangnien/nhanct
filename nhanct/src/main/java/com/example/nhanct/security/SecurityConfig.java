package com.example.nhanct.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;


@Component
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	//dùng để giải mã mật khẩu (Sử dụng thư viện JBcrypt)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	//khai báo service lấy thông tin user từ db và khai báo phương thức mã hóa password
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	@CrossOrigin(origins = "http://127.0.0.1:8090/", maxAge = 3600)
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/user/invoice/**", "/user/customer/**",
//				"/user/review", "/admin/profile", "/admin", "/admin/product",
//				"/admin/product/**").access("hasRole('ADMIN') or hasRole('NHANVIEN')");
		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ADMIN')");
//		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ADMIN')");
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().and().formLogin().loginProcessingUrl("/login/admin").loginPage("/login/admin").usernameParameter("username").passwordParameter("password")
		.defaultSuccessUrl("/admin").failureUrl("/login/admin?message=error");

	}
}