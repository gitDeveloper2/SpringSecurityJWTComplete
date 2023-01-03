package com.jwtimplementation.jet;

import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyController {
	@Autowired
	private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    JwtUtils jwtUtils=new JwtUtils();
    @GetMapping(value = "/home")
    public String login(Model model){
    	model.addAttribute("messagese", "new data");
        return "home";
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody Map<String,Object> body) throws Exception{
    	Authentication auth=null;
    	    
    	auth=new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password"));
    	try {
    auth= authenticationManager.authenticate(auth);
    UserDetails userDetails=userDetailsService.loadUserByUsername(auth.getName());
    return new ResponseEntity<>(jwtUtils.generateToken(userDetails),HttpStatus.OK);
   
    }catch(BadCredentialsException e) {
    		 System.out.println("Bad credentials");
    	}catch (DisabledException e) {
    		System.out.println("Disabled");
        }catch(Exception e) {
        	System.out.println("Any other exception");
        }
    	return new ResponseEntity<>("Forbidden",HttpStatus.FORBIDDEN);
    }
    @GetMapping(value = "/test")
    public String test(){
        return "sdfksdmfklsd";

    }}


 
