package com.uqai.demo.app.uqaidemo.config.security;

import com.uqai.demo.app.uqaidemo.model.user.User;
import com.uqai.demo.app.uqaidemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmailOrUsername(username, username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario no existe"));
        return new UserDetailsImpl(user);
    }
}
