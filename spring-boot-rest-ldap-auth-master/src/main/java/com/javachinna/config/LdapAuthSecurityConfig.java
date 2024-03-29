package com.javachinna.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

import com.javachinna.model.Role;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile(Profiles.LDAP_AUTH)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class LdapAuthSecurityConfig extends WebSecurityConfigurerAdapter {

	private final LdapUserAuthoritiesPopulator ldapUserAuthoritiesPopulator;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Returns LdapAuthenticationProviderConfigurer to allow customization of the
		// LDAP authentication
		auth.ldapAuthentication()
				// Pass the LDAP patterns for finding the username.
				// The key "{0}" will be substituted with the username
				.userDnPatterns("uid={0},ou=users")
				// Pass search base as argument for group membership searches.
				.groupSearchBase("ou=groups")
				// Configures base LDAP path context source
				.contextSource().url("ldap://localhost:10389/dc=javachinna,dc=com")
				// DN of the user who will bind to the LDAP server to perform the search
				.managerDn("uid=admin,ou=system")
				// Password of the user who will bind to the LDAP server to perform the search
				.managerPassword("secret").and()
				// Configures LDAP compare operation of the user password to authenticate
//				.passwordCompare().passwordEncoder(new LdapShaPasswordEncoder())
//				.passwordCompare().passwordEncoder(NoOpPasswordEncoder.getInstance())
				.passwordCompare().passwordEncoder(new BCryptPasswordEncoder())
				// Specifies the attribute in the directory which contains the user password.
				// Defaults to "userPassword".
				.passwordAttribute("userPassword").and()
				// Populates the user roles by LDAP user name from database
				.ldapAuthoritiesPopulator(ldapUserAuthoritiesPopulator);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// Disable CSRF
		httpSecurity.csrf().disable()
				// Only admin can perform HTTP delete operation
				.authorizeRequests().antMatchers(HttpMethod.DELETE).hasRole(Role.ADMIN)
				// any authenticated user can perform all other operations
//				.antMatchers("/products/**").hasAnyRole(Role.USER, Role.ADMIN).and().httpBasic()
				// Permit all other request without authentication
				.and().authorizeRequests().anyRequest().permitAll()
				// We don't need sessions to be created.
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();




	}
}