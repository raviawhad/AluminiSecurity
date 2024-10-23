package com.humancloud.alumni.alumniportal.Service;

import com.humancloud.alumni.alumniportal.Model.User;
import com.humancloud.alumni.alumniportal.Repository.UserRepository;
import com.humancloud.alumni.alumniportal.Security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceIMPL implements UserService {
   @Autowired
    private UserRepository userRepository;

    @Override
    public User getProfile(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        System.out.println(email);
        return this.userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        User userid = this.userRepository.getById(id);
        return userid;
    }
}
