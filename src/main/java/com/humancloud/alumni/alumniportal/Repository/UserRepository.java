package com.humancloud.alumni.alumniportal.Repository;


import com.humancloud.alumni.alumniportal.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface UserRepository extends JpaRepository<User, Long> {
        public  User findByEmail(String email);
    }

