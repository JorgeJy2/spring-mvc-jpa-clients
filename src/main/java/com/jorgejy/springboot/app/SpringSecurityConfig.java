package com.jorgejy.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jorgejy.springboot.app.auth.handler.LoginSuccessHandler;

 
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		PasswordEncoder passwordEncoder = passwordEncoder();
		UserBuilder userBuilder = User.builder().passwordEncoder(passwordEncoder::encode);
		
		authenticationManagerBuilder.inMemoryAuthentication()
		.withUser(userBuilder.username("admin").password("12345").roles("ADMIN","USER"))
		.withUser(userBuilder.username("jorge").password("12345").roles("USER"));		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/css/**", "/js/**", "/images/**", "/list").permitAll()
		/* .antMatchers("/show/**").hasAnyRole("USER") */
		/*.antMatchers("/upload/**").hasAnyRole("USER") */
		/*.antMatchers("/form/**").hasAnyRole("ADMIN") */
		/*.antMatchers("/delete/**").hasAnyRole("ADMIN") */
		/*.antMatchers("/bill/**").hasAnyRole("ADMIN") */
		.anyRequest().authenticated()
		.and()
		.formLogin().successHandler(loginSuccessHandler).loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and().exceptionHandling().accessDeniedPage("/error_403")
		;
	}
	
	
}
