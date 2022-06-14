package com.educa.mais.mais.projetointegrador.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.educa.mais.mais.projetointegrador.model.Parceiro;

public class ParceiroDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;

	
	private List<GrantedAuthority> authorities;

	public ParceiroDetailsImpl(Parceiro parceiro) {
		this.userName = parceiro.getEmail();
		this.password = parceiro.getSenha();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
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
