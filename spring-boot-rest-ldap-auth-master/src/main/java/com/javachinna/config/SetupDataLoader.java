package com.javachinna.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.javachinna.model.Role;
import com.javachinna.model.UserEntity;
import com.javachinna.repo.RoleRepository;
import com.javachinna.repo.UserRepository;

@Profile("!noauth & !defaultauth1 or customauth")
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	PasswordEncoder passwordEncoder;

	private boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}

		// Create user roles
		var userRole = createRoleIfNotFound(Role.ROLE_USER);
		var adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);

		// Create users
		createUserIfNotFound("user", userRole);
		createUserIfNotFound("admin", adminRole);

		alreadySetup = true;
	}

	@Transactional
	private final Role createRoleIfNotFound(final String name) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role = roleRepository.save(role);
		}
		return role;
	}

//	TODO (SSM-100) Spring Security Mgmt - this was not recognized with hardcoded pwd. Once used encode, it started recognizing and working the test
	@Transactional
	private final UserEntity createUserIfNotFound(final String name, final Role role) {
		UserEntity user = userRepository.findByUsername(name);
		if (user == null) {
//			user = new UserEntity(name, "{bcrypt}$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6");
			user = new UserEntity(name, passwordEncoder.encode("password"));
			user.setRoles(Set.of(role));
			user = userRepository.save(user);
		}
		return user;
	}
}