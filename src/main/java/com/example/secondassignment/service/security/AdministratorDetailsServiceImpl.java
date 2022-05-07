package com.example.secondassignment.service.security;

import com.example.secondassignment.model.AccountRole;
import com.example.secondassignment.model.Administrator;
import com.example.secondassignment.repository.AdministratorRepository;
import com.example.secondassignment.service.account.exceptions.NoSuchAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdministratorDetailsServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorDetailsServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Administrator administrator = this.administratorRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchAccountException("No admin with the email " + email + " was found!"));
        return new org.springframework.security.core.userdetails.User(
                administrator.getEmail(),
                administrator.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(AccountRole.ADMIN.toString())));
    }
}
