package com.humancloud.alumni.alumniportal.Service;


import com.humancloud.alumni.alumniportal.Model.User;

import java.util.List;

public interface UserService {
        public User getProfile(String jwt);
        public List<User> getAllUsers();


        public User getUserById(Long id);

    }
