package com.example.todoservice.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	

	@Autowired
	public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery("select username, password, enabled from user where username = ?")
				.authoritiesByUsernameQuery("select username, role from role where username = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/h2-console/**", "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
						"/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**")
				.permitAll().antMatchers("/list", "/list/**", "/user", "/user/**").authenticated().and().httpBasic();

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

}
