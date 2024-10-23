package com.humancloud.alumni.alumniportal.Service;

import com.humancloud.alumni.alumniportal.Exception.UserNameNotFoundException;
import com.humancloud.alumni.alumniportal.Model.User;
import com.humancloud.alumni.alumniportal.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserServiceIMPL implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username);
        if(user==null) {
            throw new UserNameNotFoundException("user not found with email"+username);

        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return user;
    }

}
