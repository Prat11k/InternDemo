package spring.all.Components;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import spring.all.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private final UserService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain)throws ServletException, IOException {
		logger.info("Inside doFilterInternal Method");
		final String authHeader = request.getHeader("Authorization");
	    final String jwt;
	    final String username;
	    	if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
	    logger.info("AuthHeader is null registering new user");		
	    		filterChain.doFilter(request, response);
	    		return;
	    	}
	    logger.info("AuthHeader is not null authenticating user");
	    	jwt = authHeader.substring(7);
	    	username = jwtService.extractUsername(jwt);
	    		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	    			logger.info(String.format("authenticating user : %s ",username));
	    			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	    				if (jwtService.isTokenValid(jwt, userDetails)) {
	    					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	    							userDetails,
	    							null,
	    							userDetails.getAuthorities()
	    							);
	    	 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	         SecurityContextHolder.getContext().setAuthentication(authToken);	
	    				}
	    		}
	    		filterChain.doFilter(request, response);
	}
}