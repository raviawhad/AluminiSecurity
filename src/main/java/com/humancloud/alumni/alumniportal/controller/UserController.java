package com.humancloud.alumni.alumniportal.controller;

import com.humancloud.alumni.alumniportal.Exception.userNotFoundException;
import com.humancloud.alumni.alumniportal.Model.User;
import com.humancloud.alumni.alumniportal.Service.UserService;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.humancloud.alumni.alumniportal.Security.JwtProvider.key;

@RestController
@RequestMapping("/api/user")
public class UserController {
     @Autowired
    UserService  userservice;
    public static final String USER_SERVICE="/api/user";

    @GetMapping("/profile")

    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) {
        System.out.println("Authorization header in controller: " + jwt);
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            System.out.println("Token after removing Bearer prefix in controller: " + jwt);
        }
        User user = this.userservice.getProfile(jwt);
            System.out.println(user.getFullName());


        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers(@RequestHeader("Authorization") String jwt) {
        System.out.println("Authorization header in controller: " + jwt);

        // Step 1: Remove "Bearer " prefix from JWT
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            System.out.println("Token after removing Bearer prefix in controller: " + jwt);
        }

        List<User> users = List.of();  // Default empty list if no access

        try {
            // Step 2: Parse JWT token to extract claims (ensure `key` is your secret key)
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            String roles = String.valueOf(claims.get("roles"));
            System.out.println(roles);

            // Step 3: Check if the user has the "ADMIN" role
            if (roles.contains("ADMIN")) {
                users = this.userservice.getAllUsers();  // Admin can access all users
            } else {
                System.out.println("Access denied for roles: " + roles);
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Return 403 if not admin
            }
        } catch (Exception e) {
            // Step 4: Handle any JWT parsing or validation errors
            System.out.println("Invalid JWT token: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // Return 401 for invalid token
        }

        // Step 5: Return the list of users if the request is successful
        return new ResponseEntity<>(users, HttpStatus.OK);
    }





    @GetMapping("/getUserwithId/{id}")
    public  ResponseEntity<User>getUserById(@PathVariable Long id) {
        User user = this.userservice.getUserById(id);
        if(user!=null) {
            return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
        }     else {
            throw new userNotFoundException("not found with this Id" +id);
        }
    }

}
