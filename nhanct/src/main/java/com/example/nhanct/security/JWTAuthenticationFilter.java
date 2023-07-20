package com.example.nhanct.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter{

	private UserDetailsService userDetailsService;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain chain)
			throws IOException, ServletException {
		
		String tokenHeader = request.getHeader("Authorization");
		
		if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			try {
				String token = tokenHeader.replace("Bearer ", "");
				
				String email = Jwts
						.parser()
						.setSigningKey("ALO123")
						.parseClaimsJws(token)
						.getBody()
						.getSubject();
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				
				Authentication authentication = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
			catch (Exception e) {
				response.sendError(400, "Token không đúng định dạng!");
			}
		}
		
		chain.doFilter(request, response);
	}
}
