package de.uni_koeln.spinfo.upcase.test.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityContext extends WebSecurityConfigurerAdapter {

	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth.inMemoryAuthentication().withUser("user").password("password").roles("GUEST");
	// }

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/resources/**", "/signup", "/about", "/index/**").permitAll()
//				.antMatchers("/admin/**").hasRole("ADMIN").and().formLogin().loginPage("/login").permitAll().and()
//				.logout().logoutUrl("/logout").logoutSuccessUrl("/index");
//
//	}

}
