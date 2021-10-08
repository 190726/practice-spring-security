package com.sk.user.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sk.user.domain.User;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDetailDto implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String password;
	private String name;
	private String auth;
	
	public UserDetailDto(User user) {
		this.userId = user.getUserId();
		this.name = user.getName();
		this.password = user.getPassword();
		this.auth = user.getAuth();
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(auth.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userId;
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

}
