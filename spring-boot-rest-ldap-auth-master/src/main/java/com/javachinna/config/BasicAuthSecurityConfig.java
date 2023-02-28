package com.javachinna.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.javachinna.model.Role;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Profile(Profiles.BASIC_AUTH)
@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Disable CSRF
		http
				.csrf().disable()
				// Only admin can perform HTTP delete operation
				.authorizeRequests().antMatchers(HttpMethod.DELETE).hasRole(Role.ADMIN)
				// any authenticated user can perform all other operations
//				.antMatchers("/products/**").hasAnyRole(Role.ADMIN, Role.USER).and().httpBasic()
				// Permit all other request without authentication
				.and().authorizeRequests().anyRequest().permitAll()
				// We don't need sessions to be created.
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//		http.cors().and().csrf().disable();
	}

//	@Override
//	public UserDetailsService userDetailsService() {
//		return userDetailsService;
//	}

	@Bean
	public UserDetailsService userDetailsService() {

		User.UserBuilder users = User.withDefaultPasswordEncoder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(users.username("user").password("password").roles("USER").build());
		manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
		return manager;
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(10);
////		return NoOpPasswordEncoder.getInstance();
//	}

//	@Bean
//	public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
//																		 ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
//																		 EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
//																		 WebEndpointProperties webEndpointProperties, Environment environment) {
//		List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
//		Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//		allEndpoints.addAll(webEndpoints);
//		allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//		allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//		String basePath = webEndpointProperties.getBasePath();
//		EndpointMapping endpointMapping = new EndpointMapping(basePath);
//		boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment,
//				basePath);
//		return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
//				corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
//				shouldRegisterLinksMapping, null);
//	}
//
//	private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment,
//											   String basePath) {
//		return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath)
//				|| ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
//	}
}
