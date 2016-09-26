package de.uni_koeln.spinfo.upcase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.uni_koeln.spinfo.upcase.service.security.UpcaseAuthProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// @formatter:off
	@Autowired private UpcaseAuthProvider authProvider;

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 auth.authenticationProvider(authProvider);
	}
	 
	 
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(inMemoryUserDetailsManager());
//		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
//	}
//	  @Bean
//    public MyInMemoryUserDetailsManager inMemoryUserDetailsManager() {
//
//		Collection<UserDetails> users = new ArrayList<>();
//		
//		UpcaseUser upcaseUser1 = new UpcaseUser(new RegistrationForm("Mihail", "Atanassov", "matanass@uni-koeln.de", "Department of Computational Linguistics", "matana123", "matana123"));
//		UpcaseUser upcaseUser2 = new UpcaseUser(new RegistrationForm("Claes", "Neuefeind", "claesn@uni-koeln.de", "Department of Computational Linguistics", "claesn123", "claesn123"));
//		UpcaseUser upcaseUser3 = new UpcaseUser(new RegistrationForm("Guest", "Guest", "guest", "", "guest", "guest"));
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new GrantedAuthority() {
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public String getAuthority() {
//				return "ROLE_ADMIN";
//			}
//		});
//		UpcaseUser upcaseUser4 = new UpcaseUser(new RegistrationForm("Admin", "Admin", "admin", "", "admin", "admin"), true, true, true, true, authorities);
//		UpcaseUser upcaseUser5 = new UpcaseUser(new RegistrationForm("User", "User", "user", "", "user", "user"));
//		UpcaseUser upcaseUser6 = new UpcaseUser(new RegistrationForm("Editor", "Editor", "editor", "", "editor", "editor"));
//		
//		users.add(upcaseUser1);
//		users.add(upcaseUser2);
//		users.add(upcaseUser3);
//		users.add(upcaseUser4);
//		users.add(upcaseUser5);
//		users.add(upcaseUser6);
//		
//      return  new MyInMemoryUserDetailsManager(users);
//    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/**")
			.permitAll()
			.antMatchers("/admin/**")
			.access("hasRole('ADMIN')")
		.and()
			.formLogin()
			.loginPage("/login")
			.usernameParameter("emailID")
			.passwordParameter("passwd")
			.permitAll()
		.and()
			.exceptionHandling()
			.accessDeniedPage("/access_denied")
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.permitAll()
			.logoutSuccessUrl("/")
		.and()
			.httpBasic();
		
		
	}
	// @formatter:on

}
