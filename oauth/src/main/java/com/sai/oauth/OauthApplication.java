package com.sai.oauth;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@SuppressWarnings("deprecation")
@SpringBootApplication
@RestController
@Configuration
public class OauthApplication extends WebSecurityConfigurerAdapter{
	
	





	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		String a =principal.getName();
		Map<String,Object> m=principal.getAttributes();
		String name =(String)m.get("login");
		
		System.out.println(a);
		System.out.println(name);
		return Collections.singletonMap("name", name);
	}

	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
		System.out.println("hi");
	
	}
	
	
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// for authorizing the urls for different endpoints 
		http.authorizeRequests(a -> a
				.antMatchers("/", "/error", "/webjars/**").permitAll()
				.anyRequest().authenticated()
				)
		.exceptionHandling(e -> e
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				)
		.oauth2Login();


		http.logout(l -> l.logoutSuccessUrl("/").permitAll());

		http.csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

	}




}
