package com.jorgejy.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.jorgejy.springboot.app.model.service.JpaUserDetailsService;

 
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoderDI;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private JpaUserDetailsService jpaUserDetailsService; 

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		/*
		 * PasswordEncoder passwordEncoder = passwordEncoderDI; UserBuilder userBuilder
		 * = User.builder().passwordEncoder(passwordEncoder::encode);
		 * 
		 * authenticationManagerBuilder.inMemoryAuthentication()
		 * .withUser(userBuilder.username("admin").password("12345").roles("ADMIN",
		 * "USER"))
		 * .withUser(userBuilder.username("jorge").password("12345").roles("USER"));
		 */	
		
		
		/*
		 * authenticationManagerBuilder.jdbcAuthentication() .dataSource(dataSource)
		 * .passwordEncoder(passwordEncoderDI)
		 * .usersByUsernameQuery("select username, password, enabled from users where username=?"
		 * )
		 * .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?"
		 * );
		 */
		
		
		authenticationManagerBuilder.userDetailsService(jpaUserDetailsService).passwordEncoder(passwordEncoderDI);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/css/**", "/js/**", "/images/**", "/list", "/locale", "/api/list").permitAll()
		/* .antMatchers("/show/**").hasAnyRole("USER") 
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
