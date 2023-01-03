package com.jwtimplementation.jet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UsersRepo usersRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	Users user=usersRepo.findByName(username).orElseThrow(()->new UsernameNotFoundException(  String.format("USER_NOT_FOUND", username)));
    	
        return user;


    }

    public void save(Users user) throws Exception  {
    	
        String encodedPassword=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

       usersRepo.save(user);

    }
    
 public Users find(Users user) throws Exception  {
    	
 	return usersRepo.findByName(user.getUsername()).orElseThrow(()->new UsernameNotFoundException(  String.format("USER_NOT_FOUND", user.getUsername())));


    }
    
}
