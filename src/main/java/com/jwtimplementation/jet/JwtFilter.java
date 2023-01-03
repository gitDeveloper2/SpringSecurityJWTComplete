package com.jwtimplementation.jet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtFilter extends OncePerRequestFilter{
	@Lazy
@Autowired
private MyUserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//check if request contains request and is valid, if true auto authenticate else send exception
		String token=request.getHeader("Authorization");
		
		if(token !=null && token.startsWith("Bearer ") ) {
			
			token=token.substring(7);
			JwtUtils jwtUtils=new JwtUtils();
			String username=jwtUtils.getUsernameFromToken(token);
			
//			UserDetails userDetails=new MyUserDetailsService().loadUserByUsername(username);
			UserDetails userDetails=userDetailsService.loadUserByUsername(username);

			if(jwtUtils.validateToken(token, userDetails )) {
				System.out.println(userDetails.getUsername());
				UsernamePasswordAuthenticationToken upat=
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upat);
			}
			
			
		}
		
		filterChain.doFilter(request, response);
	}
	
}
