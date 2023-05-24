package spring.all.auth;

import lombok.RequiredArgsConstructor;
import spring.all.Entity.Role;
import spring.all.Entity.User;
import spring.all.repository.UserRepository;
import spring.all.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService jwtService;
	private final AuthenticationManager authenticationManager;

	Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	// register
	public AuthenticationResponse register(RegisterRequest request) {
		/*
		 * User admin = User.builder() .username(request.getUsername())
		 * .password(passwordEncoder.encode(request.getPassword())) .role(Role.ADMIN)
		 * .build();
		 */
		logger.info(String.format("Inside method register regstering %s ", request.getUsername()));
		User user = User.builder().username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
		logger.info("Saving user to Mysql");
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		logger.info(String.format("returning token %s", jwtToken));
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public String authenticate(AuthenticationRequest request) {
		logger.info(String.format("Inside method authenticate authenticating %s ", request.getUsername()));
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		return "Authenticated " + user.getUsername();
	}
}