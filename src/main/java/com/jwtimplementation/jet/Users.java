package com.jwtimplementation.jet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "authorities")
    private String roles;

    public Users() {
    }

    public Users(Long id, String name, String password, String roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles=roles;
    }
    
    public Users(Long id, String name, String roles) {
        this.id = id;
        this.name = name;
        this.roles=roles;
    }
    
    public Users(String name, String password) {
        
        this.name = name;
        this.password=password;
    }

    public Users(String name, String password, String roles) {

        this.name = name;
        this.password = password;
        this.roles=roles;
    }

    public Long getId() {
        return id;
        //
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> grantedAuthorities=new ArrayList<>();
		
    	String authorities=getRoles();
    	String[] authoritiesArray=authorities.split(",");
    	for(String role:authoritiesArray) {
    		SimpleGrantedAuthority sga=new SimpleGrantedAuthority(role);
    		grantedAuthorities.add(sga);
    	}
        return grantedAuthorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", password=" + password + ", roles=" + roles + "]";
	}

  
 
}
