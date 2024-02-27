package com.sec_project.vacation.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec_project.vacation.config.JwtServcie;
import com.sec_project.vacation.token.Token;
import com.sec_project.vacation.token.TokenRepository;
import com.sec_project.vacation.token.TokenType;
import com.sec_project.vacation.user.User;
import com.sec_project.vacation.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository repository;
	
	private final TokenRepository tokenRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtServcie jwtServcie;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.build();
		var savedUser = repository.save(user);
		var jwtToken = jwtServcie.generateToken(user);
		var refreshToken = jwtServcie.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
					.refreshToken(refreshToken)
	            .build();
	}
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
						)
				);
				var user = repository.findByEmail(request.getEmail())
						.orElseThrow();
				var jwtToken = jwtServcie.generateToken(user);
				var refreshToken = jwtServcie.generateRefreshToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, jwtToken);
				return AuthenticationResponse.builder()
						.accessToken(jwtToken)
							.refreshToken(refreshToken)
			            .build();
	}
	
	private void revokeAllUserTokens(User user) {
		var validUserToken = tokenRepository.findAllBValidTokenByUser(user.getId());
		if(validUserToken.isEmpty())
			return;
		validUserToken.forEach( t -> {
			t.setExpired(true);
			t.setRevoked(true);
		});
		tokenRepository.saveAll(validUserToken);
	}
	
	
	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(token);
	}
	public void refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
	) throws StreamWriteException, DatabindException, IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail =  jwtServcie.extractUsername(refreshToken);
		if(userEmail != null){
			var user = this.repository.findByEmail(userEmail)
					.orElseThrow();
			var isTokenVaild = tokenRepository.findByToken(refreshToken)
					.map(t -> !t.isExpired() && !t.isRevoked())
					.orElse(false);
			if( jwtServcie.isTokenValid(refreshToken, user )&&isTokenVaild) {
				var accessToken = jwtServcie.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponce = AuthenticationResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream() , authResponce);
			}
		}
		
	}

}
