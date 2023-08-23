package com.movie.booking.config;

import com.movie.booking.models.User;
import com.movie.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsImplService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByLoginId(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();

        return new UserDetailsImpl(user.getLoginId(), user.getPassword(), authorities);

    }
}
