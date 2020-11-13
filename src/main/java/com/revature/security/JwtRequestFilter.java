package com.revature.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.revature.clients.UserClient;
import com.revature.models.User;
import com.revature.services.QuestionService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private QuestionService qService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		
		String jwt = null;
		String email = null;
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);  		
			return;
		}
		
		try {
			
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				jwt = authHeader.substring(7);
				email = jwtUtil.extractEmail(jwt);
			}
			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				User temp = new User();
				temp.setEmail(email);
				User user = userClient.authUser(temp);
				if(jwtUtil.validateToken(jwt, user).equals(true)) {
					 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user ,null, qService.getAuthority(user) );
					 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		//add catch block with logger
		}
		catch (Exception e) {
			SecurityContextHolder.clearContext();
			e.getMessage();
		}
		finally {
			filterChain.doFilter(request,response);		
		}
	}

}