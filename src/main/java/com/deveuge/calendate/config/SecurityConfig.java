package com.deveuge.calendate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deveuge.calendate.model.service.UserService;
import com.deveuge.calendate.security.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// TODO: Delete
	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/settings", "/events").authenticated()
			.anyRequest().permitAll()
			.and().formLogin().loginPage("/login").permitAll().usernameParameter("username-or-email").passwordParameter("password-login")
			.and().logout().permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/");
		
		http.sessionManagement()
			.invalidSessionUrl("/")
			.maximumSessions(2)
			.expiredUrl("/");
		
		// TODO: Delete
        //userService.save(new User("admin", "Administrador", "admin@email.com", null, new BCryptPasswordEncoder().encode("admin"), Role.ADMIN, true));
        //userService.save(new User("user", "Usuario", "user@email.com", null, new BCryptPasswordEncoder().encode("user"), Role.USER, true));
	}
	
	@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }
	
	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
}
